package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Stock;

public class StockModel extends Model<Stock> {
	
	StockModel() {
		super();
	}

	@Override
	public List<Stock> getAll() {
		return storage.getAllStocks();
	}
	
	@Override
	public Stock getById(UUID id) {
		return storage.getStockById(id);
	}

	@Override
	public void save(Stock stock) {
		storage.saveStock(stock);
	}

	@Override
	public void delete(Stock toDelete) {
		storage.deleteStock(toDelete);
	}


}
