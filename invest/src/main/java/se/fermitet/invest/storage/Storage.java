package se.fermitet.invest.storage;

import java.util.List;

import se.fermitet.invest.domain.Stock;

public interface Storage {
	public List<Stock> getAllStocks();
}
