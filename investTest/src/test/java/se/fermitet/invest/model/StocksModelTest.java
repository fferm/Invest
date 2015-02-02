package se.fermitet.invest.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class StocksModelTest {
	private StocksModelWithTestStorageFactory stocksModelWithTestStorageFactory;
	private Storage mockedStorage;

	@Before
	public void setUp() throws Exception {
		this.stocksModelWithTestStorageFactory = new StocksModelWithTestStorageFactory();
		mockedStorage = stocksModelWithTestStorageFactory.storage;
	}
	
	@Test
	public void testGetAllStocks() throws Exception {
		stocksModelWithTestStorageFactory.getAllStocks();
		verify(mockedStorage).getAllStocks();
	}
	
	@Test
	public void testGetStockById() throws Exception {
		UUID id = UUID.randomUUID();
		stocksModelWithTestStorageFactory.getStockById(id);
		verify(mockedStorage).getStockById(id);
		
	}
	
	@Test
	public void testDelete() throws Exception {
		Stock toDelete = new Stock("TO DELETE");
		stocksModelWithTestStorageFactory.deleteStock(toDelete);
		
		verify(mockedStorage).delete(toDelete);
	}
}

class StocksModelWithTestStorageFactory extends StocksModel {
	public StocksModelWithTestStorageFactory() {
		super();
	}
	
	@Override
	protected StorageFactory createStorageFactory() {
		return new StorageFactory() {
			@Override
			protected Storage createStorage() {
				return mock(Storage.class);
			}
		};
	}
}