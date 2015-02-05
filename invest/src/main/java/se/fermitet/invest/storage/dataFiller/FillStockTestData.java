package se.fermitet.invest.storage.dataFiller;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class FillStockTestData {
	public static void main(String[] args) {
		FillStockTestData obj = new FillStockTestData();
		obj.fillStockTestData();
	}

	public void fillStockTestData() {
		Storage storage = new StorageFactory().getStorage();

		List<Stock> allPrevStocks = storage.getAllStocks();
		for (Stock stock : allPrevStocks) {
			storage.delete(stock);
		}
		
		
		Stock s1 = new Stock("Axis", "AXIS");
		Stock s2 = new Stock("Handelsbanken B", "SHB B");
		Stock s3 = new Stock("NET B");

		storage.saveStock(s1);
		storage.saveStock(s2);
		storage.saveStock(s3);

		System.out.println("Saved stocks");
	}
}
