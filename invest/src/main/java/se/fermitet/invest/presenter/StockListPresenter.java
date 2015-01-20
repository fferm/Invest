package se.fermitet.invest.presenter;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;

public class StockListPresenter {

	public interface StockListView {
		public void displayStocks(List<Stock> stocks);
	}

	private StockListView view;
	private StocksModel model;
	
	public StockListPresenter(StockListView view, StocksModel model) {
		super();
		this.view = view;
		this.model = model;
		
		view.displayStocks(model.getAllStocks());
	}

}
