package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface StockListView {
	public void displayStocks(List<Stock> stocks);
	
	public interface StockListViewListener {
		public void onDeleteButtonClick(Stock toDelete);
	}
	
	public void addListener(StockListViewListener listener);
	public void removeListener(StockListViewListener listener);

}
