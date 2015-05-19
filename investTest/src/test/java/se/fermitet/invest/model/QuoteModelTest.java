package se.fermitet.invest.model;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.storage.Storage;

public class QuoteModelTest extends AbstractModelTest<TestQuoteModel>{

	public QuoteModelTest() {
		super(QuoteModel.class);
	}

	@Override
	public TestQuoteModel createModelWithMockedStorage() {
		return new TestQuoteModel();
	}

	@Test
	public void testGetAll() throws Exception {
		model.getAll();
		verify(mockedStorage).getAllQuotes();
	}
	
	@Test
	public void testGetById() throws Exception {
		UUID id = UUID.randomUUID();
		model.getById(id);
		verify(mockedStorage).getQuoteById(id);
	}
	
	@Test
	public void testDelete() throws Exception {
		Quote toDelete = new Quote();
		model.delete(toDelete);

		verify(mockedStorage).deleteQuote(toDelete);
	}

	@Test
	public void testSave() throws Exception {
		Quote toSave = new Quote();
		model.save(toSave);

		verify(mockedStorage).saveQuote(toSave);
	}
	
	@Test
	public void testGetQuotesByStock() throws Exception {
		Stock theStock = new Stock("TEST", "Test");
		
		model.getQuotesByStock(theStock);
		
		verify(mockedStorage).getQuotesByStock(theStock);
	}






}

class TestQuoteModel extends QuoteModel {
	StockModel mockedStockModel = mock(StockModel.class);
	
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
	
	@Override
	protected StockModel getStockModel() {
		return mockedStockModel;
	}
}

