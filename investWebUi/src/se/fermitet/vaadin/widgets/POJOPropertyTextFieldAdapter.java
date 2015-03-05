package se.fermitet.vaadin.widgets;

import java.text.NumberFormat;
import java.util.Locale;

import org.joda.money.Money;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.ui.TextField;

public class POJOPropertyTextFieldAdapter<POJOCLASS> extends POJOAbstractPropertyAdapter<POJOCLASS, TextField> {
	private static final long serialVersionUID = -8579073716821506905L;

	public POJOPropertyTextFieldAdapter(Class<POJOCLASS> pojoClass, String propertyName, String caption) {
		super(pojoClass, propertyName, caption);
		this.pojoClass = pojoClass;

		initialize();
	}

	private void initialize() {
		this.ui.setNullRepresentation("");
		this.ui.setNullSettingAllowed(true);
	}

	@Override
	protected TextField createUI(String caption) {
		return new TextField(caption);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void fixConverter(BeanItem<POJOCLASS> item) {
		Property<?> prop = item.getItemProperty(this.getPropertyName());
		Class<?> clz = prop.getType();

		if (clz.equals(Integer.class)) {
			this.ui.setConverter((Converter) new StringToIntegerConverter());
		} else if (clz.equals(Money.class)) {
			this.ui.setConverter((Converter) new MoneyConverter());
		}
	}


	private class MoneyConverter implements Converter<String, Money> {
		private static final long serialVersionUID = -6251536302559035538L;

		@Override
		public Money convertToModel(String value, Class<? extends Money> targetType, Locale locale)	throws ConversionException {
			throw new UnsupportedOperationException("unimplemented");
		}

		@Override
		public String convertToPresentation(Money value, Class<? extends String> targetType, Locale locale)	throws ConversionException {
			NumberFormat formatter = NumberFormat.getInstance();
			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);

			return formatter.format(value.getAmount());
		}

		@Override
		public Class<Money> getModelType() {
			return Money.class;
		}

		@Override
		public Class<String> getPresentationType() {
			return String.class;
		}

	}
}




