package se.fermitet.invest.storage.dataFiller;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

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
		this.exampleDataProvider = new ExampleDataProvider();
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
		List<Quote> quotes = getQuotes();
		for (Quote quote : quotes) {
			if (quote != null) storage.saveQuote(quote);
		}
		System.out.println("Saved " + quotes.size() + " quotes");
	}

	public List<Quote> getQuotes() {
		// Quote files from http://www.nasdaqomxnordic.com/shares/historicalprices
		CSVFileDataHandler fqh = new CSVFileDataHandler();
		
		List<Quote> ret = new ArrayList<Quote>();
		
		ret.addAll(fqh.getQuotesForStock(getStockBySymbol("AAK")));
		ret.addAll(fqh.getQuotesForStock(getStockBySymbol("BMAX")));
		
		return ret;
	}

	public void fillTransactions() {
		Portfolio priv = getPortfolioByName("Privat");
		Stock axis = getStockBySymbol("AXIS");
		fillSpecificTransactions(getPrivateAxisTransactions(axis, priv), "AXIS", "Privat");

		Stock hemfosa = getStockBySymbol("HEMF B");
		fillSpecificTransactions(getPrivateHemfosaTransactions(hemfosa, priv), "Hemfosa", "Privat");

		Portfolio corp = getPortfolioByName("Fšretag");
		Stock byggmax = getStockBySymbol("BMAX");
		fillSpecificTransactions(getCorporateByggmaxTransactions(byggmax, corp), "Byggmax", "Fšretag");
	}

	private Stock getStockBySymbol(String symbol) {
		List<Stock> stocks = storage.getAllStocks();
		for (Stock stock : stocks) {
			if (stock.getSymbol().equals(symbol)) return stock;
		}
		return null;
	}

	private Portfolio getPortfolioByName(String name) {
		List<Portfolio> portfolios = storage.getAllPortfolios();
		for (Portfolio port : portfolios) {
			if (port.getName().equals(name)) return port;
		}
		return null;
	}

	private void fillSpecificTransactions(List<Transaction> transactions, String stockName, String portfolioName) {
		for (Transaction transaction : transactions) {
			storage.saveTransaction(transaction);
		}
		System.out.println("Saved " + stockName + " transactions in portfolio " + portfolioName);
	}

	public List<Transaction> getPrivateAxisTransactions(Stock axis, Portfolio priv) {
		List<Transaction> ret = new ArrayList<Transaction>();

		ret.add(new Transaction(axis, new LocalDate(2006, 02, 06),  70, Money.parse("SEK  56.75"), Money.parse("SEK 9"), priv));
		ret.add(new Transaction(axis, new LocalDate(2006, 06, 02),  15, Money.parse("SEK  61.25"), Money.parse("SEK 9"), priv));
		ret.add(new Transaction(axis, new LocalDate(2007, 04, 26),   1, Money.parse("SEK 122.00"), Money.parse("SEK 9"), priv));
		ret.add(new Transaction(axis, new LocalDate(2009,  4,  2),   9, Money.parse("SEK  52.50"), Money.parse("SEK 9"), priv));
		ret.add(new Transaction(axis, new LocalDate(2009,  5,  4),   6, Money.parse("SEK  73.75"), Money.parse("SEK 9"), priv));
		ret.add(new Transaction(axis, new LocalDate(2012,  8, 10), -15, Money.parse("SEK 174.50"), Money.parse("SEK 9"), priv));

		return ret;
	}

	public List<Transaction> getPrivateHemfosaTransactions(Stock hemfosa, Portfolio priv) {
		List<Transaction> ret = new ArrayList<Transaction>();

		ret.add(new Transaction(hemfosa, new LocalDate(2014, 6,  4),  48, Money.parse("SEK 109.50"), Money.parse("SEK 7.88"), priv));
		ret.add(new Transaction(hemfosa, new LocalDate(2014, 7,  4),  20, Money.parse("SEK 114.25"), Money.parse("SEK 7.00"), priv));
		ret.add(new Transaction(hemfosa, new LocalDate(2014, 9, 30),  58, Money.parse("SEK 112.75"), Money.parse("SEK 9.81"), priv));

		return ret;
	}

	public List<Transaction> getCorporateByggmaxTransactions(Stock byggmax, Portfolio corp) {
		List<Transaction> ret = new ArrayList<Transaction>();

		ret.add(new Transaction(byggmax, new LocalDate(2014, 10,  2),  118, Money.parse("SEK 47.90"), Money.parse("SEK 39.00"), corp));
		ret.add(new Transaction(byggmax, new LocalDate(2015,  2, 10),   53, Money.parse("SEK 56.50"), Money.parse("SEK  7.00"), corp));
		ret.add(new Transaction(byggmax, new LocalDate(2015,  3,  9),   89, Money.parse("SEK 56.00"), Money.parse("SEK 12.00"), corp));

		return ret;
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
