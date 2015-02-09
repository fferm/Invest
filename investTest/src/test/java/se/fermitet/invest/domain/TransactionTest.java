package se.fermitet.invest.domain;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {

	private Validator validator;

	@Before
	public void setUp() throws Exception {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void testConstructor() throws Exception {
		Transaction tx = new Transaction();
		assertNotNull("Transaction itself not null", tx);
		assertNotNull("Transaction date not null", tx.getDate());
		assertEquals("Transaction date today if used with default constructor", LocalDate.now(), tx.getDate());
	}

	@Test
	public void testConstructorWithArguments() throws Exception {
		// date, stock, number, price, fee
		Stock stock = new Stock("TST", "Test");
		LocalDate date = LocalDate.now();
		int number = 20;
		Money price = Money.parse("SEK 100");
		Money fee = Money.parse("SEK 20");

		Transaction trans = new Transaction(stock, date, number, price, fee);

		assertEquals("stock", stock, trans.getStock());
		assertEquals("date", date, trans.getDate());
		assertEquals("number", number, trans.getNumber());
		assertEquals("price", price,trans.getPrice());
		assertEquals("fee", fee, trans.getFee());
	}


	@Test
	public void testDateProperty() throws Exception {
		Transaction tx = new Transaction();

		LocalDate otherDate = new LocalDate(2013, 1, 1);
		tx.setDate(otherDate);
		assertEquals("Changed date", otherDate, tx.getDate());

		tx.setDate(null);
		assertNull("after set back to null", tx.getDate());
	}

	@Test
	public void testStockProperty() throws Exception {
		Transaction tx = new Transaction();
		Stock stock = new Stock("TST");

		assertNull("null when created", tx.getStock());

		tx.setStock(stock);
		assertEquals("after setStock()", stock, tx.getStock());

		tx.setStock(null);
		assertNull("after set back to null", tx.getStock());
	}

	@Test
	public void testNumberProperty() throws Exception {
		Transaction tx = new Transaction();

		assertEquals("0 when created", 0, tx.getNumber());

		tx.setNumber(10);
		assertEquals("after setNumber()", 10, tx.getNumber());
	}

	@Test
	public void testPriceProperty() throws Exception {
		Transaction tx = new Transaction();

		assertNull("null when created", tx.getPrice());

		Money newPrice = Money.parse("SEK 100");
		tx.setPrice(newPrice);
		assertEquals("after setPrice", newPrice, tx.getPrice());

		tx.setPrice(null);
		assertNull("after set back to null", tx.getPrice());
	}

	@Test
	public void testFeeProperty() throws Exception {
		Transaction tx = new Transaction();

		assertNull("null when created", tx.getFee());

		Money newFee = Money.parse("SEK 100");
		tx.setFee(newFee);
		assertEquals("after setFee", newFee, tx.getFee());

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

		Transaction t1 = new Transaction(s1, d1, num1, price1, fee1);
		Transaction sameValues = new Transaction(s1, d1, num1, price1, fee1);

		Transaction diffDate = new Transaction(s1, d2, num1, price1, fee1);
		Transaction nullDate = new Transaction(s1, null, num1, price1, fee1);

		Transaction diffStock = new Transaction(s2, d1, num1, price1, fee1);
		Transaction nullStock = new Transaction(null, d1, num1, price1, fee1);

		Transaction diffNum = new Transaction(s1, d1, num2, price1, fee1);

		Transaction diffPrice = new Transaction(s1, d1, num1, price2, fee1);
		Transaction nullPrice = new Transaction(s1, d1, num1, null, fee1);

		Transaction diffFee = new Transaction(s1, d1, num1, price1, fee2);
		Transaction nullFee = new Transaction(s1, d1, num1, price1, null);

		assertTrue("Equal to itself", t1.equals(t1));
		assertTrue("Equal to same values", t1.equals(sameValues));

		assertFalse("Not equal to different date", t1.equals(diffDate));
		assertFalse("Not equal to null date", t1.equals(nullDate));

		assertFalse("Not equal to different stock", t1.equals(diffStock));
		assertFalse("Not equal to null stock", t1.equals(nullStock));

		assertFalse("Not equal to different number", t1.equals(diffNum));

		assertFalse("Not equal to different price", t1.equals(diffPrice));
		assertFalse("Not equal to null price", t1.equals(nullPrice));

		assertFalse("Not equal to different fee", t1.equals(diffFee));
		assertFalse("Not equal to null fee", t1.equals(nullFee));

		assertFalse("Not equal to object of different class", t1.equals("TST"));
		assertFalse("Not equal to null", t1.equals(null));

		assertTrue("Equal objects have equal hash codes", t1.hashCode() == sameValues.hashCode());
	}

	@Test
	public void testValidateStock() throws Exception {
		Transaction nullStock = new Transaction(null, LocalDate.now(), 10, Money.parse("SEK 100"), Money.parse("SEK 10"));

		Set<ConstraintViolation<Transaction>> results = validator.validate(nullStock);

		checkValidatorResults(results, "stock");
	}


	@Test
	public void testValidateDate() throws Exception {
		Transaction nullDate = new Transaction(new Stock("TST"), null, 10, Money.parse("SEK 100"), Money.parse("SEK 10"));

		Set<ConstraintViolation<Transaction>> results = validator.validate(nullDate);

		checkValidatorResults(results, "date");
	}

	@Test
	public void testValidatePrice() throws Exception {
		Transaction nullPrice = new Transaction(new Stock("TST"), LocalDate.now(), 10, null, Money.parse("SEK 10"));

		Set<ConstraintViolation<Transaction>> results = validator.validate(nullPrice);

		checkValidatorResults(results, "price");
	}

	@Test
	public void testValidateFee() throws Exception {
		// Fee can be null
	}

	@Test
	public void testValidateNumber() throws Exception {
		Transaction zeroNumber = new Transaction(new Stock("TST"), LocalDate.now(), 0, Money.parse("SEK 100"), Money.parse("SEK 10"));

		Set<ConstraintViolation<Transaction>> results = validator.validate(zeroNumber);

		checkValidatorResults(results, "number");
	}
	
	@Test
	public void testValidateValidStock() throws Exception {
		Transaction invalidStock = new Transaction(new Stock(), LocalDate.now(), 0, Money.parse("SEK 100"), Money.parse("SEK 10"));

		Set<ConstraintViolation<Transaction>> results = validator.validate(invalidStock);
		assertTrue("size", results.size() > 0);
	}
	
	@Test
	public void testValidateEmpty() throws Exception {
		Transaction empty = new Transaction();
		
		@SuppressWarnings("unused")
		Set<ConstraintViolation<Transaction>> results = validator.validate(empty);
		// Should not give weird exceptions
	}

	private void checkValidatorResults(Set<ConstraintViolation<Transaction>> results, String propertyName) {
		assertTrue(propertyName + " size", results.size() > 0);

		ConstraintViolation<Transaction> violation = results.iterator().next();
		assertEquals(propertyName + " path", propertyName, violation.getPropertyPath().toString());
	}
}
