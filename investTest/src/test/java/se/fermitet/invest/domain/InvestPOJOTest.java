package se.fermitet.invest.domain;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

public class InvestPOJOTest {
	@Test
	public void testConstructor() throws Exception {
		InvestPOJO obj = new TestInvestDomainObject();
		
		assertNotNull("not null", obj);
		assertNotNull("id", obj.getId());
	}
	
	@Test
	public void testValidateId() throws Exception {
		InvestPOJO obj = new TestInvestDomainObject();
		obj.id = null;
		
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		
		Set<ConstraintViolation<InvestPOJO>> results = validator.validate(obj);
		
		assertEquals("size", 1, results.size());
	}
}

class TestInvestDomainObject extends InvestPOJO {

	@Override
	protected void initToStringProperties() {
		// DO nothing
	}
	
}
