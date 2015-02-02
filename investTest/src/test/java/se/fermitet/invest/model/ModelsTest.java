package se.fermitet.invest.model;

import static org.junit.Assert.*;

import org.junit.Test;

import se.fermitet.invest.exception.InvestException;

public class ModelsTest {
	@Test
	public void testStocksModelsSame() throws Exception {
		StocksModel mod1 = Models.stocksModel();
		StocksModel mod2 = Models.stocksModel();
		
		assertNotNull(mod1);
		assertNotNull(mod2);
		assertSame(mod1, mod2);
	}
	
	@Test
	public void testStocksModelBasedOnClass() throws Exception {
		Model obj = Models.fromClass(StocksModel.class);
		
		assertNotNull(obj);
		assertEquals(StocksModel.class, obj.getClass());
	}
	
	@Test(expected = InvestException.class)
	public void testModelBasedOnUnknownClassGivesException() throws Exception {
		Models.fromClass(null);
	}
}
