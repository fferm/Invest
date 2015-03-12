package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.presenter.SinglePOJOPresenter;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

																			   //  VIEWINTERFACE extends SinglePOJOView, POJOCLASS, MODEL extends Model<POJOCLASS>
public abstract class SinglePOJOViewImpl<PRESENTER extends SinglePOJOPresenter<?, ?, ?>, POJOCLASS> extends ViewImpl<PRESENTER> {
	private static final long serialVersionUID = 7033937523664849158L;

	protected POJOCLASS pojo;
	
	protected abstract void bindToData();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		if (parameters.size() == 0) this.pojo = (POJOCLASS) presenter.getDOBasedOnIdString(null);
		else this.pojo = (POJOCLASS) presenter.getDOBasedOnIdString(parameters.get(0).getValue());

		bindToData();
	}

}
