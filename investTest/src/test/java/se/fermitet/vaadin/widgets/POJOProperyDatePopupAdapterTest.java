package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.ui.PopupDateField;

@SuppressWarnings("rawtypes")
public class POJOProperyDatePopupAdapterTest extends POJOAbstractPropertyAdapterTest<POJOPropertyDatePopupAdapter, PopupDateField> {

	@Before
	public void setUp() {
		this.pojoClass = TestPOJO.class;
		this.propertyName = "dateAttribute";
		this.caption = "my caption";
		
		super.setUp();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBindToProperty() throws Exception {
		LocalDate date = LocalDate.now();
		TestPOJO pojo = new TestPOJO(date);

		adapter.bindToProperty(pojo, getPropertyName());
		
		assertEquals("read from field", date.toDate(), ui.getValue());
		
		LocalDate newDate = LocalDate.now().minusDays(100);
		ui.setValue(newDate.toDate());
		
		assertEquals("field updated, read from value", newDate.toDate(), ui.getValue());
	}
	

	@SuppressWarnings("unchecked")
	@Override
	protected POJOPropertyDatePopupAdapter createAdapter() {
		return new POJOPropertyDatePopupAdapter<TestPOJO>((Class<TestPOJO>) pojoClass, caption);
	}

	@Override
	protected Class<?> getPojoClass() {
		return TestPOJO.class;
	}

	@Override
	protected String getCaption() {
		return "a caption in " + this.getClass().getName();
	}

	@Override
	protected String getPropertyName() {
		return "dateAttribute";
	}
}
