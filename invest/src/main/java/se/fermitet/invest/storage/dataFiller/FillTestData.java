package se.fermitet.invest.storage.dataFiller;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class FillTestData {
	private Storage storage;

	public static void main(String[] args) {
		FillTestData obj = new FillTestData(new StorageFactory().getStorage());
		obj.run();
	}

	public FillTestData(Storage storage) {
		this.storage = storage;
	}

	private void run() {
		deleteAll();
		fillAll();
	}

	private void fillAll() {
		fillStocks();
		fillTransactions();
	}

	public void fillStocks() {
		for (Stock stock : getStocks()) {
			storage.saveStock(stock);
		}

		System.out.println("Saved stocks");

	}

	public List<Stock> getStocks() {
		List<Stock> ret = new ArrayList<Stock>();

		ret.add(new Stock("Axis", "AXIS"));
		ret.add(new Stock("Handelsbanken B", "SHB B"));
		ret.add(new Stock("NET B"));
		ret.add(new Stock("SCA B", "SCA B"));
		ret.add(new Stock("AAK", "AAK"));
		ret.add(new Stock("Assa Abloy", "ASSA"));
		ret.add(new Stock("Billerud Korsnäs", "BILL"));
		ret.add(new Stock("Byggmax", "BMAX"));
		ret.add(new Stock("Fenix Outdoor", "FIX B"));
		ret.add(new Stock("Hemfosa","HEMF B"));
		ret.add(new Stock("Hexagon B", "HEXA B"));
		ret.add(new Stock("Latour", "LATO"));
		ret.add(new Stock("Lundbergs", "LUND"));

		return ret;
	}

	public void fillTransactions() {
		Stock axis = storage.getStockBySymbol("AXIS");
		fillSpecificTransactions(getAxisTransactions(axis), "AXIS");
		
		Stock hemfosa = storage.getStockBySymbol("HEMF B");
		fillSpecificTransactions(getHemfosaTransactions(hemfosa), "Hemfosa");
	}

	private void fillSpecificTransactions(List<Transaction> transactions, String stockName) {
		for (Transaction transaction : transactions) {
			storage.saveTransaction(transaction);
		}
		System.out.println("Saved " + stockName + " transactions");
	}

	public List<Transaction> getAxisTransactions(Stock axis) {
		List<Transaction> ret = new ArrayList<Transaction>();

		ret.add(new Transaction(axis, new LocalDate(2006, 02, 06),  70, Money.parse("SEK  56.75"), Money.parse("SEK 9")));
		ret.add(new Transaction(axis, new LocalDate(2006, 06, 02),  15, Money.parse("SEK  61.25"), Money.parse("SEK 9")));
		ret.add(new Transaction(axis, new LocalDate(2007, 04, 26),   1, Money.parse("SEK 122.00"), Money.parse("SEK 9")));
		ret.add(new Transaction(axis, new LocalDate(2009,  4,  2),   9, Money.parse("SEK  52.50"), Money.parse("SEK 9")));
		ret.add(new Transaction(axis, new LocalDate(2009,  5,  4),   6, Money.parse("SEK  73.75"), Money.parse("SEK 9")));
		ret.add(new Transaction(axis, new LocalDate(2012,  8, 10), -15, Money.parse("SEK 174.50"), Money.parse("SEK 9")));
		
		return ret;
	}

	public List<Transaction> getHemfosaTransactions(Stock hemfosa) {
		List<Transaction> ret = new ArrayList<Transaction>();

		ret.add(new Transaction(hemfosa, new LocalDate(2014, 6,  4),  48, Money.parse("SEK 109.50"), Money.parse("SEK 7.88")));
		ret.add(new Transaction(hemfosa, new LocalDate(2014, 7,  4),  20, Money.parse("SEK 114.25"), Money.parse("SEK 7.00")));
		ret.add(new Transaction(hemfosa, new LocalDate(2014, 9, 30),  58, Money.parse("SEK 112.75"), Money.parse("SEK 9.81")));

		return ret;
	}

	private void deleteAll() {
		deleteTransactions();
		deleteStocks();
	}

	private void deleteTransactions() {
		List<Transaction> all = storage.getAllTransactions();
		for(Transaction trans : all) {
			storage.deleteTransaction(trans);
		}

		System.out.println("Deleted transactions");
	}

	private void deleteStocks() {
		List<Stock> allPrevStocks = storage.getAllStocks();
		for (Stock stock : allPrevStocks) {
			storage.deleteStock(stock);
		}

		System.out.println("Deleted stocks");
	}

}
