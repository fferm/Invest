package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;

public class MongoStorageTest_general extends MongoStorageTest_abstract {
	@Test
	public void testEmptyWhenStarting() throws Exception {
		List<Stock> all = objUnderTest.getAllStocks();
		assertEquals(0, all.size());
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



