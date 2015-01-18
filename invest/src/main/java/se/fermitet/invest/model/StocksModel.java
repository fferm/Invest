package se.fermitet.invest.model;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Stock;

public class StocksModel {

	public List<Stock> getAllStocks() {
		List<Stock> stocks = new ArrayList<Stock>();
		stocks.add(new Stock("AXIS").setName("Axis"));
		stocks.add(new Stock("BMAX").setName("Byggmax group"));
		stocks.add(new Stock("CAST").setName("Castellum"));
		stocks.add(new Stock("FOI B").setName("Fenix Outdoor International B"));
		stocks.add(new Stock("SEB B").setName("SEB"));
		stocks.add(new Stock("SHB B").setName("Handelsbanken B"));
		stocks.add(new Stock("TRMO").setName("Transmode"));

		return stocks;
	}
}
