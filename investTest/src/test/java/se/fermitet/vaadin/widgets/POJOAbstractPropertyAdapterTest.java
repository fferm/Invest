package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;

@SuppressWarnings("rawtypes")
public abstract class POJOAbstractPropertyAdapterTest<ADAPTERCLASS extends POJOAbstractPropertyAdapter, UICLASS extends AbstractField<?>> {
	protected ADAPTERCLASS adapter;
	protected UICLASS ui;

	protected String propertyName;
	protected Class<?> pojoClass;
	protected String caption;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		propertyName = getPropertyName();
		caption = getCaption();
		pojoClass = getPojoClass();

		adapter =  createAdapter();
		ui =  (UICLASS) adapter.getUI();
	}

	protected abstract ADAPTERCLASS createAdapter();
	protected abstract Class<?> getPojoClass();
	protected abstract String getCaption();
	protected abstract String getPropertyName();
	
	@Test
	public void testGetters() throws Exception {
		assertEquals("Caption", caption, ui.getCaption());
		assertEquals("pojo class", pojoClass, adapter.getPojoClass());
		assertEquals("prop id", propertyName, adapter.getPropertyName());
		assertTrue("immediate", adapter.ui.isImmediate());
	}
	
	@Test
	public void testValidator() throws Exception {
		boolean found = false;
		for (Validator val : ui.getValidators()) {
			if (! (val instanceof TestableBeanValidator)) continue;
			
			TestableBeanValidator bVal = (TestableBeanValidator) val;

			boolean sameClass = pojoClass.equals(bVal.getPojoClass());
			boolean sameName = propertyName.equals(bVal.getPropertyName());
			
			if (sameClass && sameName && !found) found = true; 
		}
		assertTrue(found);
	}
	
	@Test
	public void testTestableBeanValidator() throws Exception {
		TestableBeanValidator val = new TestableBeanValidator(TestPOJO.class, "strAttribute");
		
		assertNotNull("not null", val);
		assertEquals("getPojoClass", TestPOJO.class, val.getPojoClass());
		assertEquals("getPropertyName", "strAttribute", val.getPropertyName());
	}
}
