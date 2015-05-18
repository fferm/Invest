package se.fermitet.invest.storage;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;

public interface Storage {
	public List<Stock> getAllStocks();
	public Stock getStockById(UUID id);
//	public Stock getStockBySymbol(String string);
	public void saveStock(Stock stock);
	public void deleteStock(Stock toDelete);
	public Stock getStockBySymbol(String symbol);

	public List<Transaction> getAllTransactions();
	public Transaction getTransactionById(UUID id);
	public void saveTransaction(Transaction transaction);
	public void deleteTransaction(Transaction t1);
	public List<Transaction> getTransactionsForStock(Stock stock);
	public List<Transaction> getTransactionsForPortfolio(Portfolio portfolio);

	public List<Portfolio> getAllPortfolios();
	public Portfolio getPortfolioById(UUID id);
	public void deletePortfolio(Portfolio toDelete);
	public void savePortfolio(Portfolio toSave);
	public Portfolio getPortfolioByName(String name);

	public List<Quote> getAllQuotes();
	public void saveQuote(Quote quote);
	public Quote getQuoteById(UUID id);
	public void deleteQuote(Quote quote);
	public List<Quote> getQuotesByStock(Stock theStock);

}
