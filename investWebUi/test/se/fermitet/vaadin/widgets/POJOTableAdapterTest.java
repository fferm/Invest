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

	@Test(expected=POJOTableAdapterException.class)
	public void testSetDataBeforeColumnDefinitionShouldGiveRuntimeException() throws Exception {
		tableAdapter.setdData(getTestData());
	}

	@Test
	public void testSetDataAfterColumnDefinitionShouldNotGiveRuntimeException () throws Exception {
		setColumnDefinitions();
		tableAdapter.setdData(getTestData());
	}

//	@Test(expected=POJOTableAdapterException.class)
//	public void testExceptionWhenGetterNameDoesNotCorrespondToRealMethod() {
//		tableAdapter.addColumn("test", "test");
//	}
//
//	@Test(expected=POJOTableAdapterException.class)
//	public void testTwoEqualDataObjectsAreNotAllowed() {
//		setColumnDefinitions();
//		List<TestPOJO> data = new ArrayList<TestPOJO>();
//
//		TestPOJO d1 = new TestPOJO().setIntAttribute(1).setStrAttribute("STR");
//		TestPOJO d2 = new TestPOJO().setIntAttribute(1).setStrAttribute("STR");
//
//		assertEquals("d1 and d2 must be equal for the test to pass", d1, d2);
//
//		data.add(d1);
//		data.add(d2);
//
//		tableAdapter.setdData(data);
//	}
	
//	@Test(expected=POJOTableAdapterException.class)
//	public void testNullDataObjectsAreNotAllowed() throws Exception {
//		setColumnDefinitions();
//		List<TestPOJO> data = getTestData();
//		data.add(null);
//		
//		tableAdapter.setdData(data);
//	}

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
//		tableAdapter.addColumn("getStrAttribute", "String attribute");
//		tableAdapter.addColumn("getIntAttribute", "Int attribute");
//		tableAdapter.addColumn("getObjAttribute", "Object attribute");
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

		ret.add(new TestPOJO().setStrAttribute("Str 1").setIntAttribute(1).setObjAttribute(new TestPOJO_Linked("Linked 1")));
		ret.add(new TestPOJO().setStrAttribute("Str 2").setIntAttribute(2).setObjAttribute(new TestPOJO_Linked("Linked 2")));
		ret.add(new TestPOJO().setStrAttribute( null  ).setIntAttribute(3).setObjAttribute(new TestPOJO_Linked("Linked 3")));
		ret.add(new TestPOJO().setStrAttribute("Str 4").setIntAttribute(4).setObjAttribute(null                           ));

		return ret;
	}

	private List<TestPOJO> getTestDataSecond() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO().setStrAttribute("Second 1").setIntAttribute(100).setObjAttribute(new TestPOJO_Linked("Second linked 1")));

		return ret;

	}

}

class TestPOJO {
	private String strAttribute;
	private int intAttribute;
	private Object objAttribute;

	public String getStrAttribute() {
		return strAttribute;
	}

	public TestPOJO setStrAttribute(String strAttribute) {
		this.strAttribute = strAttribute;
		return this;
	}
	public int getIntAttribute() {
		return intAttribute;
	}
	public TestPOJO setIntAttribute(int intAttribute) {
		this.intAttribute = intAttribute;
		return this;
	}
	public Object getObjAttribute() {
		return objAttribute;
	}
	public TestPOJO setObjAttribute(Object objAttribute) {
		this.objAttribute = objAttribute;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + intAttribute;
		result = prime * result
				+ ((objAttribute == null) ? 0 : objAttribute.hashCode());
		result = prime * result
				+ ((strAttribute == null) ? 0 : strAttribute.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestPOJO other = (TestPOJO) obj;
		if (intAttribute != other.intAttribute)
			return false;
		if (objAttribute == null) {
			if (other.objAttribute != null)
				return false;
		} else if (!objAttribute.equals(other.objAttribute))
			return false;
		if (strAttribute == null) {
			if (other.strAttribute != null)
				return false;
		} else if (!strAttribute.equals(other.strAttribute))
			return false;
		return true;
	}


}

class TestPOJO_Linked {
	@SuppressWarnings("unused")
	private String string;

	public TestPOJO_Linked(String string) {
		this.string = string;
	}
}
