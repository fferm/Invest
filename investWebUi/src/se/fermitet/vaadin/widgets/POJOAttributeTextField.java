package se.fermitet.vaadin.widgets;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.TextField;

public class POJOAttributeTextField<POJOCLASS> extends TextField {
	private static final long serialVersionUID = -8579073716821506905L;
	private Class<?> pojoClass;
	private String propertyName;
	private BeanValidator validator;

	public POJOAttributeTextField(String caption, Class<?> pojoClass, String propertyName) {
		super(caption);
		this.pojoClass = pojoClass;
		this.propertyName = propertyName;
		
		initialize();
	}
	
	private void initialize() {
		this.setImmediate(true);
		this.setNullRepresentation("");
		this.setNullSettingAllowed(true);
		
		this.validator = new TestableBeanValidator(pojoClass, propertyName);
		this.addValidator(validator);
	}

	public Class<?> getPojoClass() {
		return pojoClass;
	}

	public String getPropertyId() {
		return propertyName;
	}

	public void bindToData(POJOCLASS data) {
		BeanItem<POJOCLASS> item = new BeanItem<POJOCLASS>(data);
		this.setPropertyDataSource(item.getItemProperty(this.propertyName));
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

