package se.fermitet.invest.storage.dataFiller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import se.fermitet.invest.converter.DateConverter;
import se.fermitet.invest.converter.MoneyConverter;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;

class FileQuoteHandler {
	
	List<Quote> getQuotesForStock(Stock stock) {
		List<Quote> ret = new ArrayList<Quote>();
		
		String filename = stock.getSymbol() + ".csv";
		
		List<String> lines = getLines(filename);
		for (String line : lines) {
			ret.add(getQuoteFromLine(stock, line));
		}
		return ret;
	}

	@SuppressWarnings("finally")
	private List<String> getLines(String filename) {
		BufferedReader br = null;
		List<String> ret = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(new File("src/main/resources/dataFiller", filename)));
			String line;
			while ((line = br.readLine()) != null) {
				ret.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)	br.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return ret;
			}
		}
	}
	
	private Quote getQuoteFromLine(Stock stock, String line) {
		if (line.contains("Date")) return null;
		
		String[] splits = line.split(";");
		
		Quote ret = new Quote();
		
		ret.setStock(stock);
		ret.setDate(getDateFromString(splits[0]));
		ret.setBid(getMoneyFromString(splits[1]));
		ret.setAsk(getMoneyFromString(splits[2]));
		ret.setHigh(getMoneyFromString(splits[4]));
		ret.setLow(getMoneyFromString(splits[5]));
		ret.setLast(getMoneyFromString(splits[6]));
		ret.setVolume(getIntegerFromString(splits[8]));
		ret.setTurnover(getMoneyFromString(splits[9]));

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
