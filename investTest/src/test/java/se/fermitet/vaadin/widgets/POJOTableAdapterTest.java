package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.joda.money.Money;
import org.junit.Test;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
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

		assertEquals(myCaption, tableAdapter.getUI().getCaption());
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
		
		assertEquals("normal text", propText, ((Table) adapter.getUI()).getColumnHeader(propWithText));
		assertEquals("empty", "", ((Table) adapter.getUI()).getColumnHeader(propWithoutText));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayedData() throws Exception {
		defineVisibleData();
		adapter.setData(testData);

		int i = 0;
		for (Object itemId : adapter.getUI().getItemIds()) {
			Item item = adapter.getUI().getItem(itemId);
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
	@Test
	public void testMoneyDisplay() throws Exception {
		List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
		String colName = "moneyAttribute";
		colDefs.add(new ColumnDefinition(colName, "Money"));
		adapter.setVisibleData(colDefs);
		
		String[] parseStrings = new String[]{"SEK 200", "SEK 200.25", "SEK 1010", "SEK -10"};
		String[] expecteds    = new String[]{"200,00" , "200,25",     "1 010,00", "-10,00"};

		List<TestPOJO> testData = new ArrayList<TestPOJO>();
		for (int i = 0; i < parseStrings.length; i++) {
			testData.add(new TestPOJO(Money.parse(parseStrings[i])));
		}
		adapter.setData(testData);
		
		Table ui = (Table) adapter.getUI();
		int row = 0;
		for(Object itemId : ui.getItemIds()) {
			Item item = ui.getItem(itemId);
			for (int col = 0; col < ui.getVisibleColumns().length; col++) {
				Object propId = ui.getVisibleColumns()[col];
				if (! propId.equals(colName)) continue;
				
				Object propValue = item.getItemProperty(propId).getValue();
				
				String displayedValue;
				Converter<String, Object> converter = ui.getConverter(propId);
				if (converter != null) displayedValue = converter.convertToPresentation(propValue, String.class, Locale.getDefault());
				else displayedValue = propValue.toString();
				
				assertEquals("Row " + row, expecteds[row], displayedValue);
			}
			row++;
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
