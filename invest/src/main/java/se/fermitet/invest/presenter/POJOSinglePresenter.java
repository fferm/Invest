package se.fermitet.invest.presenter;

import java.util.UUID;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.POJOSingleView;

public abstract class POJOSinglePresenter<VIEWINTERFACE extends POJOSingleView, POJO, MODEL extends Model<POJO>> extends Presenter<VIEWINTERFACE, POJO, MODEL> {

	private Class<POJO> pojoClass;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public POJOSinglePresenter(VIEWINTERFACE view, Class<MODEL> modelClass, Class<POJO> pojoClass) {
		super(view, (Class<Model>) modelClass);
		
		this.pojoClass = pojoClass;
	}

	public POJO getDOBasedOnIdString(String idString) {
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
	
	public void onOkButtonClick(POJO pojo) {
		model.save(pojo);
		view.navigateBack();
	}
	
	private POJO newDefaultPOJO() {
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
