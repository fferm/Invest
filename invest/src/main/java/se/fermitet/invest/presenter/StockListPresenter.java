package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenter extends ListPresenter<StockListView, Stock, StockModel>{

	public StockListPresenter(StockListView view) {
		super(view, StockModel.class);
	}

	public void onNewButtonClick() {
		view.navigateToSingleStockView(null);
	}

	public void onEditButtonClick(Stock selectedStock) {
		view.navigateToSingleStockView(selectedStock);
	}

	public void onDeleteButtonClick(Stock toDelete) {
		model.delete(toDelete);
		view.displayData(model.getAll());
	}
	


}
