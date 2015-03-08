package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface SingleTransactionView {

	public void showStocksInSelection(List<Stock> list);

	public void navigateBack();

}
