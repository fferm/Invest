package se.fermitet.invest.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.Storage;

public class StockModelTest extends AbstractModelTest<StockModel> {

	public StockModelTest() {
		super(StockModel.class);
	}

	@Override
	public StockModel createModelWithMockedStorage() {
		return new StockModelWithTestStorageFactory();
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

	@Test
	public void testDeleteWithAssociatedTransactions() throws Exception {
		Stock stock = new Stock();
		Transaction trans = new Transaction();
		List<Transaction> transactions = new ArrayList<Transaction>();

		when(mockedStorage.getTransactionsForStock(stock)).thenReturn(transactions); //Empty at this point

		model.delete(stock);  // should be OK

		transactions.add(trans);
		when(mockedStorage.getTransactionsForStock(stock)).thenReturn(transactions);  // Now contains something

		try {
			model.delete(stock);
			fail("should give exception");
		} catch (ModelException e) {
			assertEquals(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, e);
		}
	}
}

class StockModelWithTestStorageFactory extends StockModel {
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
}
