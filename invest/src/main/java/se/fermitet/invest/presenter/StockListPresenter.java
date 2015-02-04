package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenter extends Presenter<StockListView, StocksModel>{

	public StockListPresenter(StockListView view) {
		super(view, StocksModel.class);
	}

	public void fillStockListWithAllStocks() {
		this.view.displayStocks(model.getAllStocks());
	}

	public void onNewButtonClick() {
		view.navigateToSingleStockView(null);
	}

	public void onEditButtonClick(Stock selectedStock) {
		view.navigateToSingleStockView(selectedStock);
	}

	public void onDeleteButtonClick(Stock toDelete) {
		model.deleteStock(toDelete);
		view.displayStocks(model.getAllStocks());
	}
	


}
