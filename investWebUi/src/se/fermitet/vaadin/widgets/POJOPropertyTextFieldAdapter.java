package se.fermitet.vaadin.widgets;

import com.vaadin.ui.TextField;

public class POJOPropertyTextFieldAdapter<POJOCLASS> extends POJOAbstractPropertyAdapter<POJOCLASS, TextField> {
	private static final long serialVersionUID = -8579073716821506905L;

	public POJOPropertyTextFieldAdapter(Class<POJOCLASS> pojoClass, String propertyName, String caption) {
		super(pojoClass, propertyName, new TextField(caption));

		initialize();
	}

	private void initialize() {
		this.getUI().setNullRepresentation("");
		this.getUI().setNullSettingAllowed(true);
	}

}




