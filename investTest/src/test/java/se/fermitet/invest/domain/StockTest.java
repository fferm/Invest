package se.fermitet.invest.domain;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

public class StockTest {

	@Test
	public void testDefaultConstructor() throws Exception {
		Stock stock = new Stock();
		assertNotNull(stock);
		assertNull(stock.getName());
		assertNull(stock.getSymbol());
	}
	
	@Test
	public void testSymbolConstructor() throws Exception {
		Stock stock = new Stock("TST");
		assertNotNull(stock);
		assertEquals("TST", stock.getSymbol());
	}
	
	@Test
	public void testDoubleConstructor() throws Exception {
		String name = "name";
		String symbol = "symbol";
		
		Stock stock = new Stock(name, symbol);
		
		assertNotNull(stock);
		assertEquals("name", name, stock.getName());
		assertEquals("symbol", symbol, stock.getSymbol());
	}
	
	@Test
	public void testSymbolProperty() throws Exception {
		String SYMBOL = "SYMBOL";
		
		Stock stock = new Stock(SYMBOL);
		assertEquals("getter", SYMBOL, stock.getSymbol());
		
		String NEWSYMBOL = "NEW SYMBOL";
		stock.setSymbol(NEWSYMBOL);
		assertEquals("getter after setter", NEWSYMBOL, stock.getSymbol());
	}
	
	@Test
	public void testNameProperty() throws Exception {
		Stock stock = new Stock("TST");
		
		assertNull("getter before", stock.getName());
		
		String newName = "new name";
		stock.setName(newName);
		assertEquals("getter after set", newName, stock.getName());
	}
	
	@Test
	public void testMustHaveSymbol() throws Exception {
		// Null
		Stock stock = new Stock(null);
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Stock>> results = validator.validate(stock);
		
		assertEquals("size", 1, results.size());
		
		ConstraintViolation<Stock> violation = results.iterator().next();
		assertEquals("propertyPath", "symbol", violation.getPropertyPath().toString());
		
		// empty
		stock = new Stock("");
		
		results = validator.validate(stock);
		assertEquals("size", 1, results.size());
		
		violation = results.iterator().next();
		assertEquals("propertyPath", "symbol", violation.getPropertyPath().toString());
		
	}
	
	@Test
	public void testValueObject() throws Exception {
		String sym1 = "SYM1";
		String sym2 = "SYM2";
		String name1 = "name 1";
		String name2 = "name 2";
		
		Stock s1 = new Stock(name1, sym1);
		Stock s2 = new Stock(name1, sym1);
		Stock diffSym = new Stock(name1, sym2);
		Stock diffName = new Stock(name2, sym1);
		Stock nullName1 = new Stock(null, sym1);
		Stock nullName2 = new Stock(null, sym1);
		
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
	
	@Test
	public void testToString() throws Exception {
		String symbol = "SYMBOL";
		String name = "NAME";
		
		Stock empty = new Stock();
		Stock hasSymbol = new Stock(symbol);
		Stock hasBoth = new Stock(name, symbol);
		
		String expEmpty  = "[ Stock { symbol : null | name : null } ]";
		String expSymbol = "[ Stock { symbol : " + symbol + " | name : null } ]";
		String expBoth   = "[ Stock { symbol : " + symbol + " | name : " + name + " } ]";
		
		assertEquals("hasBoth", expBoth, hasBoth.toString());
		assertEquals("hasSymbol", expSymbol, hasSymbol.toString());
		assertEquals("empty", expEmpty, empty.toString());
	}

	
	
}
