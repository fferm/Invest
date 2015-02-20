package se.fermitet.invest.presenter;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.model.Models;

@SuppressWarnings("rawtypes")
public abstract class Presenter<VIEWINTERFACE, MODEL extends Model> {

	protected VIEWINTERFACE view;
	protected MODEL model;
	private Class<? extends Model> modelClass;

	public Presenter(VIEWINTERFACE view, Class<? extends Model> modelClass) {
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
