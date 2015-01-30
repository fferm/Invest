package se.fermitet.invest.model;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class StocksModel {
	
	Storage storage;

	public StocksModel() {
		super();
		storage = createStorageFactory().getStorage();
	}

	protected StorageFactory createStorageFactory() {
		return new StorageFactory();
	}
	
	public List<Stock> getAllStocks() {
		return storage.getAllStocks();
	}

	public void deleteStock(Stock toDelete) {
		storage.delete(toDelete);
	}
}
