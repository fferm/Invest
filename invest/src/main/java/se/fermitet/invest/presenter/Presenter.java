package se.fermitet.invest.presenter;

public abstract class Presenter<VIEWINTERFACE, MODEL> {

	protected VIEWINTERFACE view;
	protected MODEL model;

	public Presenter(VIEWINTERFACE view) {
		super();
		
		this.view = view;
		this.model = createModel();
	}

	protected abstract MODEL createModel();
}
