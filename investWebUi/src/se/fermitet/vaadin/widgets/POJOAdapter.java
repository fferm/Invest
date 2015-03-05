package se.fermitet.vaadin.widgets;

import java.io.Serializable;

import com.vaadin.ui.AbstractComponent;

interface POJOAdapter<POJOCLASS, UICLASS extends AbstractComponent> extends Serializable {
	
	public UICLASS getUI();
	public Class<POJOCLASS> getPojoClass();
}

class POJOAdapterHelper<POJOCLASS, UICLASS extends AbstractComponent> implements POJOAdapter<POJOCLASS, UICLASS> {
	private static final long serialVersionUID = 3489127965350620634L;

	private UICLASS ui;
	private Class<POJOCLASS> pojoClass;

	POJOAdapterHelper(UICLASS ui, Class<POJOCLASS> pojoClass) {
		super();
		this.ui = ui;
		this.pojoClass = pojoClass;
		
		ui.setImmediate(true);
	}
	
	@Override
	public UICLASS getUI() {
		return this.ui;
	}

	@Override
	public Class<POJOCLASS> getPojoClass() {
		return this.pojoClass;
	}
	
}
