package se.fermitet.invest.testData;

import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.dataFiller.DataQuery;
import se.fermitet.invest.storage.dataFiller.ExampleDataProvider;

public class TestDataProvider extends ExampleDataProvider {

	public TestDataProvider() {
		super(null);
		this.dq = new TestDataQuery(this);
	}
}

class TestDataQuery implements DataQuery {

	private ExampleDataProvider dataProvider;
	private List<Stock> allStocks;
	private List<Portfolio> allPortfolios;
	
	public TestDataQuery(ExampleDataProvider dataProvider) {
		super();
		this.dataProvider = dataProvider;
	}

	@Override
	public Stock getStockBySymbol(String symbol) {
		if (allStocks == null) allStocks = dataProvider.getStocks();
		
		for (Stock stock : allStocks) {
			if (stock.getSymbol().equals(symbol)) return stock;
		}
		return null;
	}

	@Override
	public Portfolio getPortfolioByName(String name) {
	 	if (allPortfolios == null) allPortfolios = dataProvider.getPortfolios();
	 	
		for (Portfolio portfolio : allPortfolios) {
			if (portfolio.getName().equals(name)) return portfolio;
		}
		return null;
	}
	
}
