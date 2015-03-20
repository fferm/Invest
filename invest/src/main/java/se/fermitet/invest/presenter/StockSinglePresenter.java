package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.StockSingleView;

public class StockSinglePresenter extends POJOSinglePresenter<StockSingleView, Stock, StockModel> {
	public StockSinglePresenter(StockSingleView view) {
		super(view, StockModel.class, Stock.class);
	}
}
