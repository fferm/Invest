package se.fermitet.vaadin.widgets;

import java.io.Serializable;

import com.vaadin.ui.AbstractComponent;

abstract class POJOAbstractAdapter<POJOCLASS, UICLASS extends AbstractComponent> implements Serializable {
	private static final long serialVersionUID = 5686683176723768207L;

	protected UICLASS ui;
	protected Class<POJOCLASS> pojoClass;

	 POJOAbstractAdapter(Class<POJOCLASS> pojoClass, String caption) {
		super();
		this.pojoClass = pojoClass;
		
		this.ui = createUI(caption);
		this.ui.setImmediate(true);
	}
	
	public UICLASS getUI() {
		return ui;
	}
	
	public Class<POJOCLASS> getPojoClass() {
		return this.pojoClass;
	}

	protected abstract UICLASS createUI(String caption);

}
