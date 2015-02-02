package se.fermitet.invest.webui.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

abstract class ViewImpl<PRESENTER> extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;
	
	PRESENTER presenter;
	
	public ViewImpl() {
		super();
		
		this.presenter = createPresenter();
		
		setCompositionRoot(createMainLayout());
	}

	protected abstract Component createMainLayout();
	protected abstract PRESENTER createPresenter();

}
