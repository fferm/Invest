package se.fermitet.invest.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class StocksModelTest {
	private StocksModelWithTestStorageFactory stocksModelWithTestStorageFactory;

	@Before
	public void setUp() throws Exception {
		this.stocksModelWithTestStorageFactory = new StocksModelWithTestStorageFactory();
	}
	
	@Test
	public void testGetAllStocks() throws Exception {
		stocksModelWithTestStorageFactory.getAllStocks();
		verify(stocksModelWithTestStorageFactory.mockedStorage).getAllStocks();
	}
}

class StocksModelWithTestStorageFactory extends StocksModel {
	Storage mockedStorage = mock(Storage.class);

	@Override
	protected StorageFactory createStorageFactory() {
		return new StorageFactory() {
			@Override
			protected Storage createStorage() {
				return mockedStorage;
			}
		};
	}
}
