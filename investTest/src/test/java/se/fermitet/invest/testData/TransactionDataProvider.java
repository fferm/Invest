package se.fermitet.invest.testData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;
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
		Stock byggmax = null;
		List<Stock> stocks = ftd.getStocks();
		for (Stock stock : stocks) {
			if (stock.getSymbol().equals("AXIS")) axis = stock;
			if (stock.getSymbol().equals("HEMF B")) hemfosa = stock;
			if (stock.getSymbol().equals("BMAX")) byggmax = stock;
		}
		
		Portfolio priv = null;
		Portfolio corp = null;
		List<Portfolio> ports = ftd.getPortfolios();
		for (Portfolio port : ports) {
			if (port.getName().equals("Privat")) priv = port;
			if (port.getName().equals("Fšretag")) corp = port;
		}

		testData.addAll(ftd.getPrivateAxisTransactions(axis, priv));
		testData.addAll(ftd.getPrivateHemfosaTransactions(hemfosa, priv));
		testData.addAll(ftd.getCorporateByggmaxTransactions(byggmax, corp));
		Collections.shuffle(testData);
	}

}
