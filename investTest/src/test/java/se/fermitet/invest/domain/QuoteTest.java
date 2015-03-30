package se.fermitet.invest.domain;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.junit.Test;

public class QuoteTest {
	@Test
	public void testDefaultConstructor() throws Exception {
		Quote quote = new Quote();
		assertNotNull(quote);
	}
	
	@Test
	public void testStockProperty() throws Exception {
		Stock stock = new Stock("TEST");
		
		Quote quote = new Quote();
		assertNull(quote.getStock());
		
		quote.setStock(stock);
		assertEquals(stock, quote.getStock());
	}
	
	@Test
	public void testMustHaveStock() throws Exception {
		// Null
		Quote quote = new Quote();
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Quote>> results = validator.validate(quote);
		
		assertEquals("size", 1, results.size());
		
		ConstraintViolation<Quote> violation = results.iterator().next();
		assertEquals("propertyPath", "stock", violation.getPropertyPath().toString());
		
		// Invalid stock
		quote.setStock(new Stock());

		results = validator.validate(quote);
		
		assertEquals("size", 1, results.size());
		
		violation = results.iterator().next();
		assertTrue("propertyPath", violation.getPropertyPath().toString().startsWith("stock."));
	}
	
	@Test
	public void testDateProperty() throws Exception {
		LocalDate deflt = LocalDate.now();
		LocalDate expected = deflt.minusDays(100);
		
		Quote quote = new Quote();
		assertEquals(deflt, quote.getDate());
		
		quote.setDate(expected);
		
		assertEquals(expected, quote.getDate());
	}
	
	@Test
	public void testMustHaveDate() throws Exception {
		// Null
		Quote quote = new Quote();
		quote.setStock(new Stock("TEST"));
		quote.setDate(null);
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Quote>> results = validator.validate(quote);
		
		assertEquals("size", 1, results.size());
		
		ConstraintViolation<Quote> violation = results.iterator().next();
		assertEquals("propertyPath", "date", violation.getPropertyPath().toString());
	}

	@Test
	public void testLastProperty() throws Exception {
		Money expected = Money.parse("SEK 200");
		
		Quote quote = new Quote();
		assertNull(quote.getLast());
		
		quote.setLast(expected);
		
		assertEquals(expected, quote.getLast());
	}

	@Test
	public void testHighProperty() throws Exception {
		Money expected = Money.parse("SEK 200");
		
		Quote quote = new Quote();
		assertNull(quote.getHigh());
		
		quote.setHigh(expected);
		
		assertEquals(expected, quote.getHigh());
	}

	@Test
	public void testLowProperty() throws Exception {
		Money expected = Money.parse("SEK 200");
		
		Quote quote = new Quote();
		assertNull(quote.getLow());
		
		quote.setLow(expected);
		
		assertEquals(expected, quote.getLow());
	}

	@Test
	public void testBidProperty() throws Exception {
		Money expected = Money.parse("SEK 200");
		
		Quote quote = new Quote();
		assertNull(quote.getBid());
		
		quote.setBid(expected);
		
		assertEquals(expected, quote.getBid());
	}

	@Test
	public void testAskProperty() throws Exception {
		Money expected = Money.parse("SEK 200");
		
		Quote quote = new Quote();
		assertNull(quote.getAsk());
		
		quote.setAsk(expected);
		
		assertEquals(expected, quote.getAsk());
	}

	@Test
	public void testVolumeProperty() throws Exception {
		Integer expected = 1000000;
		
		Quote quote = new Quote();
		assertNull(quote.getVolume());
		
		quote.setVolume(expected);
		
		assertEquals(expected, quote.getVolume());
	}

	@Test
	public void testTurnoverProperty() throws Exception {
		Money expected = Money.parse("SEK 20000000");
		
		Quote quote = new Quote();
		assertNull(quote.getTurnover());
		
		quote.setTurnover(expected);
		
		assertEquals(expected, quote.getTurnover());
	}

