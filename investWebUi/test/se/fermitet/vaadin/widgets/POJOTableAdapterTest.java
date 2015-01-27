package se.fermitet.vaadin.widgets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		tableAdapter.setdData(getTestData());
	}

	@Test(expected=POJOTableAdapterException.class)
	public void testExceptionWhenGetterNameDoesNotCorrespondToRealMethod() {
		List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
		colDefs.add(new ColumnDefinition("test", null));
		
		tableAdapter.setColumns(colDefs);
	}

	@Test
	public void testNullObjectHandling() throws Exception {
		fail("unimplemented");
	}
	
	@Test
	public void testNullHeaderHandling() throws Exception {
		fail("unimplemented");
	}
	
	@Test
	public void testTableSize() throws Exception {
		setColumnDefinitions();

		assertEquals("Empty before", 0, tableAdapter.getTable().size());

		List<TestPOJO> testData= getTestData();
		tableAdapter.setdData(testData);

		assertEquals("Size after", testData.size(), tableAdapter.getTable().size());

		// Call again and check size
		List<TestPOJO> testDataSecond = getTestDataSecond();
		tableAdapter.setdData(testDataSecond);
		assertEquals("Size after second", testDataSecond.size(), tableAdapter.getTable().size());
	}

	@Test
	public void testDisplayedData() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setdData(testData);

		int i = 0;
		for (Object itemId : tableAdapter.getTable().getItemIds()) {
			Item item = tableAdapter.getTable().getItem(itemId);
			TestPOJO pojo = testData.get(i);

			for (Object propId : item.getItemPropertyIds()) {
				Method getter = TestPOJO.class.getMethod(propId.toString());
				Object pojoValue = getter.invoke(pojo);

				Property<?> prop = item.getItemProperty(propId);
				Object tableValue = prop.getValue();

				assertEquals(pojoValue, tableValue);
			}
			i++;
		}
	}

	@Test
	public void testGetData() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setdData(testData);

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
		
		// TODO remove this comment out, test linked attribute
//		colDefs.add(new ColumnDefinition("objAttribute.string", "Linked attribute"));
		
		tableAdapter.setColumns(colDefs);
	}

	@Test
	public void testSelection() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		tableAdapter.setdData(testData);

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

	//	@Test
	//	public void testSelectionFiresSelectionEvent() throws Exception {
	//		setColumnDefinitions();
	//		List<TestPOJO> testData = getTestData();
	//		tableAdapter.setdData(testData);
	//
	//		tableAdapter.addSelectionListener((POJOTableAdapter.SelectionListener) l -> {
	//			// Do something
	//		});
	//		
	//		tableAdapter.getTable().select(itemId);
	//	}

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

class TestPOJO {
	private String strAttribute;
	private int intAttribute;
	private TestPOJO_Linked linkedAttribute;

	
	public int getIntAttribute() {
		return intAttribute;
	}
	public TestPOJO setIntAttribute(int intAttribute) {
		this.intAttribute = intAttribute;
		return this;
	}
	public String getStrAttribute() {
		return strAttribute;
	}
	public TestPOJO setStrAttribute(String strAttribute) {
		this.strAttribute = strAttribute;
		return this;
	}
	public TestPOJO_Linked getLinkedAttribute() {
		return linkedAttribute;
	}
	public TestPOJO setLinkedAttribute(TestPOJO_Linked linkedAttribute) {
		this.linkedAttribute = linkedAttribute;
		return this;
	}
}

class TestPOJO_Linked {
	private String string;

	public TestPOJO_Linked(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
}
