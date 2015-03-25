package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.storage.dataFiller.FillTestData;

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
		FillTestData ftd = new FillTestData(null);
		
		testData.addAll(ftd.getPortfolios());
		Collections.shuffle(testData);
	}

}
