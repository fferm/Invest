package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.ListView;

public class StockListPresenter extends ListPresenter<ListView<Stock>, Stock, StockModel>{

	public StockListPresenter(ListView<Stock> view) {
		super(view, StockModel.class);
	}
}
