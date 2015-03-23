package se.fermitet.invest.domain;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

public class PortfolioTest {

	@Test
	public void testDefaultConstructor() throws Exception {
		Portfolio port = new Portfolio();
		assertNotNull(port);
	}
	
	@Test
	public void testNameConstructor() throws Exception {
		Portfolio port = new Portfolio("a name");
		assertNotNull(port);
		assertEquals("a name", port.getName());
	}
	
	@Test
	public void testNameProperty() throws Exception {
		Portfolio port = new Portfolio();
		assertNull("getter before", port.getName());
		
		port.setName("new name");
		
		assertEquals("after set", "new name", port.getName());
	}

	@Test
	public void testMustHaveName() throws Exception {
		// Null
		Portfolio port = new Portfolio();
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Portfolio>> results = validator.validate(port);
		
		assertEquals("size", 1, results.size());
		
		ConstraintViolation<Portfolio> violation = results.iterator().next();
		assertEquals("propertyPath", "name", violation.getPropertyPath().toString());
		
		// empty
		port.setName("");
		
		results = validator.validate(port);
		assertEquals("size", 1, results.size());
		
		violation = results.iterator().next();
		assertEquals("propertyPath", "name", violation.getPropertyPath().toString());
	}
	
	@Test
	public void testValueObject() throws Exception {
		Portfolio orig = new Portfolio("orig");
		Portfolio copy = new Portfolio("orig");
		Portfolio nullName = new Portfolio();
		Portfolio nullName2 = new Portfolio();
		Portfolio other = new Portfolio("other");
		
		assertTrue("Equal to itself", orig.equals(orig));
		assertTrue("Equal to copy", orig.equals(copy));
		assertTrue("Two with null names equal", nullName.equals(nullName2));
		
		assertFalse("Not equal to null name", orig.equals(nullName));
		assertFalse("Not equal to other name", orig.equals(other));
		assertFalse("not equal to null", orig.equals(null));
		assertFalse("Not equal to other class", orig.equals("A STRING"));
		
		assertTrue("Equal objects have same hash", orig.hashCode() == copy.hashCode());
		assertTrue("Equal objects with null names have same hash", nullName.hashCode() == nullName2.hashCode());
	}
	
	@Test
	public void testToString() throws Exception {
		String name = "NAME";

		Portfolio empty = new Portfolio();
		Portfolio hasName = new Portfolio(name);
		
		String expEmpty  = "[ Portfolio { name : null } ]";
		String expName = "[ Portfolio { name : " + name + " } ]";
		
		assertEquals("empty", expEmpty, empty.toString());
		assertEquals("name", expName, hasName.toString());
	}

	

}
