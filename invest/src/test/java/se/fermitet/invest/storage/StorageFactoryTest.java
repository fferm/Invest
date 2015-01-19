package se.fermitet.invest.storage;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class StorageFactoryTest {
	@Test
	public void testSameStorageInstance() throws Exception {
		Storage s1 = StorageFactory.instance().getStorage();
		Storage s2 = StorageFactory.instance().getStorage();
		
		assertSame(s1, s2);
	}
}
