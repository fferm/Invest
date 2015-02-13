package se.fermitet.invest.model;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.Storage;

public class TransactionModelTest extends AbstractModelTest<TransactionModel>{

	public TransactionModelTest() {
		super(TransactionModel.class);
	}

	@Override
	public TransactionModel createModelWithMockedStorage() {
		return new TransactionModel() {
			@Override
			protected Storage createStorage() {
				return mock(Storage.class);
			}
		};
	}
	
	@Test
	public void testGetAll() throws Exception {
		model.getAll();
		verify(mockedStorage).getAllTransactions();
	}
	
	@Test
	public void testDelete() throws Exception {
		Transaction toDelete = new Transaction(new Stock("HEJ"), LocalDate.now(), 10, Money.parse("SEK 20"), Money.parse("SEK 2"));
		model.delete(toDelete);
		
		verify(mockedStorage).deleteTransaction(toDelete);
	}
	
	@Test
	public void testSave() throws Exception {
		Transaction toSave = new Transaction(new Stock("HEJ"), LocalDate.now(), 10, Money.parse("SEK 20"), Money.parse("SEK 2"));
		model.save(toSave);
		
		verify(mockedStorage).saveTransaction(toSave);
	}
	
	@Test
	public void testGetById() throws Exception {
		UUID id = UUID.randomUUID();
		model.getById(id);
		verify(mockedStorage).getTransactionById(id);
	}
	



}
