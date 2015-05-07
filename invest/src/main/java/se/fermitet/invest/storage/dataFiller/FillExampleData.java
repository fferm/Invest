package se.fermitet.invest.storage.dataFiller;

import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class FillExampleData {
	private Storage storage;
	private ExampleDataProvider exampleDataProvider;

	public static void main(String[] args) {
		FillExampleData obj = new FillExampleData(new StorageFactory().getStorage());
		obj.run();
	}

	public FillExampleData(Storage storage) {
		this.storage = storage;
		this.exampleDataProvider = new ExampleDataProvider(new StorageDataQueryAdapter(storage));
	}

	private void run() {
		deleteAll();
		fillAll();
	}

	private void fillAll() {
		fillStocks();
		fillPortfolios();
		fillTransactions();
		fillQuotes();
	}

	public void fillStocks() {
		List<Stock> stocks = exampleDataProvider.getStocks();
		for (Stock stock : stocks) {
			storage.saveStock(stock);
		}

		System.out.println("Saved " + stocks.size() + " stocks");
	}

	public void fillPortfolios() {
		List<Portfolio> portfolios = exampleDataProvider.getPortfolios();
		for (Portfolio port : portfolios) {
			storage.savePortfolio(port);
		}
		System.out.println("Saved " + portfolios.size() + " portfolios");
	}

	public void fillQuotes() {
		List<Quote> quotes = exampleDataProvider.getQuotes();
		for (Quote quote : quotes) {
			if (quote != null) storage.saveQuote(quote);
		}
		System.out.println("Saved " + quotes.size() + " quotes");
	}

	public void fillTransactions() {
		List<Transaction> transactions = exampleDataProvider.getTransactions();
		for (Transaction transaction : transactions) {
			storage.saveTransaction(transaction);
		}
		System.out.println("Saved " + transactions.size() + " transactions");
	}

	private void deleteAll() {
		deleteQuotes();
		deleteTransactions();
		deletePortfolios();
		deleteStocks();
	}

	private void deleteQuotes() {
		List<Quote> all = storage.getAllQuotes();
		for(Quote quote : all) {
			storage.deleteQuote(quote);
		}

		System.out.println("Deleted quotes");
	}

	private void deletePortfolios() {
		List<Portfolio> all = storage.getAllPortfolios();
		for(Portfolio port : all) {
			storage.deletePortfolio(port);
		}

		System.out.println("Deleted portfolios");
	}

	private void deleteTransactions() {
		List<Transaction> all = storage.getAllTransactions();
		for(Transaction trans : all) {
			storage.deleteTransaction(trans);
		}

		System.out.println("Deleted transactions");
	}

	private void deleteStocks() {
		List<Stock> allPrevStocks = storage.getAllStocks();
		for (Stock stock : allPrevStocks) {
			storage.deleteStock(stock);
		}

		System.out.println("Deleted stocks");
	}

}

class StorageDataQueryAdapter implements DataQuery {
	private Storage storage;

	public StorageDataQueryAdapter(Storage storage) {
		super();
		this.storage = storage;
	}

	public Stock getStockBySymbol(String stockSymbol) {
		return storage.getStockBySymbol(stockSymbol);
	}

	public Portfolio getPortfolioByName(String name) {
		return storage.getPortfolioByName(name);
	}
}
