package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Stock;

public class StockDataProvider {

	private List<Stock> testStocks;
	
	public List<Stock> getTestStocks() {
		if (testStocks == null) {
			initTestStocks();
		}
		return Collections.unmodifiableList(testStocks);
	}
	
	private void initTestStocks() {
		testStocks = new ArrayList<Stock>();
		
		testStocks.add(new Stock("Name 1", "C"));
		testStocks.add(new Stock("Name 2", "B"));
		testStocks.add(new Stock("A"));
	}

}
