package se.fermitet.vaadin.widgets;

import java.io.Serializable;

import com.vaadin.ui.AbstractComponent;

interface POJOAdapter<POJO, UI extends AbstractComponent> extends Serializable {
	
	public UI getUI();
	public Class<POJO> getPojoClass();
}

class POJOAdapterHelper<POJO, UI extends AbstractComponent> implements POJOAdapter<POJO, UI> {
	private static final long serialVersionUID = 3489127965350620634L;

	private UI ui;
	private Class<POJO> pojoClass;

	POJOAdapterHelper(UI ui, Class<POJO> pojoClass) {
		super();
		this.ui = ui;
		this.pojoClass = pojoClass;
		
		ui.setImmediate(true);
	}
	
	@Override
	public UI getUI() {
		return this.ui;
	}

	@Override
	public Class<POJO> getPojoClass() {
		return this.pojoClass;
	}
	
}
