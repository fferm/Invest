package se.fermitet.invest.webui.views;

import java.util.Iterator;
import java.util.List;

import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.presenter.Presenter;
import se.fermitet.invest.viewinterface.InvestView;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.invest.webui.error.ErrorMessages;
import se.fermitet.vaadin.navigation.DirectionalNavigator;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.data.Validatable;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("rawtypes")
public abstract class ViewImpl<PRESENTER extends Presenter> extends CustomComponent implements InvestView, View {
	private static final long serialVersionUID = 1L;

	PRESENTER presenter;
	private DirectionalNavigator navigator;
	private ErrorMessage applicationException;

	public ViewImpl() {
		super();

		this.presenter = createPresenter();
	}

	protected void init() {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addComponent(new NavBarViewImpl());
		mainLayout.addComponent(createMainLayout());

		setCompositionRoot(mainLayout);
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

	protected void handleApplicationException(ErrorMessage applicationException) {
		throw new UnsupportedOperationException("Should be implemented in subclasses that need application exception support");
	}

	@Override
	public boolean hasApplicationException() {
		return applicationException != null;
	}

	@Override
	public void displayApplicationException(ModelException exception) {
		String message = ErrorMessages.getMessage(exception);
		this.applicationException = new UserError(message);
		handleApplicationException(this.applicationException);

		if (getUI() != null) Notification.show(message, Notification.Type.TRAY_NOTIFICATION);

	}

	@Override
	public void clearApplicationException() {
		this.applicationException = null;
		handleApplicationException(this.applicationException);
	}


}
