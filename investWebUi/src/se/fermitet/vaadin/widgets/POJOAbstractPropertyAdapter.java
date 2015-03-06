package se.fermitet.vaadin.widgets;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.AbstractField;

abstract class POJOAbstractPropertyAdapter<POJOCLASS, UICLASS extends AbstractField<?>> implements POJOAdapter<POJOCLASS, UICLASS> {
	private static final long serialVersionUID = 4600062141995002807L;

	private POJOAdapterHelper<POJOCLASS, UICLASS> pojoAdapter;

	private BeanValidator validator;

	POJOAbstractPropertyAdapter(Class<POJOCLASS> pojoClass, UICLASS ui) {
		super();
		
		this.pojoAdapter = new POJOAdapterHelper<POJOCLASS, UICLASS>(ui, pojoClass);
	}
	
	public void bindToProperty(POJOCLASS data, String propertyName) {
		fixValidator(propertyName);
		
		BeanItem<POJOCLASS> item = new BeanItem<POJOCLASS>(data);
		Property<?> prop = item.getItemProperty(propertyName);
				
		fixConverter(prop, propertyName);
		
		this.getUI().setPropertyDataSource(prop);
	}
	
	private void fixValidator(String propertyName) {
		this.validator = new TestableBeanValidator(getPojoClass(), propertyName);
		
		this.getUI().removeAllValidators();
		this.getUI().addValidator(validator);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fixConverter(Property<?> prop, String propertyName) {
		Class<?> clz = prop.getType();

		if (clz.equals(Integer.class)) {
			this.getUI().setConverter((Converter) new StringToIntegerConverter());
		} else if (clz.equals(Money.class)) {
			this.getUI().setConverter((Converter) new MoneyConverter());
		}  else if (clz.equals(LocalDate.class)) {
			this.getUI().setConverter((Converter) new LocalDateConverter());
		}
	}

	@Override
	public UICLASS getUI() {
		return pojoAdapter.getUI();
	}
	
	@Override
	public Class<POJOCLASS> getPojoClass() {
		return pojoAdapter.getPojoClass();
	}
}

class TestableBeanValidator extends BeanValidator {
	private static final long serialVersionUID = -3327624056651149028L;

	private Class<?> pojoClass;
	private String propertyName;

	public TestableBeanValidator(Class<?> pojoClass, String propertyName) {
		super(pojoClass, propertyName);
		
		this.pojoClass = pojoClass;
		this.propertyName = propertyName;
	}

	public Class<?> getPojoClass() {
		return pojoClass;
	}

	public String getPropertyName() {
		return propertyName;
	}
}

abstract class POJOConverter<PRESENTATION, MODEL> implements Converter<PRESENTATION, MODEL>{
	private static final long serialVersionUID = 2527687838020839324L;

	private Class<PRESENTATION> presentationClass;
	private Class<MODEL> modelClass;

	public POJOConverter(Class<PRESENTATION> presentationClass, Class<MODEL> modelClass) {
		super();
		this.presentationClass = presentationClass;
		this.modelClass = modelClass;
	}
	
	@Override
	public Class<MODEL> getModelType() {
		return modelClass;
	}
	
	@Override
	public Class<PRESENTATION> getPresentationType() {
		return presentationClass;
	}
}

class MoneyConverter extends POJOConverter<String, Money> {
	private static final long serialVersionUID = -6251536302559035538L;

	public MoneyConverter() {
		super(String.class, Money.class);
	}

	@Override
	public Money convertToModel(String value, Class<? extends Money> targetType, Locale locale)	throws ConversionException {
		throw new UnsupportedOperationException("unimplemented");
	}

	@Override
	public String convertToPresentation(Money value, Class<? extends String> targetType, Locale locale)	throws ConversionException {
		if (value == null) return null;
		
		NumberFormat formatter = NumberFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);

		return formatter.format(value.getAmount());
	}
}

class LocalDateConverter extends POJOConverter<Date, LocalDate> {
	private static final long serialVersionUID = -3288012479071159027L;

	public LocalDateConverter() {
		super(Date.class, LocalDate.class);
	}

	@Override
	public LocalDate convertToModel(Date value, Class<? extends LocalDate> targetType, Locale locale) throws ConversionException {
		return LocalDate.fromDateFields(value);
	}

	@Override
	public Date convertToPresentation(LocalDate value, Class<? extends Date> targetType, Locale locale)	throws ConversionException {
		return value.toDate();
	}
}




