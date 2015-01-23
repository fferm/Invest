package se.fermitet.vaadin.widgets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

public class POJOTableTest {
	private POJOTable<TestPOJO> table;
	
	@Before
	public void setUp() throws Exception {
		table = new POJOTable<TestPOJO>(TestPOJO.class);
	}
	
	@Test
	public void testSimpleConstructor() throws Exception {
		POJOTable<TestPOJO> table = new POJOTable<TestPOJO>(TestPOJO.class);
		
		assertNotNull("not null", table);
		assertTrue("instanceof Table", Table.class.isAssignableFrom(table.getClass()));
	}
	
	@Test
	public void testConstructorWithCaption() throws Exception {
		String myCaption = "MY TEST CAPTION";
		POJOTable<TestPOJO> table = new POJOTable<TestPOJO>(TestPOJO.class, myCaption);
		
		assertEquals(myCaption, table.getCaption());
	}

	@Test(expected=RuntimeException.class)
	public void testSetDisplayedDataBeforeColumnDefinitionShouldGiveRuntimeException() throws Exception {
		table.setDisplayedData(getTestData());
	}

	@Test
	public void testSetDisplayedDataAfterColumnDefinitionShouldNotGiveRuntimeException () throws Exception {
		setColumnDefinitions();
		table.setDisplayedData(getTestData());
	}
	
	@Test(expected=RuntimeException.class)
	public void testExceptionWhenGetterNameDoesNotCorrespondToRealMethod() {
		table.addColumn("test", "test");
	}
	
	@Test
	public void testTableSize() throws Exception {
		setColumnDefinitions();

		assertEquals("Empty before", 0, table.size());
		
		List<TestPOJO> testData= getTestData();
		table.setDisplayedData(testData);
		
		assertEquals("Size after", testData.size(), table.size());

		// Call again and check size
		List<TestPOJO> testDataSecond = getTestDataSecond();
		table.setDisplayedData(testDataSecond);
		assertEquals("Size after second", testDataSecond.size(), table.size());
	}
	
	@Test
	public void testDisplayedData() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		table.setDisplayedData(testData);
		
		int i = 0;
		for (Object itemId : table.getItemIds()) {
			Item item = table.getItem(itemId);
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
	public void testGetDisplayedData() throws Exception {
		setColumnDefinitions();
		List<TestPOJO> testData = getTestData();
		table.setDisplayedData(testData);
		
		List<TestPOJO> dataFromTable = table.getDisplayedData();
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
		table.addColumn("getStrAttribute", "String attribute");
		table.addColumn("getIntAttribute", "Int attribute");
		table.addColumn("getObjAttribute", "Object attribute");
	}

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
}

class TestPOJO_Linked {
	@SuppressWarnings("unused")
	private String string;
	
	public TestPOJO_Linked(String string) {
		this.string = string;
	}
}
