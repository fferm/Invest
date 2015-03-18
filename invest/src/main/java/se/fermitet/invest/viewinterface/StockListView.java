package se.fermitet.invest.viewinterface;

import se.fermitet.invest.domain.Stock;

public interface StockListView extends ListView<Stock> {
	public void navigateToSingleStockView(Stock stock);
}
