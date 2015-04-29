package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.storage.dataFiller.ExampleDataProvider;

public class PortfolioDataProvider {
	private static List<Portfolio> testData;
	
	public List<Portfolio> getTestData() {
		if (testData == null) {
			initTestData();
		}
		return Collections.unmodifiableList(testData);
	}
	
	private void initTestData() {
		testData = new ArrayList<Portfolio>();
		testData.addAll(new ExampleDataProvider().getPortfolios());
		Collections.shuffle(testData);
	}

}