	@Test
	public void testValueObject() throws Exception {
		Stock s1 = new Stock("S1");
		Stock s2 = new Stock("S2");
		
		LocalDate d1 = LocalDate.now();
		LocalDate d2 = d1.minusDays(100);
		
		Money m1 = Money.parse("SEK 200");
		Money m2 = Money.parse("SEK 100");
		
		Integer i1 = 1000;
		Integer i2 = 2000;
		
		Quote original = new Quote(s1, d1, m1, m1, m1, m1, m1, i1, m1);
		Quote copy = new Quote(s1, d1, m1, m1, m1, m1, m1, i1, m1);
		
		Quote otherStock = new Quote(s2, d1, m1, m1, m1, m1, m1, i1, m1);
		Quote nullStock  = new Quote(null, d1, m1, m1, m1, m1, m1, i1, m1);
		
		Quote  otherDate = new Quote(s1, d2, m1, m1, m1, m1, m1, i1, m1);
		Quote  nullDate  = new Quote(s1, null, m1, m1, m1, m1, m1, i1, m1);

		Quote  otherAsk = new Quote(s1, d1, m2, m1, m1, m1, m1, i1, m1);
		Quote  nullAsk  = new Quote(s1, d1, null, m1, m1, m1, m1, i1, m1);
		
		Quote  otherBid = new Quote(s1, d1, m1, m2, m1, m1, m1, i1, m1);
		Quote  nullBid  = new Quote(s1, d1, m1, null, m1, m1, m1, i1, m1);
		
		Quote  otherLast = new Quote(s1, d1, m1, m1, m2, m1, m1, i1, m1);
		Quote  nullLast  = new Quote(s1, d1, m1, m1, null, m1, m1, i1, m1);

		Quote  otherHigh = new Quote(s1, d1, m1, m1, m1, m2, m1, i1, m1);
		Quote  nullHigh  = new Quote(s1, d1, m1, m1, m1, null, m1, i1, m1);

		Quote  otherLow = new Quote(s1, d1, m1, m1, m1, m1, m2, i1, m1);
		Quote  nullLow  = new Quote(s1, d1, m1, m1, m1, m1, null, i1, m1);

		Quote  otherVolume = new Quote(s1, d1, m1, m1, m1, m1, m1, i2, m1);
		Quote  nullVolume  = new Quote(s1, d1, m1, m1, m1, m1, m1, null, m1);

		Quote  otherTurnover = new Quote(s1, d1, m1, m1, m1, m1, m1, i1, m2);
		Quote  nullTurnover  = new Quote(s1, d1, m1, m1, m1, m1, m1, i1, null);

		assertTrue(original.equals(original));
		assertTrue(original.equals(copy));
		
		assertFalse(original.equals(otherStock));
		assertFalse(original.equals(nullStock));

		assertFalse(original.equals(otherDate));
		assertFalse(original.equals(nullDate));
		
		assertFalse(original.equals(otherAsk));
		assertFalse(original.equals(nullAsk));
		
		assertFalse(original.equals(otherBid));
		assertFalse(original.equals(nullBid));
		
		assertFalse(original.equals(otherLast));
		assertFalse(original.equals(nullLast));
		
		assertFalse(original.equals(otherHigh));
		assertFalse(original.equals(nullHigh));
		
		assertFalse(original.equals(otherLow));
		assertFalse(original.equals(nullLow));

		assertFalse(original.equals(otherVolume));
		assertFalse(original.equals(nullVolume));
		
		assertFalse(original.equals(otherTurnover));
		assertFalse(original.equals(nullTurnover));
		
		assertFalse(original.equals("A STRING"));
		assertFalse(original.equals(null));
		
		assertTrue(original.hashCode() == copy.hashCode());
	}
	
	@Test
	public void testToString() throws Exception {
		Stock s1 = new Stock("S1");
		
		LocalDate d1 = LocalDate.now();
		
		Money m1 = Money.parse("SEK 200");
		
		Integer i1 = 1000;
		
		Quote original = new Quote(s1, d1, m1, m1, m1, m1, m1, i1, m1);
		Quote nullStock  = new Quote(null, d1, m1, m1, m1, m1, m1, i1, m1);
		Quote  nullDate  = new Quote(s1, null, m1, m1, m1, m1, m1, i1, m1);
		Quote  nullAsk  = new Quote(s1, d1, null, m1, m1, m1, m1, i1, m1);
		Quote  nullBid  = new Quote(s1, d1, m1, null, m1, m1, m1, i1, m1);
		Quote  nullLast  = new Quote(s1, d1, m1, m1, null, m1, m1, i1, m1);
		Quote  nullHigh  = new Quote(s1, d1, m1, m1, m1, null, m1, i1, m1);
		Quote  nullLow  = new Quote(s1, d1, m1, m1, m1, m1, null, i1, m1);
		Quote  nullVolume  = new Quote(s1, d1, m1, m1, m1, m1, m1, null, m1);
		Quote  nullTurnover  = new Quote(s1, d1, m1, m1, m1, m1, m1, i1, null);
		
		String expOriginal  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullStock  = "[ Quote { stock : null | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullDate  = "[ Quote { stock : " + s1.toString() + " | date : null | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullAsk  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : null | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullBid  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : null | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullLast  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : null | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullHigh  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : null | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullLow  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : null | volume : " + i1.toString() + " | turnover : " + m1.toString() + " } ]";
		String expNullVolume  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : null | turnover : " + m1.toString() + " } ]";
		String expNullTurnover  = "[ Quote { stock : " + s1.toString() + " | date : " + d1.toString() + " | ask : " + m1.toString() + " | bid : " + m1.toString() + " | last : " + m1.toString() + " | high : " + m1.toString() + " | low : " + m1.toString() + " | volume : " + i1.toString() + " | turnover : null } ]";
		
		assertEquals(expOriginal, original.toString());
		assertEquals(expNullStock, nullStock.toString());
		assertEquals(expNullDate, nullDate.toString());
		assertEquals(expNullAsk, nullAsk.toString());
		assertEquals(expNullBid, nullBid.toString());
		assertEquals(expNullLast, nullLast.toString());
		assertEquals(expNullHigh, nullHigh.toString());
		assertEquals(expNullLow, nullLow.toString());
		assertEquals(expNullVolume, nullVolume.toString());
		assertEquals(expNullTurnover, nullTurnover.toString());
	}



}
