package se.fermitet.invest.webui.views;

import java.util.Iterator;
import java.util.List;

import se.fermitet.invest.presenter.Presenter;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.data.Validatable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HasComponents;

@SuppressWarnings("rawtypes")
abstract class ViewImpl<PRESENTER extends Presenter> extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;
	
	PRESENTER presenter;
	private DirectionalNavigator navigator;
	
	public ViewImpl() {
		super();
		
		this.presenter = createPresenter();
		
		setCompositionRoot(createMainLayout());
	}
	
	protected abstract Component createMainLayout();
	protected abstract PRESENTER createPresenter();
	protected abstract void enter(ViewChangeEvent event, List<URIParameter> parameters);

	@Override
	public void enter(ViewChangeEvent event) {
		String parameterString = null;
		
		if (event != null) parameterString = event.getParameters();
		
		enter(event, DirectionalNavigator.parse(parameterString));
	}
	
	
	public DirectionalNavigator getNavigator() {
		if (navigator == null) {
			navigator = createNavigator();
		}
		return navigator;
	}
	
	protected DirectionalNavigator createNavigator() {
		InvestWebUI ui = (InvestWebUI) getUI();
		
		if (ui != null) return ui.getDirectionalNavigator();
		return null;
	}

	protected boolean isValid() {
		return recursiveIsValid(this);
	}
	
	private boolean recursiveIsValid(HasComponents comp) {
		for (Iterator<Component> iter = comp.iterator(); iter.hasNext(); ) {
			Component current = iter.next();

			if (current instanceof Validatable) {
				Validatable validatable = (Validatable) current;
				
				if (! validatable.isValid()) return false;
			}
			
			if (current instanceof HasComponents) {
				HasComponents hasComponents = (HasComponents) current;
				
				if (! recursiveIsValid(hasComponents)) return false;
			}
		}
		
		return true;
	}
	

}
