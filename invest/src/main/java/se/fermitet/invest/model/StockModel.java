package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class StockModel extends Model {
	
	Storage storage;

	StockModel() {
		super();
		storage = createStorageFactory().getStorage();
	}

	protected StorageFactory createStorageFactory() {
		return new StorageFactory();
	}
	
	public List<Stock> getAllStocks() {
		return storage.getAllStocks();
	}
	
	public Stock getStockById(UUID id) {
		return storage.getStockById(id);
	}

	public void save(Stock stock) {
		storage.saveStock(stock);
	}

	public void deleteStock(Stock toDelete) {
		storage.delete(toDelete);
	}

}
