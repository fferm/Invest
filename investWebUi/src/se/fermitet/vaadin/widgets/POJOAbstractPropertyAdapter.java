package se.fermitet.vaadin.widgets;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.AbstractField;

abstract class POJOAbstractPropertyAdapter<POJOCLASS, FIELDCLASS extends AbstractField<?>> extends POJOAbstractAdapter<POJOCLASS, FIELDCLASS> {
	private static final long serialVersionUID = 4600062141995002807L;
	private String propertyName;
	private BeanValidator validator;


	POJOAbstractPropertyAdapter(Class<POJOCLASS> pojoClass, String propertyName, String caption) {
		super(pojoClass, caption);
		
		this.propertyName = propertyName;
		
		this.validator = new TestableBeanValidator(pojoClass, propertyName);
		this.ui.addValidator(validator);
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public void bindToData(POJOCLASS data) {
		BeanItem<POJOCLASS> item = new BeanItem<POJOCLASS>(data);

		fixConverter(item);
		
		this.ui.setPropertyDataSource((Property<?>) item.getItemProperty(this.propertyName));
	}

	protected abstract void fixConverter(BeanItem<POJOCLASS> item);


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

