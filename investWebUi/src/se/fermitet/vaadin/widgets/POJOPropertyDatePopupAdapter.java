package se.fermitet.vaadin.widgets;

import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.PopupDateField;

public class POJOPropertyDatePopupAdapter<POJOCLASS> extends POJOAbstractPropertyAdapter<POJOCLASS, PopupDateField>{
	private static final long serialVersionUID = -5381555555854972062L;

	public POJOPropertyDatePopupAdapter(Class<POJOCLASS> pojoClass,	String propertyName, String caption) {
		super(pojoClass, propertyName, caption);
	}

	@Override
	protected PopupDateField createUI(String caption) {
		return new PopupDateField(caption);
	}

	@Override
	protected void fixConverter(BeanItem<POJOCLASS> item) {
		Property<?> prop = item.getItemProperty(this.getPropertyName());
		Class<?> clz = prop.getType();
		
		if (clz.equals(LocalDate.class)) {
			this.ui.setConverter(new LocalDateConverter());
		}
	}

	private class LocalDateConverter implements Converter<Date, LocalDate> {
		private static final long serialVersionUID = -3288012479071159027L;

		@Override
		public LocalDate convertToModel(Date value, Class<? extends LocalDate> targetType, Locale locale) throws ConversionException {
			return LocalDate.fromDateFields(value);
		}

		@Override
		public Date convertToPresentation(LocalDate value, Class<? extends Date> targetType, Locale locale)	throws ConversionException {
			return value.toDate();
		}

		@Override
		public Class<LocalDate> getModelType() {
			return LocalDate.class;
		}

		@Override
		public Class<Date> getPresentationType() {
			return Date.class;
		}
		
	}
}
