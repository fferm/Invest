package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.dataFiller.FillTestData;

public class MongoStorageTest_stock extends MongoStorageTest_abstract {
	@Test
	public void testStoreAndRetrieveOneStock() throws Exception {
		Stock stock = new Stock("TST");
		stock.setName("TestName");
		
		objUnderTest.saveStock(stock);
		
		List<Stock> all = objUnderTest.getAllStocks();
		
		assertEquals("Size", 1, all.size());
		assertTrue("Contains", all.contains(stock));
		
		Stock fromDb = all.get(0);
		assertEquals("id", stock.getId(), fromDb.getId());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Stock stock = new Stock("name", "symbol");
		
		objUnderTest.saveStock(stock);
		assertEquals("Size", 1, objUnderTest.getAllStocks().size());
		
		stock.setName("new name");
		stock.setSymbol("new symbol");
		
		objUnderTest.saveStock(stock);
		
		List<Stock> all = objUnderTest.getAllStocks();
		
		assertEquals("Size", 1, all.size());
		assertTrue("Contains", all.contains(stock));
	}
	
	@Test
	public void testGetStockById() throws Exception {
		Stock first = new Stock("Hej");
		first.setName("Ett namn");
		
		objUnderTest.saveStock(first);
		
		Stock fromDb = objUnderTest.getStockById(first.getId());
		
		assertNotNull("not null", fromDb);
		assertEquals("equal", first, fromDb);
		
		Stock shouldBeNull = objUnderTest.getStockById(UUID.randomUUID());
		assertNull("should be null", shouldBeNull);
	}
	
	@Test
	public void testRemoveStock() throws Exception {
		Stock s1 = new Stock("S1", "S1");
		Stock s2 = new Stock("S2", "S2");
		
		objUnderTest.saveStock(s1);
		objUnderTest.saveStock(s2);
		
		objUnderTest.deleteStock(s1);
		
		List<Stock> allLeft = objUnderTest.getAllStocks();
		
		assertEquals("size", 1, allLeft.size());
		assertTrue("contains", allLeft.contains(s2));
	}
	
	@Test
	public void testGetStocksBySymbol() throws Exception {
		new FillTestData(objUnderTest).fillStocks();

		Stock retrieved = objUnderTest.getStockBySymbol("AAK");
		
		assertNotNull("not null", retrieved);
		
		assertEquals("symbol", "AAK", retrieved.getSymbol());
	}
	

}
