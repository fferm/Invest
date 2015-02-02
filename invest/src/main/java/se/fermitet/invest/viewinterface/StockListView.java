package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface StockListView {
	public void displayStocks(List<Stock> stocks);

	public void editSingleStock(Stock stock);
}
