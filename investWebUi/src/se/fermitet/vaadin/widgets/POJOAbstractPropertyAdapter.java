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

abstract class POJOAbstractPropertyAdapter<POJO, UI extends AbstractField<?>, VALUE> implements POJOAdapter<POJO, UI> {
	private static final long serialVersionUID = 4600062141995002807L;

	private POJOAdapterHelper<POJO, UI> pojoAdapter;

	private BeanValidator validator;

	private Property<VALUE> property;

	POJOAbstractPropertyAdapter(Class<POJO> pojoClass, UI ui) {
		super();
		
		this.pojoAdapter = new POJOAdapterHelper<POJO, UI>(ui, pojoClass);
	}
	
	@SuppressWarnings("unchecked")
	public void bindToProperty(POJO data, String propertyName) {
		fixValidator(propertyName);
		
		BeanItem<POJO> item = new BeanItem<POJO>(data);
		property = item.getItemProperty(propertyName);
		fixConverter(propertyName);
		
		this.getUI().setPropertyDataSource(property);
	}
	
	private void fixValidator(String propertyName) {
		this.validator = new TestableBeanValidator(getPojoClass(), propertyName);
		
		this.getUI().removeAllValidators();
		this.getUI().addValidator(validator);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void fixConverter(String propertyName) {
		Class<?> clz = property.getType();

		if (clz.equals(Integer.class)) {
			this.getUI().setConverter((Converter) new StringToIntegerConverter());
		} else if (clz.equals(Money.class)) {
			this.getUI().setConverter((Converter) new MoneyConverter());
		}  else if (clz.equals(LocalDate.class)) {
			this.getUI().setConverter((Converter) new LocalDateConverter());
		}
	}
	
	public VALUE getValue() {
		return property.getValue();
	}

	public void setValue(VALUE value) {
		property.setValue(value);
	}
	
	@Override
	public UI getUI() {
		return pojoAdapter.getUI();
	}
	
	@Override
	public Class<POJO> getPojoClass() {
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




