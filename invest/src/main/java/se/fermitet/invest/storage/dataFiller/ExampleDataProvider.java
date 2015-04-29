package se.fermitet.invest.storage.dataFiller;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;

public class ExampleDataProvider {
	
	private CSVFileDataHandler csv;

	public ExampleDataProvider() {
		super();
		csv = new CSVFileDataHandler();
	}
	
	public List<Stock> getStocks() {
		List<Stock> ret = new ArrayList<Stock>();

		for (String[] splitLine : csv.getSplitLines("Stocks.csv")) {
			Stock stock = new Stock();
			
			stock.setSymbol(splitLine[0]);
			
			if (splitLine.length > 1 && splitLine[1] != null) stock.setName(splitLine[1]);
			ret.add(stock);
		}

		return ret;
	}

	public List<Portfolio> getPortfolios() {
		List<Portfolio> ret = new ArrayList<Portfolio>();

		ret.add(new Portfolio("ISK"));
		ret.add(new Portfolio("Privat"));
		ret.add(new Portfolio("Barnen"));
		ret.add(new Portfolio("Fšretag"));
		ret.add(new Portfolio("Direktpension"));

		return ret;
	}


}
