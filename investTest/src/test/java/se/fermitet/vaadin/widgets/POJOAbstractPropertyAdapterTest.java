package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vaadin.ui.AbstractField;

@SuppressWarnings("rawtypes")
public abstract class POJOAbstractPropertyAdapterTest<ADAPTER extends POJOAbstractPropertyAdapter, UI extends AbstractField<?>> {
	protected ADAPTER adapter;
	protected UI ui;

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
		ui =  (UI) adapter.getUI();
	}

	protected abstract ADAPTER createAdapter();
	protected abstract Class<?> getPojoClass();
	protected abstract String getCaption();
	protected abstract String getPropertyName();
	
	@Test
	public void testGetters() throws Exception {
		assertEquals("Caption", caption, ui.getCaption());
		assertEquals("pojo class", pojoClass, adapter.getPojoClass());
		assertTrue("immediate", ui.isImmediate());
	}
	
	@Test
	public void testTestableBeanValidator() throws Exception {
		TestableBeanValidator val = new TestableBeanValidator(TestPOJO.class, "strAttribute");
		
		assertNotNull("not null", val);
		assertEquals("getPojoClass", TestPOJO.class, val.getPojoClass());
		assertEquals("getPropertyName", "strAttribute", val.getPropertyName());
	}
}
