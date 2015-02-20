package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.dataFiller.FillTestData;

public class TransactionDataProvider {
	private List<Transaction> testData;
	
	public List<Transaction> getTestData() {
		if (testData == null) {
			initTestData();
		}
		return Collections.unmodifiableList(testData);
	}
	
	private void initTestData() {
		testData = new ArrayList<Transaction>();
		FillTestData ftd = new FillTestData(null);
		
		Stock axis = null;
		Stock hemfosa = null;
		List<Stock> stocks = ftd.getStocks();
		for (Stock stock : stocks) {
			if (stock.getSymbol().equals("AXIS")) axis = stock;
			if (stock.getSymbol().equals("HEMF B")) hemfosa = stock;
		}

		testData.addAll(ftd.getAxisTransactions(axis));
		testData.addAll(ftd.getHemfosaTransactions(hemfosa));
		Collections.shuffle(testData);
	}

}
