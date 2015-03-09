package se.fermitet.vaadin.widgets;

import org.joda.time.LocalDate;

import com.vaadin.ui.PopupDateField;

public class POJOPropertyDatePopupAdapter<POJOCLASS> extends POJOAbstractPropertyAdapter<POJOCLASS, PopupDateField, LocalDate>{
	private static final long serialVersionUID = -5381555555854972062L;

	public POJOPropertyDatePopupAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super(pojoClass, new PopupDateField(caption));
	}

}
