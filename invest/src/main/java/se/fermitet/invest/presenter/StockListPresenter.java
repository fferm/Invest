package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenter extends Presenter<StockListView, StocksModel>{

	public StockListPresenter(StockListView view) {
		super(view);
	}

	public void fillStockListWithAllStocks() {
		this.view.displayStocks(model.getAllStocks());
	}

	public void onNewButtonClick() {
		view.editSingleStock(null);
	}

	public void onEditButtonClick(Stock selectedStock) {
		view.editSingleStock(selectedStock);
	}

	public void onDeleteButtonClick(Stock toDelete) {
		model.deleteStock(toDelete);
		view.displayStocks(model.getAllStocks());
	}
	
	protected StocksModel createModel() {
		return new StocksModel();
	}




}
