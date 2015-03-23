package se.fermitet.invest.presenter;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.viewinterface.ListView;

public abstract class ListPresenter<VIEWINTERFACE extends ListView<POJO>, POJO, MODEL extends Model<POJO>> extends Presenter<VIEWINTERFACE, POJO, MODEL> {

	public ListPresenter(VIEWINTERFACE view, Class<?> modelClass) {
		super(view, modelClass);
	}

	public void fillViewWithData() {
		this.view.displayData(model.getAll());
	}
	
	public void onDeleteButtonClick(POJO toDelete) {
		try {
			model.delete(toDelete);
			view.clearApplicationException();
		} catch (ModelException e) {
			e.printStackTrace();
			view.displayApplicationException(e);
		}
		view.displayData(model.getAll());
	}
	
	public void onNewButtonClick() {
		view.navigateToSingleView(null);
	}
	
	public void onEditButtonClick(POJO selected) {
		view.navigateToSingleView(selected);
	}





}
