package se.fermitet.invest.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public class StocksModelTest {
	private StockModel stocksModelWithTestStorageFactory;
	private Storage mockedStorage;

	@Before
	public void setUp() throws Exception {
		this.stocksModelWithTestStorageFactory = new StocksModelWithTestStorageFactory();
		mockedStorage = stocksModelWithTestStorageFactory.storage;
	}
	
	@Test
	public void testNoPublicConstructor() throws Exception {
		Constructor<?>[] constructors = StockModel.class.getConstructors();
		for (@SuppressWarnings("rawtypes") Constructor constructor : constructors) {
			assertTrue("Found a public constructor: " + constructor, (constructor.getModifiers() & Modifier.PUBLIC) == 0);
		}
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
		
		verify(mockedStorage).deleteStock(toDelete);
	}
	
	@Test
	public void testSave() throws Exception {
		Stock stock = new Stock("name", "symbol");
		stocksModelWithTestStorageFactory.save(stock);
		
		verify(mockedStorage).saveStock(stock);
		
	}
}

class StocksModelWithTestStorageFactory extends StockModel {
	public StocksModelWithTestStorageFactory() {
		super();
	}
	
	@Override
	protected StorageFactory createStorageFactory() {
		return new StorageFactory() {
			@Override
			public Storage getStorage() {
				return mock(Storage.class);
			}
		};
	}
}
