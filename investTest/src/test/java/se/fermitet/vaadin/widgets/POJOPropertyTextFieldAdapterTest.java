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
	protected POJOPropertyTextFieldAdapter<TestPOJO, Object> createAdapter() {
		return new POJOPropertyTextFieldAdapter<TestPOJO, Object>((Class<TestPOJO>) pojoClass, caption);
	}

	@Test
	public void testConstructor() throws Exception {
		@SuppressWarnings("unchecked")
		POJOPropertyTextFieldAdapter<TestPOJO, Object> adapter = new POJOPropertyTextFieldAdapter<TestPOJO, Object>((Class<TestPOJO>) pojoClass, caption);
		
		assertNotNull(adapter);
	}
	
	@Test
	public void testTextFieldSetups() throws Exception {
		assertEquals("null representation", "", ui.getNullRepresentation());
		assertTrue("null setting allowed", ui.isNullSettingAllowed());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBindToProperty() throws Exception {
		String strData = "TestData";
		TestPOJO testData = new TestPOJO(strData, 0, null);
		
		adapter.bindToProperty(testData, getPropertyName());
		
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
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO, Integer>((Class<TestPOJO>) pojoClass, caption);
		
		adapter.bindToProperty(testData, "intAttribute");
		
		assertEquals("" + intData, ((AbstractField<?>) adapter.getUI()).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMoneyData_SEK_withDecimals() throws Exception {
		Money money = Money.parse("SEK 200.25");
		
		TestPOJO pojo = new TestPOJO(money);
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO, Money>(TestPOJO.class, null);
		
		adapter.bindToProperty(pojo, "moneyAttribute");
		
		assertEquals("with decimals", "200,25", ((AbstractField<?>) adapter.getUI()).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMoneyData_SEK_noDecimals() throws Exception {
		Money money = Money.parse("SEK 201");
		
		TestPOJO pojo = new TestPOJO(money);
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO, Money>(TestPOJO.class, null);
		
		adapter.bindToProperty(pojo, "moneyAttribute");
		
		assertEquals("with decimals", "201,00",((AbstractField<?>) adapter.getUI()).getValue());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAndSetValue_money() throws Exception {
		TestPOJO pojo = new TestPOJO();
		
		adapter = new POJOPropertyTextFieldAdapter<TestPOJO, Money>(TestPOJO.class, null);
		adapter.bindToProperty(pojo, "moneyAttribute");
		
		assertNull("null before", adapter.getValue());
		assertNull("null before, from UI", adapter.getUI().getValue());
		
		Money newValue = Money.parse("SEK 201");
		
		adapter.setValue(newValue);
		assertEquals("after set, from getValue", newValue, adapter.getValue());
		assertEquals("after set, from UI", newValue, adapter.getUI().getConverter().convertToModel(adapter.getUI().getValue(), null, null));
		
		adapter.setValue(null);
		assertNull("null again", adapter.getValue());
		assertNull("null again, from UI", adapter.getUI().getValue());
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
