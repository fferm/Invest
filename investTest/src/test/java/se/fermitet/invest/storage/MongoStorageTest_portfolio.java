package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.storage.dataFiller.FillExampleData;

public class MongoStorageTest_portfolio extends MongoStorageTest_abstract {

	@Test
	public void testStoreAndRetrieveOne() throws Exception {
		Portfolio port = new Portfolio("a name");
		
		objUnderTest.savePortfolio(port);
		
		List<Portfolio> all = objUnderTest.getAllPortfolios();
		
		assertEquals("Size", 1, all.size());
		assertTrue("Contains", all.contains(port));
		
		Portfolio fromDb = all.get(0);
		assertEquals("id", port.getId(), fromDb.getId());
	}
	
	@Test
	public void testRemove() throws Exception {
		Portfolio p1 = new Portfolio("first");
		Portfolio p2 = new Portfolio("second");

		objUnderTest.savePortfolio(p1);;
		objUnderTest.savePortfolio(p2);

		objUnderTest.deletePortfolio(p1);

		List<Portfolio> allLeft = objUnderTest.getAllPortfolios();

		assertEquals("size", 1, allLeft.size());
		assertTrue("contains", allLeft.contains(p2));
	}
	
	@Test
	public void testGetById() throws Exception {
		Portfolio p1 = new Portfolio("first");
		Portfolio p2 = new Portfolio("second");

		objUnderTest.savePortfolio(p1);;
		objUnderTest.savePortfolio(p2);

		Portfolio fromDb = objUnderTest.getPortfolioById(p1.getId());
		
		assertNotNull("not null", fromDb);
		assertEquals("equal", p1, fromDb);
		
		Portfolio shouldBeNull = objUnderTest.getPortfolioById(UUID.randomUUID());
		assertNull("should be null", shouldBeNull);
	}

	@Test
	public void testGetPortfolioByName() throws Exception {
		new FillExampleData(objUnderTest).fillPortfolios();

		Portfolio retrieved = objUnderTest.getPortfolioByName("Privat");
		
		assertNotNull("not null", retrieved);
		
		assertEquals("name", "Privat", retrieved.getName());
	}


}
