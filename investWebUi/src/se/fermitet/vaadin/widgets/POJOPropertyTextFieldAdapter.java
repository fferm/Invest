package se.fermitet.vaadin.widgets;

import com.vaadin.ui.TextField;

public class POJOPropertyTextFieldAdapter<POJO, VALUE> extends POJOAbstractPropertyAdapter<POJO, TextField, VALUE> {
	private static final long serialVersionUID = -8579073716821506905L;

	public POJOPropertyTextFieldAdapter(Class<POJO> pojoClass, String caption) {
		super(pojoClass, new TextField(caption));

		initialize();
	}

	private void initialize() {
		this.getUI().setNullRepresentation("");
		this.getUI().setNullSettingAllowed(true);
	}

}




