package se.fermitet.invest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StockTest {

	@Test
	public void testConstructor() throws Exception {
		Stock stock = new Stock("TST");
		assertNotNull(stock);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorSymbolCannotBeNull() throws Exception {
		new Stock(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorSymbolCannotBeEmpty() throws Exception {
		new Stock("");
	}
	
	@Test
	public void testSymbolProperty() throws Exception {
		String SYMBOL = "SYMBOL";
		
		Stock stock = new Stock(SYMBOL);
		assertEquals("getter", SYMBOL, stock.getSymbol());
		
		String NEWSYMBOL = "NEW SYMBOL";
		Stock newStock = stock.setSymbol(NEWSYMBOL);
		assertEquals("getter after setter", NEWSYMBOL, stock.getSymbol());
		assertSame("same", stock, newStock);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetterSymbolCannotBeNull() throws Exception {
		Stock stock = new Stock("TST");
		stock.setSymbol(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetterSymbolCannotBeEmpty() throws Exception {
		Stock stock = new Stock("TST");
		stock.setSymbol("");
	}
	
	@Test
	public void testNameProperty() throws Exception {
		Stock stock = new Stock("TST");
		
		assertNull("getter before", stock.getName());
		
		String newName = "new name";
		Stock newStock = stock.setName(newName);
		assertEquals("getter after set", newName, stock.getName());
		assertSame("same", stock, newStock);
	}
	
	@Test
	public void testValueObject() throws Exception {
		String sym1 = "SYM1";
		String sym2 = "SYM2";
		String name1 = "name 1";
		String name2 = "name 2";
		
		Stock s1 = new Stock(sym1).setName(name1);
		Stock s2 = new Stock(sym1).setName(name1);
		Stock diffSym = new Stock(sym2).setName(name1);
		Stock diffName = new Stock(sym1).setName(name2);
		Stock nullName1 = new Stock(sym1).setName(null);
		Stock nullName2 = new Stock(sym1).setName(null);
		
		assertTrue("Equal to itself", s1.equals(s1));
		assertTrue("Equal to object with same symbol and name", s1.equals(s2));
		assertTrue("Equal to equal object where both have null names", nullName1.equals(nullName2));
		
		assertFalse("Not equal to object with different symbol", s1.equals(diffSym));
		assertFalse("Not equal to object with different name", s1.equals(diffName));
		assertFalse("Not equal to object with null name", s1.equals(nullName1));
		assertFalse("Not equal to object of other class", s1.equals("TST"));
		assertFalse("Not equal to null", s1.equals(null));
		
		assertTrue("Equal objects have same hash", s1.hashCode() == s2.hashCode());
		assertTrue("Equal objects have same hash (both have null names", nullName1.hashCode() == nullName2.hashCode());
	}

	
	
}
