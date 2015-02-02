package se.fermitet.invest.webui.views;

import se.fermitet.invest.presenter.Presenter;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

@SuppressWarnings("rawtypes")
abstract class ViewImpl<PRESENTER extends Presenter> extends CustomComponent implements View {
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
