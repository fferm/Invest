package se.fermitet.vaadin.widgets;

import com.vaadin.ui.PopupDateField;

public class POJOPropertyDatePopupAdapter<POJOCLASS> extends POJOAbstractPropertyAdapter<POJOCLASS, PopupDateField>{
	private static final long serialVersionUID = -5381555555854972062L;

	public POJOPropertyDatePopupAdapter(Class<POJOCLASS> pojoClass,	String propertyName, String caption) {
		super(pojoClass, propertyName, new PopupDateField(caption));
	}

}
