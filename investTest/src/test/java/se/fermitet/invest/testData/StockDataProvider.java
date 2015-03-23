package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Stock;

public class StockDataProvider {

	private static List<Stock> testData;
	
	public List<Stock> getTestData() {
		if (testData == null) {
			initTestData();
		}
		return Collections.unmodifiableList(testData);
	}
	
	private void initTestData() {
		testData = new ArrayList<Stock>();
		
		testData.add(new Stock("Name 1", "C"));
		testData.add(new Stock("Name 2", "B"));
		testData.add(new Stock("A"));
		testData.add(new Stock("B", "BB"));
		testData.add(new Stock("AAB", "AAK"));
	}

}
