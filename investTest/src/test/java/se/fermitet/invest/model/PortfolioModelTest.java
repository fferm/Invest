package se.fermitet.invest.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.storage.Storage;

public class PortfolioModelTest extends AbstractModelTest<PortfolioModel>{

	public PortfolioModelTest() {
		super(PortfolioModel.class);
	}

	@Override
	public PortfolioModel createModelWithMockedStorage() {
		return new TestPortfolioModel();
	}

	@Test
	public void testGetAll() throws Exception {
		model.getAll();
		verify(mockedStorage).getAllPortfolios();
	}

	@Test
	public void testGetById() throws Exception {
		UUID id = UUID.randomUUID();
		model.getById(id);
		verify(mockedStorage).getPortfolioById(id);
	}
	
	@Test
	public void testDelete() throws Exception {
		Portfolio toDelete = new Portfolio();
		model.delete(toDelete);
		
		verify(mockedStorage).deletePortfolio(toDelete);
	}
	
	@Test
	public void testSave() throws Exception {
		Portfolio toSave = new Portfolio();
		model.save(toSave);
		
		verify(mockedStorage).savePortfolio(toSave);
	}
	
	@Test
	public void testDeleteWithAssociatedTransactions() throws Exception {
		Portfolio portfolio = new Portfolio("TEST");
		Transaction trans = new Transaction();
		List<Transaction> transactions = new ArrayList<Transaction>();

		when(mockedStorage.getTransactionsForPortfolio(portfolio)).thenReturn(transactions); //Empty at this point

		model.delete(portfolio);  // should be OK

		transactions.add(trans);
		when(mockedStorage.getTransactionsForPortfolio(portfolio)).thenReturn(transactions);  // Now contains something

		try {
			model.delete(portfolio);
			fail("should give exception");
		} catch (ModelException e) {
			assertEquals(new ModelException(ModelExceptionType.CANNOT_DELETE_PORTFOLIO_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, portfolio), e);
		}
	}


}

class TestPortfolioModel extends PortfolioModel {
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
}

