package se.fermitet.invest.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class StockTest {

	@Test
	public void constructor() throws Exception {
		Stock stock = new Stock();
		assertNotNull(stock);
	}
	
	
}
