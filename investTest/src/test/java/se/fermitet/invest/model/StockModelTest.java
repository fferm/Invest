package se.fermitet.invest.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
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
			assertEquals(new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, stock), e);
		}
	}
	
	@Test
	public void testSaveSecondStockWithSameSymbol() throws Exception {
		Stock first = new Stock("A");
		Stock second = new Stock("A");
		Stock third = new Stock("B");
		
		when(mockedStorage.getStockBySymbol(first.getSymbol())).thenReturn(second);
		when(mockedStorage.getStockBySymbol(third.getSymbol())).thenReturn(null);
		
		model.save(third); // should be OK
		
		try {
			model.save(first);
			fail("Should give exception");
		} catch (ModelException e) {
			assertEquals(new ModelException(ModelExceptionType.CANNOT_SAVE_STOCK_SINCE_THERE_IS_ALREADY_A_STOCK_WITH_THAT_SYMBOL, first), e);
		}
	}
}

class StockModelWithTestStorageFactory extends StockModel {
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
}
