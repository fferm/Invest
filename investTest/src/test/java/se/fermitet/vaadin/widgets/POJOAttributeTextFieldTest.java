package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;

public class POJOAttributeTextFieldTest {
	
	private POJOAttributeTextField<TestPOJO> field;

	private String propertyId;
	private Class<?> pojoClass;
	private String caption;

	@Before
	public void setUp() {
		caption = "Caption";
		pojoClass = TestPOJO.class;
		propertyId = "strAttribute";
		
		field = new POJOAttributeTextField<TestPOJO>(caption, pojoClass, propertyId);
	}
	
	@Test
	public void testConstructorAndGetters() throws Exception {
		POJOAttributeTextField<TestPOJO> field = new POJOAttributeTextField<TestPOJO>(caption, pojoClass, propertyId);
		
		assertNotNull(field);
		assertTrue("instanceof", field instanceof TextField);

		assertEquals("Caption", caption, field.getCaption());
		assertEquals("pojo class", pojoClass, field.getPojoClass());
		assertEquals("prop id", propertyId, field.getPropertyId());
	}
	
	@Test
	public void testTextFieldSetups() throws Exception {
		assertTrue("immediate", field.isImmediate());
		assertEquals("null representation", "", field.getNullRepresentation());
		assertTrue("null setting allowed", field.isNullSettingAllowed());
	}
	
	@Test
	public void testValidator() throws Exception {
		boolean found = false;
		for (Validator val : field.getValidators()) {
			if (! (val instanceof TestableBeanValidator)) continue;
			
			TestableBeanValidator bVal = (TestableBeanValidator) val;

			boolean sameClass = pojoClass.equals(bVal.getPojoClass());
			boolean sameName = propertyId.equals(bVal.getPropertyName());
			
			if (sameClass && sameName && !found) found = true; 
		}
		assertTrue(found);
	}
	
	@Test
	public void testBindToData() throws Exception {
		String strData = "TestData";
		TestPOJO testData = new TestPOJO(strData, 0, null);
		
		field.bindToData(testData);
		
		assertEquals("read from field", strData, field.getValue());
		
		String newData = "new test data";
		field.setValue(newData);
		
		assertEquals("field updated, read from value", newData, field.getValue());
	}
	
	@Test
	public void testTestableBeanValidator() throws Exception {
		TestableBeanValidator val = new TestableBeanValidator(TestPOJO.class, "strAttribute");
		
		assertNotNull("not null", val);
		assertEquals("getPojoClass", TestPOJO.class, val.getPojoClass());
		assertEquals("getPropertyName", "strAttribute", val.getPropertyName());
	}
}
