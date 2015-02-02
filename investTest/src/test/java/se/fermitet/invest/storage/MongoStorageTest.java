package se.fermitet.invest.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.MappedClass;

import se.fermitet.invest.domain.Stock;

import com.mongodb.MongoClient;

public class MongoStorageTest {
	private TestMongoStorage objUnderTest;
	
	@Before
	public void setUp() throws Exception {
		objUnderTest = new TestMongoStorage();
		objUnderTest.cleanUpDatabase();
	}
	
	@After
	public void tearDown() throws Exception {
		objUnderTest.cleanUpDatabase();
	}
	
	@Test
	public void testNoStocksWhenStarting() throws Exception {
		List<Stock> all = objUnderTest.getAllStocks();
		assertEquals(0, all.size());
	}
	
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
		Stock s1 = new Stock("S1").setName("S1");
		Stock s2 = new Stock("S2").setName("S2");
		
		objUnderTest.saveStock(s1);
		objUnderTest.saveStock(s2);
		
		objUnderTest.delete(s1);
		
		List<Stock> allLeft = objUnderTest.getAllStocks();
		
		assertEquals("size", 1, allLeft.size());
		assertTrue("contains", allLeft.contains(s2));
	}
	
	@Test
	public void testMongoThrowingExceptionShouldGiveStorageExceptionWithSameMessage() throws Exception {
		final String message = "TEST MESSAGE";
		
		ExceptionMongoStorage storage = new ExceptionMongoStorage(message);
		
		try {
			storage.getDatastore();
			fail("Didn't give an exception");
		} catch (StorageException e) {
			assertNotNull("Cause not null", e.getCause());
			assertEquals("Cause class", UnknownHostException.class, e.getCause().getClass());
			assertEquals("Message", message, e.getMessage());
		} catch (Exception e) {
			fail("Didn't give a StorageException, instead it was a " + e.getClass().getName());
		}
	}
}

class TestMongoStorage extends MongoStorage {
	@Override
	protected String getDbName() {
		return "testInvest";
	}

	public void cleanUpDatabase() throws Exception {
		Datastore ds = getDatastore();

		Collection<MappedClass> mappedClasses = morphia.getMapper().getMappedClasses();
		for (MappedClass mappedClass : mappedClasses) {
			Class<?> pojoClass = mappedClass.getClazz();
			ds.delete(ds.find(pojoClass));
		}
	}
}

class ExceptionMongoStorage extends TestMongoStorage {
	private String message;

	public ExceptionMongoStorage(String exceptionMessage) {
		super();
		this.message = exceptionMessage;
		client = null;
	}
	
	@Override
	protected MongoClient createMongoClient() throws UnknownHostException {
		throw new UnknownHostException(message);
	}
}


