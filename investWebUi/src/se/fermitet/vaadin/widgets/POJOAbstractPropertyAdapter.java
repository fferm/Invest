package se.fermitet.vaadin.widgets;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import se.fermitet.vaadin.converters.LocalDateConverter;
import se.fermitet.vaadin.converters.MoneyConverter;

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




