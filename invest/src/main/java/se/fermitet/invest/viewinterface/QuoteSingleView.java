package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface QuoteSingleView extends POJOSingleView {

	public void showStocksInSelection(List<Stock> list);

}
