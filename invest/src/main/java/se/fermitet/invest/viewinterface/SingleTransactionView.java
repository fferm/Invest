package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface SingleTransactionView extends SinglePOJOView {

	public void showStocksInSelection(List<Stock> list);

}
