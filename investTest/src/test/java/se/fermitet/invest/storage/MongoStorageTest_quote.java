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
	
	@Test
	public void testGetByStock() throws Exception {
		Stock s1 = new Stock("s1");
		Stock s2 = new Stock("s2");
		Stock s3 = new Stock("s3");
		objUnderTest.saveStock(s1);
		objUnderTest.saveStock(s2);
		objUnderTest.saveStock(s3);
		
		Quote q11 = new Quote();
		Quote q12 = new Quote();
		q11.setStock(s1);
		q12.setStock(s1);
		q11.setAsk(Money.parse("SEK 11"));
		q12.setAsk(Money.parse("SEK 12"));
		objUnderTest.saveQuote(q11);
		objUnderTest.saveQuote(q12);
		
		Quote q21 = new Quote();
		q21.setStock(s2);
		q21.setAsk(Money.parse("SEK 21"));
		objUnderTest.saveQuote(q21);

		List<Quote> s1Results = objUnderTest.getQuotesByStock(s1);
		assertEquals("size s1", 2, s1Results.size());
		assertTrue("contains q11", s1Results.contains(q11));
		assertTrue("contains q12", s1Results.contains(q12));
		
		List<Quote> s2Results = objUnderTest.getQuotesByStock(s2);
		assertEquals("size s2", 1, s2Results.size());
		assertTrue("contains q21", s2Results.contains(q21));
		
		List<Quote> s3Results = objUnderTest.getQuotesByStock(s3);
		assertEquals("s3 size", 0, s3Results.size());
	}
	

	

}
