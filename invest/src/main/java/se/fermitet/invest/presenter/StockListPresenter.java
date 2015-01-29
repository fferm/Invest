package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.StockListView;
import se.fermitet.invest.viewinterface.StockListView.StockListViewListener;

public class StockListPresenter implements StockListViewListener {

	private StockListView view;
	private StocksModel model;
	
	public StockListPresenter(StockListView view, StocksModel model) {
		super();
		this.view = view;
		this.model = model;
		
		view.addListener(this);
		
		view.displayStocks(model.getAllStocks());
	}

	public void onDeleteButtonClick(Stock toDelete) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

}
