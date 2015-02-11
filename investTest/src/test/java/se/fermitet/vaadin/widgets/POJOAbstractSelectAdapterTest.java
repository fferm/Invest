package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.vaadin.widgets.POJOAbstractSelectAdapter.SelectionListener;


@SuppressWarnings("rawtypes")
public abstract class POJOAbstractSelectAdapterTest<ADAPTERCLASS extends POJOAbstractSelectAdapter> {
	protected List<TestPOJO> testData;
	protected ADAPTERCLASS adapter;


	@Before
	public void setUp() throws Exception {
		testData = TestPOJO.getTestData();

		adapter = createAdapter();
	}

	protected abstract ADAPTERCLASS createAdapter();
	protected abstract void defineVisibleData();
	protected abstract void defineIllegalColumn();
	protected abstract void defineIllegalNestedColumn();

	@SuppressWarnings("unchecked")
	@Test
	public void testSize() throws Exception {
		defineVisibleData();

		assertEquals("Empty before", 0, adapter.getUI().size());

		adapter.setData(testData);

		assertEquals("Size after", testData.size(), adapter.getUI().size());

		// Call again and check size 
		List<TestPOJO> testDataSecond = TestPOJO.getTestDataSecond();
		adapter.setData(testDataSecond);
		assertEquals("Size after second", testDataSecond.size(), adapter.getUI().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetData() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		List<TestPOJO> dataFromTable = adapter.getData();
		assertArrayEquals(testData.toArray(), dataFromTable.toArray());

		// Check that it is unmodifiable
		try {
			dataFromTable.add(null);
			fail("Adding items to the data from table should give exception");
		} catch (UnsupportedOperationException e) {
			// OK
		} catch (Exception e) {
			fail("Got exception.  Msg: " + e.getMessage() + "     type was: " + e.getClass().getName());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSelection() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		assertNull("Null selection before", adapter.getSelectedData());

		TestPOJO toSelect = testData.get(0);
		adapter.select(toSelect);

		assertEquals("selectedItem", toSelect, adapter.getSelectedData());

		adapter.select(null);
		assertNull("Null selection when select(null) is called", adapter.getSelectedData());

		adapter.select(toSelect);
		adapter.unselect();

		assertNull("Null selection when unselect is called", adapter.getSelectedData());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSelectionFiresSelectionEvent() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		Integer idxOfItemToSelect = 1;
		TestPOJO selectedPOJO = testData.get(idxOfItemToSelect);

		SelectionListener<TestPOJO> listener = mock(SelectionListener.class);
		adapter.addSelectionListener(listener);

		adapter.getUI().select(idxOfItemToSelect);
		verify(listener).onSelect(1, selectedPOJO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUnselectionFiresSelectionEventWithNullParameters() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		Integer idxOfItemToSelect = 1;

		adapter.getUI().select(idxOfItemToSelect);

		SelectionListener<TestPOJO> listener = mock(SelectionListener.class);
		adapter.addSelectionListener(listener);

		adapter.getUI().unselect(idxOfItemToSelect);
		verify(listener).onSelect(null,  null);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testSorting_sortOrderAfterSetData() throws Exception {
		defineVisibleData();
		List<TestPOJO> testData = getUnsortedTestData();

		adapter.setData(testData);
		adapter.setSortOrder("strAttribute");

		assessSortOrder();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSorting_sortOrderBeforeSetData() throws Exception {
		defineVisibleData();
		List<TestPOJO> testData = getUnsortedTestData();

		adapter.setSortOrder("strAttribute");
		adapter.setData(testData);

		assessSortOrder();
	}

	@SuppressWarnings("unchecked")
	private void assessSortOrder() {
		List<TestPOJO> dataFromTable = adapter.getData();

		String prev = null;
		boolean onFirst = false;
		for (TestPOJO testPOJO : dataFromTable) {
			String current = testPOJO.getStrAttribute();

			if (!onFirst) assertNotNull("Shouldn't start with a null value", current);
			else if (prev != null && current != null) assertTrue(current.compareTo(prev) >= 1);
			else if (prev != null && current == null) {} // do nothing
			else if (prev == null && current != null) fail("a value cannot be after null.  Value = " + current);

			onFirst = true;
			prev = current;
		}
	}

	private List<TestPOJO> getUnsortedTestData() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO(null));
		ret.add(new TestPOJO("E"));
		ret.add(new TestPOJO("C"));
		ret.add(new TestPOJO("A"));
		ret.add(new TestPOJO(null));
		ret.add(new TestPOJO("D"));
		ret.add(new TestPOJO("B"));

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSetDataAfterDefiningVisibleDataNotGiveRuntimeException () throws Exception {
		defineVisibleData();
		adapter.setData(testData);
	}

	@Test(expected=POJOUIException.class)
	public void testDefiningIllegalDataShouldGiveException() throws Exception {
		defineIllegalColumn();
	}

	@Test(expected=POJOUIException.class)
	public void testDefiningIllegalNestedDataShouldGiveException() throws Exception {
		defineIllegalNestedColumn();
	}

}
