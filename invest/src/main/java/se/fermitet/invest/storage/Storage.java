package se.fermitet.invest.storage;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Stock;

public interface Storage {
	public List<Stock> getAllStocks();
	public Stock getStockById(UUID id);
	public void saveStock(Stock stock);
	public void delete(Stock toDelete);

}
