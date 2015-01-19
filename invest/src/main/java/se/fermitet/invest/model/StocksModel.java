package se.fermitet.invest.model;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class StocksModel {

	public List<Stock> getAllStocks() {
		Storage storage = StorageFactory.instance().getStorage();
		return storage.getAllStocks();
	}
}
