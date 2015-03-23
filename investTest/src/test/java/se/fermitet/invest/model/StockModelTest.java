package se.fermitet.invest.model;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;

public class StockModelTest extends AbstractModelTest<StockModel> {
	
	public StockModelTest() {
		super(StockModel.class);
	}

	@Override
	public StockModel createModelWithMockedStorage() {
		return new StocksModelWithTestStorageFactory();
	}

	@Test
	public void testGetAll() throws Exception {
		model.getAll();
		verify(mockedStorage).getAllStocks();
	}
	
	@Test
	public void testGetStockById() throws Exception {
		UUID id = UUID.randomUUID();
		model.getById(id);
		verify(mockedStorage).getStockById(id);
	}
	
	@Test
	public void testDelete() throws Exception {
		Stock toDelete = new Stock("TO DELETE");
		model.delete(toDelete);
		
		verify(mockedStorage).deleteStock(toDelete);
	}
	
	@Test
	public void testSave() throws Exception {
		Stock stock = new Stock("name", "symbol");
		model.save(stock);
		
		verify(mockedStorage).saveStock(stock);
		
	}

}

class StocksModelWithTestStorageFactory extends StockModel {
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
}
