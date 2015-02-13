package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;

public class MongoStorageTest_transaction extends MongoStorageTest_abstract {
	private Stock stock;
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		this.stock = new Stock("NAME", "SYMBOL");
		this.transaction = new Transaction(stock, LocalDate.now(), 20, Money.parse("SEK 200"), Money.parse("SEK 2"));
	}
	
	@Test
	public void testStoreAndRetrieveOne() throws Exception {
		objUnderTest.saveTransaction(transaction);
		
		List<Transaction> all = objUnderTest.getAllTransactions();
		
		assertEquals("Size", 1, all.size());
		assertTrue("Contains", all.contains(transaction));
		
		Transaction fromDb = all.get(0);
		assertEquals("id", transaction.getId(), fromDb.getId());
	}

}
