package se.fermitet.invest.presenter;

import java.util.UUID;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.SinglePOJOView;

public abstract class SinglePOJOPresenter<VIEWINTERFACE extends SinglePOJOView, POJOCLASS, MODEL extends Model<POJOCLASS>> extends Presenter<VIEWINTERFACE, POJOCLASS, MODEL> {

	private Class<POJOCLASS> pojoClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SinglePOJOPresenter(VIEWINTERFACE view, Class<MODEL> modelClass, Class<POJOCLASS> pojoClass) {
		super(view, (Class<Model>) modelClass);
		
		this.pojoClass = pojoClass;
	}

	public POJOCLASS getDOBasedOnIdString(String idString) {
		if (idString == null || idString.length() == 0) {
			return newDefaultPOJO();
		} else {
			UUID id = UUID.fromString(idString);
			
			return model.getById(id);
		}
	}
	
	public void onCancelButtonClick() {
		view.navigateBack();
	}
	
	public void onOkButtonClick(POJOCLASS pojo) {
		model.save(pojo);
		view.navigateBack();
	}


	
	private POJOCLASS newDefaultPOJO() {
		try {
			return pojoClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}
