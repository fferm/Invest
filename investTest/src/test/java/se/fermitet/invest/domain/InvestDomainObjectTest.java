package se.fermitet.invest.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class InvestDomainObjectTest {
	@Test
	public void testConstructor() throws Exception {
		InvestDomainObject obj = new InvestDomainObject();
		
		assertNotNull("not null", obj);
		assertNotNull("id", obj.getId());
	}
}
