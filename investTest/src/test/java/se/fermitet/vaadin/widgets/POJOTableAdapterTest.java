package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.Test;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;

@SuppressWarnings("rawtypes")
public class POJOTableAdapterTest extends POJOAbstractSelectAdapterTest<POJOTableAdapter>{

	protected POJOTableAdapter<TestPOJO> createAdapter() {
		return new POJOTableAdapter<TestPOJO>(TestPOJO.class);
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
		Table table = adapter.getTable();

		assertNotNull(table);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHeaderHandling() throws Exception {
		String propWithText = "intAttribute";
		String propText = "Normal text";
		String propWithoutText = "strAttribute";
		
		List<ColumnDefinition> defs = new ArrayList<ColumnDefinition>();
		defs.add(new ColumnDefinition(propWithText, propText));
		defs.add(new ColumnDefinition(propWithoutText, null));
		adapter.setVisibleData(defs);
		adapter.setData(testData);
		
		assertEquals("normal text", propText, adapter.getTable().getColumnHeader(propWithText));
		assertEquals("empty", "", adapter.getTable().getColumnHeader(propWithoutText));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayedData() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		int i = 0;
		for (Object itemId : adapter.getTable().getItemIds()) {
			Item item = adapter.getTable().getItem(itemId);
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

	@SuppressWarnings("unchecked")
	protected void defineVisibleData() {
		List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
		colDefs.add(new ColumnDefinition("strAttribute", "String attribute"));
		colDefs.add(new ColumnDefinition("intAttribute", "Int attribute"));
		colDefs.add(new ColumnDefinition("linkedAttribute.stringAttribute", "Linked attribute"));
		
		adapter.setVisibleData(colDefs);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void defineIllegalColumn() {
		List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
		colDefs.add(new ColumnDefinition("ILLEGAL", null));
		
		adapter.setVisibleData(colDefs);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void defineIllegalNestedColumn() {
		List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
		colDefs.add(new ColumnDefinition("linkedAttribute.ILLEGAL", null));
		
		adapter.setVisibleData(colDefs);
	}
}
