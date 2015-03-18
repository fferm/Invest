package se.fermitet.invest.presenter;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.ListView;

public abstract class ListPresenter<VIEWINTERFACE extends ListView<POJO>, POJO, MODEL extends Model<POJO>> extends Presenter<VIEWINTERFACE, POJO, MODEL> {

	public ListPresenter(VIEWINTERFACE view, Class<?> modelClass) {
		super(view, modelClass);
	}

	public void fillViewWithData() {
		this.view.displayData(model.getAll());
	}


}
