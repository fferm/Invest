package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.dataFiller.DataQuery;
import se.fermitet.invest.storage.dataFiller.ExampleDataProvider;

public class TestDataProvider extends ExampleDataProvider {

	public TestDataProvider() {
		super(null);
		this.dq = new TestDataQuery(this);
	}
	
	@Override
	public List<Stock> getStocks() {
		List<Stock> ret = super.getStocks();
		Collections.shuffle(ret);
		return ret;
	}
	
	public Stock getStockBySymbol(String symbol) {
		for (Stock stock : getStocks()) {
			if (stock.getSymbol().equals(symbol)) return stock;
		}
		return null;
	}

	@Override
	public List<Transaction> getTransactions() {
		List<Transaction> ret = super.getTransactions();
		Collections.shuffle(ret);
		return ret;
	}
	
	@Override
	public List<Portfolio> getPortfolios() {
		List<Portfolio> ret = super.getPortfolios();
		Collections.shuffle(ret);
		return ret;
	}

	@Override
	public List<Quote> getQuotes() {
		List<Quote> ret = super.getQuotes();
		Collections.shuffle(ret);
		return ret;
	}
	
	public List<Quote> getQuotesForStock(Stock stock) {
		return getQuotesForStockBySymbol(stock.getSymbol());
	}

	public List<Quote> getQuotesForStockBySymbol(String stockSymbol) {
		List<Quote> all = this.getQuotes();
		List<Quote> ret = new ArrayList<Quote>();
		for (Quote quote : all) {
			if (quote.getStock().getSymbol().equals(stockSymbol)) ret.add(quote);
		}
		return ret;
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
