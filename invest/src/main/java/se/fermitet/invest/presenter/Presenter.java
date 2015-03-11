package se.fermitet.invest.presenter;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.model.Models;

public abstract class Presenter<VIEWINTERFACE, POJOCLASS, MODEL extends Model<POJOCLASS>> {

	protected VIEWINTERFACE view;
	protected MODEL model;
	private Class<?> modelClass;

	public Presenter(VIEWINTERFACE view, Class<?> modelClass) {
		super();
		
		this.view = view;
		this.modelClass = modelClass;
		this.model = createModel();
	}

	@SuppressWarnings("unchecked")
	protected MODEL createModel() {
		return (MODEL) Models.fromClass(modelClass);
	}
}
