package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.junit.Test;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.TextField;

@SuppressWarnings("rawtypes")
public class POJOPropertyTextFieldAdapterTest extends POJOAbstractPropertyAdapterTest<POJOPropertyTextFieldAdapter, TextField> {

	@SuppressWarnings("unchecked")
	@Override
	protected POJOPropertyTextFieldAdapter<TestPOJO> createAdapter() {
		return new POJOPropertyTextFieldAdapter<TestPOJO>((Class<TestPOJO>) pojoClass, propertyName, caption);
	}

	@Test
	public void testConstructor() throws Exception {
		@SuppressWarnings("unchecked")
		POJOPropertyTextFieldAdapter<TestPOJO> adapter = new POJOPropertyTextFieldAdapter<TestPOJO>((Class<TestPOJO>) pojoClass, propertyName, caption);
		
		assertNotNull(adapter);
	}
	
	@Test
	public void testTextFieldSetups() throws Exception {
		assertEquals("null representation", "", ui.getNullRepresentation());
		assertTrue("null setting allowed", ui.isNullSettingAllowed());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBindToData() throws Exception {
		String strData = "TestData";
		TestPOJO testData = new TestPOJO(strData, 0, null);
		
		adapter.bindToData(testData);
		
		assertEquals("read from field", strData, ui.getValue());
		
		String newData = "new test data";
		ui.setValue(newData);
		
		assertEquals("field updated, read from value", newData, ui.getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testIntegerData() throws Exception {
		int intData = -10;
		TestPOJO testData = new TestPOJO(null, intData, null);
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO>((Class<TestPOJO>) pojoClass, "intAttribute", caption);
		
		adapter.bindToData(testData);
		
		assertEquals("" + intData, ((AbstractField<?>) adapter.getUI()).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMoneyData_SEK_withDecimals() throws Exception {
		Money money = Money.parse("SEK 200.25");
		
		TestPOJO pojo = new TestPOJO(money);
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO>(TestPOJO.class, "moneyAttribute", null);
		
		adapter.bindToData(pojo);
		
		assertEquals("with decimals", "200,25", ((AbstractField<?>) adapter.getUI()).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMoneyData_SEK_noDecimals() throws Exception {
		Money money = Money.parse("SEK 201");
		
		TestPOJO pojo = new TestPOJO(money);
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO>(TestPOJO.class, "moneyAttribute", null);
		
		adapter.bindToData(pojo);
		
		assertEquals("with decimals", "201,00",((AbstractField<?>) adapter.getUI()).getValue());
	}

	@Override
	protected Class<TestPOJO> getPojoClass() {
		return TestPOJO.class;
	}

	@Override
	protected String getCaption() {
		return "a caption";
	}

	@Override
	protected String getPropertyName() {
		return "strAttribute";
	}
	

}
