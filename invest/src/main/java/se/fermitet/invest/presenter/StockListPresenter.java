package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenter extends ListPresenter<StockListView, Stock, StockModel>{

	public StockListPresenter(StockListView view) {
		super(view, StockModel.class);
	}
}
