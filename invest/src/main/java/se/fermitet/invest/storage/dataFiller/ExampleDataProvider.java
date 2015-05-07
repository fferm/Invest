package se.fermitet.invest.storage.dataFiller;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import se.fermitet.invest.converter.DateConverter;
import se.fermitet.invest.converter.MoneyConverter;
import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;

public class ExampleDataProvider {
	
	private CSVFileDataHandler csv;
	protected DataQuery dq;

	public ExampleDataProvider(DataQuery dq) {
		super();
		this.dq = dq;
		this.csv = new CSVFileDataHandler();
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

		for (String[] splitLine : csv.getSplitLines("Portfolios.csv")) {
			Portfolio portfolio = new Portfolio(splitLine[0]);
			
			ret.add(portfolio);
		}

		return ret;
	}

	public List<Quote> getQuotes() {
		List<Quote> ret = new ArrayList<Quote>();
		
		for (String[] splitLine : csv.getSplitLines("Quotes.csv")) {
			Quote quote = new Quote();
			
			Stock stock = dq.getStockBySymbol(splitLine[0]);
			quote.setStock(stock);
			quote.setDate(getDateFromString(splitLine[1]));
			quote.setBid(getMoneyFromString(splitLine[2]));
			quote.setAsk(getMoneyFromString(splitLine[3]));
			quote.setHigh(getMoneyFromString(splitLine[5]));
			quote.setLow(getMoneyFromString(splitLine[6]));
			quote.setLast(getMoneyFromString(splitLine[7]));
			quote.setVolume(getIntegerFromString(splitLine[9]));
			quote.setTurnover(getMoneyFromString(splitLine[10]));

			ret.add(quote);
		}
		
		return ret;
	}
	
	public List<Transaction> getTransactions() {
		List<Transaction> ret = new ArrayList<Transaction>();
		
		for (String[] splitLine : csv.getSplitLines("Transactions.csv")) {
			Transaction transaction = new Transaction();
			
			Stock stock = dq.getStockBySymbol(splitLine[0]);
			transaction.setStock(stock);
			
			transaction.setDate(getDateFromString(splitLine[1]));
			transaction.setNumber(getIntegerFromString(splitLine[2]));
			transaction.setPrice(getMoneyFromString(splitLine[3]));
			transaction.setFee(getMoneyFromString(splitLine[4]));
			
			Portfolio portfolio = dq.getPortfolioByName(splitLine[5]);
			transaction.setPortfolio(portfolio);
			
			ret.add(transaction);
		}
		
		return ret;
	}



	private Integer getIntegerFromString(String string) {
		return Integer.parseInt(string);
	}

	private Money getMoneyFromString(String string) {
		return new MoneyConverter().moneyFromString(string);
	}

	private LocalDate getDateFromString(String string) {
		return new DateConverter().dateFromString(string);
	}

}
