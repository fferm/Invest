package se.fermitet.invest.storage;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;

public interface Storage {
	public List<Stock> getAllStocks();
	public Stock getStockById(UUID id);
	public void saveStock(Stock stock);
	public void deleteStock(Stock toDelete);

	public List<Transaction> getAllTransactions();
	public void saveTransaction(Transaction transaction);

}
