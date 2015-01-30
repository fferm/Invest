package se.fermitet.vaadin.widgets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.vaadin.widgets.POJOTableAdapter.SelectionListener;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class POJOTableAdapterTest {
	private POJOTableAdapter<TestPOJO> tableAdapter;

	@Before
	public void setUp() throws Exception {
		tableAdapter = new POJOTableAdapter<TestPOJO>(TestPOJO.class);
	}

	@Test
	public void testSimpleConstructor() throws Exception {
		POJOTableAdapter<TestPOJO> table = new POJOTableAdapter<TestPOJO>(TestPOJO.class);

		assertNotNull("not null", table);
	}

	@Test
	public void testConstructorWithCaption() throws Exception {
		String myCaption = "MY TEST CAPTION";
		POJOTableAdapter<TestPOJO> tableAdapter = new POJOTableAdapter<TestPOJO>(TestPOJO.class, myCaption);

		assertEquals(myCaption, tableAdapter.getTable().getCaption());
	}

	@Test
	public void testGetTable() throws Exception {
		Table table = tableAdapter.getTable();

		assertNotNull(table);
	}

	@Test
	public void testSetDataAfterColumnDefinitionShouldNotGiveRuntimeException () throws Exception {
		setColumnDefinitions();
		tableAdapter.setData(getTestData());
	}

	@Test
	public void testHeaderHandling() throws Exception {
		String propWithText = "intAttribute";
		String propText = "Normal text";
		String propWithoutText = "strAttribute";
		
		List<ColumnDefinition> defs = new ArrayList<ColumnDefinition>();
		defs.add(new ColumnDefinition(propWithText, propText));
		defs.add(new ColumnDefinition(propWithoutText, null));
		tableAdapter.setColumns(defs);
		tableAdapter.setData(getTestData());
		
		assertEquals("normal text", propText, tableAdapter.getTable().getColumnHeader(propWithText));
		assertEquals("empty", "", tableAdapter.getTable().getColumnHeader(propWithoutText));
	}
	
	@Test
	public void testTableSize() throws Exception {
		setColumnDefinitions();

		assertEquals("Empty before", 0, tableAdapter.getTable().size());

		List<TestPOJO> testData = getTestData();
		tableAdapter.setData(testData);

		assertEquals("Size after", testData.size(), tableAdapter.getTable().size());

		// Call again and check size 
		List<TestPOJO> testDataSecond = getTestDataSecond();
		tableAdapter.setData(testDataSecond);
		assertEquals("Size after second", testDataSecond.size(), tableAdapter.getTable().size());
	}

	@Test
	public void testDisplayedData() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setData(testData);

		int i = 0;
		for (Object itemId : tableAdapter.getTable().getItemIds()) {
			Item item = tableAdapter.getTable().getItem(itemId);
			TestPOJO pojo = testData.get(i);

			for (Object propId : item.getItemPropertyIds()) {
				Object intermediateObject = pojo;
				for (StringTokenizer tokenizer = new StringTokenizer(propId.toString(), "."); tokenizer.hasMoreTokens(); ) {
					String propName = tokenizer.nextToken();
					
					String getterName = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1, propName.length());
					Method getter = intermediateObject.getClass().getMethod(getterName);
					intermediateObject = getter.invoke(intermediateObject);
					
					if (intermediateObject == null) break;
				}
				
				Object pojoValue = intermediateObject;

				Property<?> prop = item.getItemProperty(propId);
				Object tableValue = prop.getValue();

				assertEquals("propertyId: " + propId, pojoValue, tableValue);
			}
			i++;
		}
	}

	@Test
	public void testGetData() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setData(testData);

		List<TestPOJO> dataFromTable = tableAdapter.getData();
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

	private void setColumnDefinitions() {
		List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
		colDefs.add(new ColumnDefinition("strAttribute", "String attribute"));
		colDefs.add(new ColumnDefinition("intAttribute", "Int attribute"));
		colDefs.add(new ColumnDefinition("linkedAttribute.stringAttribute", "Linked attribute"));
		
		tableAdapter.setColumns(colDefs);
	}

	@Test
	public void testSelection() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setData(testData);

		assertNull("Null selection before", tableAdapter.getSelectedData());

		TestPOJO toSelect = testData.get(0);
		tableAdapter.select(toSelect);

		assertEquals("selectedItem", toSelect, tableAdapter.getSelectedData());

		tableAdapter.select(null);
		assertNull("Null selection when select(null) is called", tableAdapter.getSelectedData());

		tableAdapter.select(toSelect);
		tableAdapter.unselect();

		assertNull("Null selection when unselect is called", tableAdapter.getSelectedData());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSelectionFiresSelectionEvent() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setData(testData);
		
		Integer idxOfItemToSelect = 1;
		TestPOJO selectedPOJO = testData.get(idxOfItemToSelect);

		SelectionListener<TestPOJO> listener = mock(SelectionListener.class);
		tableAdapter.addSelectionListener(listener);

		tableAdapter.getTable().select(idxOfItemToSelect);
		verify(listener).onSelect(1, selectedPOJO);
	}
	
	@Test
	public void testUnselectionFiresSelectionEventWithNullParameters() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setData(testData);
		
		Integer idxOfItemToSelect = 1;

		tableAdapter.getTable().select(idxOfItemToSelect);

		@SuppressWarnings("unchecked")
		SelectionListener<TestPOJO> listener = mock(SelectionListener.class);
		tableAdapter.addSelectionListener(listener);

		tableAdapter.getTable().unselect(idxOfItemToSelect);
		verify(listener).onSelect(null,  null);
	}

	private List<TestPOJO> getTestData() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO().setStrAttribute("Str 1").setIntAttribute(1).setLinkedAttribute(new TestPOJO_Linked("Linked 1")));
		ret.add(new TestPOJO().setStrAttribute("Str 2").setIntAttribute(2).setLinkedAttribute(new TestPOJO_Linked("Linked 2")));
		ret.add(new TestPOJO().setStrAttribute( null  ).setIntAttribute(3).setLinkedAttribute(new TestPOJO_Linked("Linked 3")));
		ret.add(new TestPOJO().setStrAttribute("Str 4").setIntAttribute(4).setLinkedAttribute(null                           ));

		return ret;
	}

	private List<TestPOJO> getTestDataSecond() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO().setStrAttribute("Second 1").setIntAttribute(100).setLinkedAttribute(new TestPOJO_Linked("Second linked 1")));

		return ret;

	}

}
