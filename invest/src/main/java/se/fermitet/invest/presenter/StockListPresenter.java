package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.StockListView;
import se.fermitet.invest.viewinterface.StockListView.StockListViewListener;

public class StockListPresenter implements StockListViewListener {

	private StockListView view;
	StocksModel model;
	
	public StockListPresenter(StockListView view) {
		super();
		this.view = view;
		this.model = createStocksModel();
		
		this.view.addListener(this);
		this.view.displayStocks(model.getAllStocks());
	}

	public void onDeleteButtonClick(Stock toDelete) {
		model.deleteStock(toDelete);
		view.displayStocks(model.getAllStocks());
	}
	
	protected StocksModel createStocksModel() {
		return new StocksModel();
	}

}
