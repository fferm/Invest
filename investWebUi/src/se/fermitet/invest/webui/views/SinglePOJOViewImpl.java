package se.fermitet.invest.webui.views;

import se.fermitet.invest.presenter.SinglePOJOPresenter;

																			   //  VIEWINTERFACE extends SinglePOJOView, POJOCLASS, MODEL extends Model<POJOCLASS>
public abstract class SinglePOJOViewImpl<PRESENTER extends SinglePOJOPresenter<?, ?, ?>> extends ViewImpl<PRESENTER> {
	private static final long serialVersionUID = 7033937523664849158L;

}
