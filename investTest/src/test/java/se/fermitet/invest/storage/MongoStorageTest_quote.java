package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.joda.money.Money;
import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;

public class MongoStorageTest_quote extends MongoStorageTest_abstract {
	@Test
	public void testStoreAndRetrieveOne() throws Exception {
		Stock stock = new Stock("TST");
		Quote quote = new Quote();
		quote.setStock(stock);
		quote.setAsk(Money.parse("SEK 201"));
		quote.setBid(Money.parse("SEK 202"));
		quote.setHigh(Money.parse("SEK 205"));
		quote.setLast(Money.parse("SEK 195"));
		quote.setLast(Money.parse("SEK 201.50"));
		quote.setVolume(10000);
		quote.setTurnover(Money.parse("SEK 2000000"));
		
		objUnderTest.saveStock(stock);
		objUnderTest.saveQuote(quote);
		
		List<Quote> all = objUnderTest.getAllQuotes();
		
		assertEquals("Size", 1, all.size());
		assertTrue("Contains", all.contains(quote));
		
		Quote fromDb = all.get(0);
		assertEquals("id", quote.getId(), fromDb.getId());
	}
	
	@Test
	public void testGetById() throws Exception {
		Quote first = new Quote();
		first.setStock(new Stock("TEST"));
		
		objUnderTest.saveQuote(first);
		
		Quote fromDb = objUnderTest.getQuoteById(first.getId());
		
		assertNotNull("not null", fromDb);
		assertEquals("equal", first, fromDb);
		
		Quote shouldBeNull = objUnderTest.getQuoteById(UUID.randomUUID());
		assertNull("should be null", shouldBeNull);
	}
	
	@Test
	public void testRemove() throws Exception {
		Quote s1 = new Quote();
		Quote s2 = new Quote();
		
		objUnderTest.saveQuote(s1);
		objUnderTest.saveQuote(s2);
		
		objUnderTest.deleteQuote(s1);
		
		List<Quote> allLeft = objUnderTest.getAllQuotes();
		
		assertEquals("size", 1, allLeft.size());
		assertTrue("contains", allLeft.contains(s2));
	}
	

	

}
