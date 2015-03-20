package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.vaadin.widgets.POJOAbstractSelectAdapter.SelectionListener;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.AbstractSelect;


@SuppressWarnings("rawtypes")
public abstract class POJOAbstractSelectAdapterTest<ADAPTER extends POJOAbstractSelectAdapter> {
	protected List<TestPOJO> testData;
	protected ADAPTER adapter;


	@Before
	public void setUp() throws Exception {
		testData = TestPOJO.getTestData();

		adapter = createAdapter();
	}

	protected abstract ADAPTER createAdapter();
	protected abstract void defineVisibleData();
	protected abstract void defineIllegalColumn();
	protected abstract void defineIllegalNestedColumn();

	@SuppressWarnings("unchecked")
	@Test
	public void testSize() throws Exception {
		defineVisibleData();

		AbstractSelect ui = (AbstractSelect) adapter.getUI();

		assertEquals("Empty before", 0, ui.size());

		adapter.setData(testData);

		assertEquals("Size after", testData.size(), ui.size());

		// Call again and check size 
		List<TestPOJO> testDataSecond = TestPOJO.getTestDataSecond();
		adapter.setData(testDataSecond);
		assertEquals("Size after second", testDataSecond.size(), ui.size());
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
		assertNull("Null selection before from UI", getSelectionFromUI());

		TestPOJO toSelect = testData.get(1);
		adapter.select(toSelect);

		assertEquals("selectedItem", toSelect, adapter.getSelectedData());
		assertEquals("selectedItem from UI", toSelect, getSelectionFromUI());

		adapter.select(null);
		assertNull("Null selection when select(null) is called", adapter.getSelectedData());
		assertNull("Null selection when select(null) is called  (UI)", getSelectionFromUI());

		adapter.select(toSelect);
		adapter.unselect();

		assertNull("Null selection when unselect is called", adapter.getSelectedData());
		assertNull("Null selection when unselect is called  (UI)", getSelectionFromUI());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSelectionAfterSort() throws Exception {
		defineVisibleData();
		Collections.shuffle(testData);
		adapter.setData(testData);
		adapter.setSortOrder("strAttribute");
		
		TestPOJO toSelect = testData.get(1);
		adapter.select(toSelect);
		
		assertEquals("selectedItem", toSelect, adapter.getSelectedData());
		assertEquals("selectedItem from UI", toSelect, getSelectionFromUI());
	}
	
	private TestPOJO getSelectionFromUI() {
		BeanItem item = (BeanItem) adapter.getUI().getContainerDataSource().getItem(adapter.getUI().getValue());
		if (item == null) return null;
		
		return (TestPOJO) item.getBean();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSelectionFiresSelectionEvent() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		TestPOJO selectedPOJO = testData.get(1);

		SelectionListener<TestPOJO> listener = mock(SelectionListener.class);
		adapter.addSelectionListener(listener);

		((AbstractSelect) adapter.getUI()).select(selectedPOJO.getId());
		verify(listener).onSelect(selectedPOJO.getId(), selectedPOJO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUnselectionFiresSelectionEventWithNullParameters() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		TestPOJO selectedPOJO = testData.get(1);

		AbstractSelect ui = (AbstractSelect) adapter.getUI();
		
		ui.select(selectedPOJO.getId());

		SelectionListener<TestPOJO> listener = mock(SelectionListener.class);
		adapter.addSelectionListener(listener);

		ui.unselect(selectedPOJO.getId());
		verify(listener).onSelect(null,  null);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void testSorting_sortOrderAfterSetData() throws Exception {
		defineVisibleData();
		List<TestPOJO> testData = getUnsortedTestData();

		adapter.setData(testData);
		adapter.setSortOrder("strAttribute");

		assessSimpleSortOrder();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSorting_sortOrderBeforeSetData() throws Exception {
		defineVisibleData();
		List<TestPOJO> testData = getUnsortedTestData();

		adapter.setSortOrder("strAttribute");
		adapter.setData(testData);

		assessSimpleSortOrder();
	}
	
	@SuppressWarnings("unchecked")
	private void assessSimpleSortOrder() {
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

		ret.add(new TestPOJO((String) null));
		ret.add(new TestPOJO("E"));
		ret.add(new TestPOJO("C"));
		ret.add(new TestPOJO("A"));
		ret.add(new TestPOJO((String) null));
		ret.add(new TestPOJO("D"));
		ret.add(new TestPOJO("B"));

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSorting_multipleAttributes() throws Exception {
		defineVisibleData();
		
		TestPOJO t1 = new TestPOJO("D", 1, null);
		TestPOJO t2 = new TestPOJO("B", 3, null);
		TestPOJO t3 = new TestPOJO(null, 3, null);
		TestPOJO t4 = new TestPOJO("A", 5, null);
		TestPOJO t5 = new TestPOJO("C", 5, null);
		TestPOJO t6 = new TestPOJO("E", 5, null);
		TestPOJO t7 = new TestPOJO(null, 5, null);

		List<TestPOJO> expected = new ArrayList<TestPOJO>();
		expected.add(t1);
		expected.add(t2);
		expected.add(t3);
		expected.add(t4);
		expected.add(t5);
		expected.add(t6);
		expected.add(t7);
		
		List<TestPOJO> shuffled = new ArrayList<TestPOJO>(expected);
		Collections.shuffle(shuffled);
		
		List<String> sortOrder = new ArrayList<String>();
		sortOrder.add("intAttribute");
		sortOrder.add("strAttribute");
		
		adapter.setSortOrder(sortOrder);
		adapter.setData(shuffled);
		
		List<TestPOJO> dataFromTable = adapter.getData();
		
		assertArrayEquals(expected.toArray(), dataFromTable.toArray());
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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSelectionAffectsBoundData() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		TestPOJO_LinkedFrom myPOJO = new TestPOJO_LinkedFrom();
		assertNull("After constructor", myPOJO.getTestPOJO());
		
		adapter.bindSelectionToProperty(myPOJO, "testPOJO");
		assertNull("After bind", myPOJO.getTestPOJO());
		
		TestPOJO toSelect = testData.get(2);
		
		adapter.select(toSelect);
		assertEquals("after select", toSelect, myPOJO.getTestPOJO());
		
		adapter.unselect();
		assertNull("after unselect", myPOJO.getTestPOJO());
	}
}
