package se.fermitet.invest.storage.dataFiller;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;

public interface DataQuery {

	public Stock getStockBySymbol(String symbol);
	public Portfolio getPortfolioByName(String name);


}
