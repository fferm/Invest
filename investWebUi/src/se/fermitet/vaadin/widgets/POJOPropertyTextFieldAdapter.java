package se.fermitet.vaadin.widgets;

import com.vaadin.ui.TextField;

public class POJOPropertyTextFieldAdapter<POJOCLASS, VALUECLASS> extends POJOAbstractPropertyAdapter<POJOCLASS, TextField, VALUECLASS> {
	private static final long serialVersionUID = -8579073716821506905L;

	public POJOPropertyTextFieldAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super(pojoClass, new TextField(caption));

		initialize();
	}

	private void initialize() {
		this.getUI().setNullRepresentation("");
		this.getUI().setNullSettingAllowed(true);
	}

}




