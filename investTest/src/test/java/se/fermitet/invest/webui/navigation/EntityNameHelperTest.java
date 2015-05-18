package se.fermitet.invest.webui.navigation;

import static org.junit.Assert.*;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;

public class EntityNameHelperTest {
	@Test
	public void testRegulars() throws Exception {
		assertEquals("stock", EntityNameHelper.entityNameFor(Stock.class));
		assertEquals("quote", EntityNameHelper.entityNameFor(Quote.class));
		assertEquals("transaction", EntityNameHelper.entityNameFor(Transaction.class));
		assertEquals("portfolio", EntityNameHelper.entityNameFor(Portfolio.class));
		
	}
	
	@Test(expected=EntityNameHelperException.class)
	public void testIllegalClass() throws Exception {
		EntityNameHelper.entityNameFor(String.class);
		
	}
}
