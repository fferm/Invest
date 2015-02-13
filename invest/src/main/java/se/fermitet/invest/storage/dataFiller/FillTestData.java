package se.fermitet.invest.storage.dataFiller;

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
		storage.saveStock(new Stock("Axis", "AXIS"));
		storage.saveStock(new Stock("Handelsbanken B", "SHB B"));
		storage.saveStock(new Stock("NET B"));
		storage.saveStock(new Stock("SCA B", "SCA B"));
		storage.saveStock(new Stock("AAK", "AAK"));
		storage.saveStock(new Stock("Assa Abloy", "ASSA"));
		storage.saveStock(new Stock("Billerud Korsnäs", "BILL"));
		storage.saveStock(new Stock("Byggmax", "BMAX"));
		storage.saveStock(new Stock("Fenix Outdoor", "FIX B"));
		storage.saveStock(new Stock("Hemfosa","HEMF B"));
		storage.saveStock(new Stock("Hexagon B", "HEXA B"));
		storage.saveStock(new Stock("Latour", "LATO"));
		storage.saveStock(new Stock("Lundbergs", "LUND"));

		System.out.println("Saved stocks");
	}

	private void fillTransactions() {
		fillAxisTransactions();
		fillHemfosaTransactions();
	}
	
	private void fillAxisTransactions() {
		Stock axis = storage.getStockBySymbol("AXIS");
		
		storage.saveTransaction(new Transaction(axis, new LocalDate(2006, 02, 06),  70, Money.parse("SEK  56.75"), Money.parse("SEK 9")));
		storage.saveTransaction(new Transaction(axis, new LocalDate(2006, 06, 02),  15, Money.parse("SEK  61.25"), Money.parse("SEK 9")));
		storage.saveTransaction(new Transaction(axis, new LocalDate(2007, 04, 26),   1, Money.parse("SEK 122.00"), Money.parse("SEK 9")));
		storage.saveTransaction(new Transaction(axis, new LocalDate(2009,  4,  2),   9, Money.parse("SEK  52.50"), Money.parse("SEK 9")));
		storage.saveTransaction(new Transaction(axis, new LocalDate(2009,  5,  4),   6, Money.parse("SEK  73.75"), Money.parse("SEK 9")));
		storage.saveTransaction(new Transaction(axis, new LocalDate(2012,  8, 10), -15, Money.parse("SEK 174.50"), Money.parse("SEK 9")));
		
		System.out.println("Saved AXIS transactions");
	}
	
	private void fillHemfosaTransactions() {
		Stock hemfosa = storage.getStockBySymbol("HEMF B");
		
		storage.saveTransaction(new Transaction(hemfosa, new LocalDate(2014, 6,  4),  48, Money.parse("SEK 109.50"), Money.parse("SEK 7.88")));
		storage.saveTransaction(new Transaction(hemfosa, new LocalDate(2014, 7,  4),  20, Money.parse("SEK 114.25"), Money.parse("SEK 7.00")));
		storage.saveTransaction(new Transaction(hemfosa, new LocalDate(2014, 9, 30),  58, Money.parse("SEK 112.75"), Money.parse("SEK 9.81")));
		
		System.out.println("Saved Hemfosa transactions");
		
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
