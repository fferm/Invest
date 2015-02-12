package se.fermitet.invest.storage;

import static org.junit.Assert.*;

import org.junit.Test;


public class StorageFactoryTest {
	
	@Test
	public void testIsAMongoStorage() throws Exception {
		StorageFactory factory = new StorageFactory();
		
		Storage storage = factory.getStorage();
		
		assertEquals("class", MongoStorage.class, storage.getClass());
	}
}
