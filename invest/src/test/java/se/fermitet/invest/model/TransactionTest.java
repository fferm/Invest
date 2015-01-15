package se.fermitet.invest.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.junit.Test;

public class TransactionTest {


	@Test
	public void testConstructor() throws Exception {
		Transaction tx = new Transaction();
		assertNotNull("Transaction itself not null", tx);
		assertNotNull("Transaction date not null", tx.getDate());
		assertEquals("Transaction date today if used with default constructor", LocalDate.now(), tx.getDate());
	}
	
	@Test
	public void testDateProperty() throws Exception {
		Transaction tx = new Transaction();
		
		LocalDate otherDate = new LocalDate(2013, 1, 1);
		Transaction newTx = tx.setDate(otherDate);
		assertEquals("Changed date", otherDate, tx.getDate());
		assertSame("return same object", tx, newTx);
		
		try  {
			tx.setDate(null);
			fail("Setting date to null should give IllegalArgumentException");
		} catch (IllegalArgumentException ex) {
			// Test OK
		}
	}
	
	@Test
	public void testStockProperty() throws Exception {
		Transaction tx = new Transaction();
		Stock stock = new Stock("TST");
		
		assertNull("null when created", tx.getStock());
		
		Transaction newTx = tx.setStock(stock);
		assertEquals("after setStock()", stock, tx.getStock());
		assertSame("setter returns object", tx, newTx);
		
		tx.setStock(null);
		assertNull("after set back to null", tx.getStock());
	}
	
	@Test
	public void testNumberProperty() throws Exception {
		Transaction tx = new Transaction();
		
		assertNull("null when created", tx.getNumber());
		
		Transaction newTX = tx.setNumber(10);
		assertEquals("after setNumber()", new Integer(10), tx.getNumber());
		assertSame("setter returns object", tx, newTX);
		
		tx.setNumber(null);
		assertNull("after set back to null", tx.getNumber());
	}
	
	@Test
	public void testPriceProperty() throws Exception {
		Transaction tx = new Transaction();
		
		assertNull("null when created", tx.getPrice());
		
		Money newPrice = Money.parse("SEK 100");
		Transaction newTx = tx.setPrice(newPrice);
		assertEquals("after setPrice", newPrice, tx.getPrice());
		assertSame("setter returns object", tx, newTx);
		
		tx.setPrice(null);
		assertNull("after set back to null", tx.getPrice());
	}
	
	@Test
	public void testFeeProperty() throws Exception {
		Transaction tx = new Transaction();
		
		assertNull("null when created", tx.getFee());
		
		Money newFee = Money.parse("SEK 100");
		Transaction newTx = tx.setFee(newFee);
		assertEquals("after setFee", newFee, tx.getFee());
		assertSame("setter returns object", tx, newTx);
		
		tx.setFee(null);
		assertNull("after set back to null", tx.getFee());
	}
	
	@Test
	public void testValueObject() throws Exception {
		LocalDate d1 = new LocalDate(2012, 1, 1);
		LocalDate d2 = new LocalDate(2013, 2, 2);
		
		Stock s1 = new Stock("s1");
		Stock s2 = new Stock("s2");
		
		int num1 = 1;
		int num2 = 2;
		
		Money price1 = Money.parse("SEK 1");
		Money price2 = Money.parse("SEK 2");
		
		Money fee1 = Money.parse("NOK 1");
		Money fee2 = Money.parse("NOK 2");
		
		Transaction t1 = new Transaction().setDate(d1).setStock(s1).setNumber(num1).setPrice(price1).setFee(fee1);
		Transaction sameValues = new Transaction().setDate(d1).setStock(s1).setNumber(num1).setPrice(price1).setFee(fee1);
		
		Transaction diffDate = new Transaction().setDate(d2).setStock(s1).setNumber(num1).setPrice(price1).setFee(fee1);

		Transaction diffStock = new Transaction().setDate(d1).setStock(s2).setNumber(num1).setPrice(price1).setFee(fee1);
		Transaction nullStock = new Transaction().setDate(d1).setStock(null).setNumber(num1).setPrice(price1).setFee(fee1);
		
		Transaction diffNum = new Transaction().setDate(d1).setStock(s1).setNumber(num2).setPrice(price1).setFee(fee1);
		Transaction nullNum = new Transaction().setDate(d1).setStock(s1).setNumber(null).setPrice(price1).setFee(fee1);
		
		Transaction diffPrice = new Transaction().setDate(d1).setStock(s1).setNumber(num1).setPrice(price2).setFee(fee1);
		Transaction nullPrice = new Transaction().setDate(d1).setStock(s1).setNumber(num1).setPrice(null).setFee(fee1);
		
		Transaction diffFee = new Transaction().setDate(d1).setStock(s1).setNumber(num1).setPrice(price1).setFee(fee2);
		Transaction nullFee = new Transaction().setDate(d1).setStock(s1).setNumber(num1).setPrice(price1).setFee(null);
		
		assertTrue("Equal to itself", t1.equals(t1));
		assertTrue("Equal to same values", t1.equals(sameValues));

		assertFalse("Not equal to different date", t1.equals(diffDate));
		
		assertFalse("Not equal to different stock", t1.equals(diffStock));
		assertFalse("Not equal to null stock", t1.equals(nullStock));

		assertFalse("Not equal to different number", t1.equals(diffNum));
		assertFalse("Not equal to null number", t1.equals(nullNum));

		assertFalse("Not equal to different price", t1.equals(diffPrice));
		assertFalse("Not equal to null price", t1.equals(nullPrice));

		assertFalse("Not equal to different fee", t1.equals(diffFee));
		assertFalse("Not equal to null fee", t1.equals(nullFee));
		
		assertFalse("Not equal to object of different class", t1.equals("TST"));
		assertFalse("Not equal to null", t1.equals(null));
		
		assertTrue("Equal objects have equal hash codes", t1.hashCode() == sameValues.hashCode());

		
		
	}
	
	
}
