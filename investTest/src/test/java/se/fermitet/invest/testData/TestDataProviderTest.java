package se.fermitet.invest.testData;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;

public class TestDataProviderTest {

	private TestDataProvider provider;

	@Before
	public void setUp() throws Exception {
		this.provider = new TestDataProvider();
	}
	
	@Test
	public void testGetStocks() throws Exception {
		List<Stock> list = provider.getStocks();
		
		assertNotNull(list);
		assertTrue("size > 0", list.size() > 0);
	}
	
	@Test
	public void testGetPortfolios() throws Exception {
		List<Portfolio> list = provider.getPortfolios();
		
		assertNotNull(list);
		assertTrue("size > 0", list.size() > 0);
	}
	
	@Test
	public void testGetTransactions() throws Exception {
		List<Transaction> list = provider.getTransactions();
		
		assertNotNull(list);
		assertTrue("size > 0", list.size() > 0);
		
		Transaction first = list.get(0);
		String firstStockSymbol = first.getStock().getSymbol();
		String firstPortfolioName = first.getPortfolio().getName();
		
		Transaction second = null;
		Iterator<Transaction> iter = list.iterator();
		iter.next();
		while (second == null && iter.hasNext()) {
			Transaction next = iter.next();
			if (next.getStock().getSymbol().equals(firstStockSymbol)) second = next;
		}
		assertNotNull("second not null", second);
		assertFalse("not same", first == second);
		assertEquals("first and second stock id", first.getStock().getId(), second.getStock().getId());


		second = null;
		iter = list.iterator();
		iter.next();
		while (second == null && iter.hasNext()) {
			Transaction next = iter.next();
			if (next.getPortfolio().getName().equals(firstPortfolioName)) second = next;
		}
		assertNotNull("second not null", second);
		assertFalse("not same", first == second);
		assertEquals("first and second portfolio id", first.getPortfolio().getId(), second.getPortfolio().getId());

	}
}
