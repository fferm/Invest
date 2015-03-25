package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;

public interface TransactionSingleView extends POJOSingleView {

	public void showStocksInSelection(List<Stock> list);
	public void showPortfoliosInSelection(List<Portfolio> list);

}
