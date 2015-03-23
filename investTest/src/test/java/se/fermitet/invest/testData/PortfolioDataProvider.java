package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;

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
		
		testData.add(new Portfolio("Fšretag"));;
		testData.add(new Portfolio("Privat"));;
		testData.add(new Portfolio("ISK"));;
		testData.add(new Portfolio("Barnen"));;
		testData.add(new Portfolio("Direktpension"));;
	}

}
