package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

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
		objUnderTest.saveStock(this.stock);
		objUnderTest.saveTransaction(transaction);

		List<Transaction> all = objUnderTest.getAllTransactions();

		assertEquals("Size", 1, all.size());
		assertTrue("Contains", all.contains(transaction));

		Transaction fromDb = all.get(0);
		assertEquals("id", transaction.getId(), fromDb.getId());
	}

	@Test
	public void testRemove() throws Exception {
		Stock s1 = new Stock("S1", "S1");

		objUnderTest.saveStock(s1);

		Transaction t1 = new Transaction(s1, LocalDate.now(), -10, Money.parse("SEK 200"), Money.parse("SEK 2"));
		Transaction t2 = new Transaction(s1, LocalDate.now().minusDays(10), 100, Money.parse("SEK 100"), Money.parse("SEK 2"));

		objUnderTest.saveTransaction(t1);
		objUnderTest.saveTransaction(t2);

		objUnderTest.deleteTransaction(t1);

		List<Transaction> allLeft = objUnderTest.getAllTransactions();

		assertEquals("size", 1, allLeft.size());
		assertTrue("contains", allLeft.contains(t2));
	}
	
	@Test
	public void testGetById() throws Exception {
		Stock s1 = new Stock("S1", "S1");

		objUnderTest.saveStock(s1);

		Transaction t1 = new Transaction(s1, LocalDate.now(), -10, Money.parse("SEK 200"), Money.parse("SEK 2"));
		Transaction t2 = new Transaction(s1, LocalDate.now().minusDays(10), 100, Money.parse("SEK 100"), Money.parse("SEK 2"));

		objUnderTest.saveTransaction(t1);
		objUnderTest.saveTransaction(t2);

		Transaction fromDb = objUnderTest.getTransactionById(t1.getId());
		
		assertNotNull("not null", fromDb);
		assertEquals("equal", t1, fromDb);
		
		Transaction shouldBeNull = objUnderTest.getTransactionById(UUID.randomUUID());
		assertNull("should be null", shouldBeNull);
	}
	



}
