package se.fermitet.invest.storage;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface Storage {
	public List<Stock> getAllStocks();
	public void saveStock(Stock stock);
	public void removeStock(Stock stock);

}
