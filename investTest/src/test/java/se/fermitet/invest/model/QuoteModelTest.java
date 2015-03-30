package se.fermitet.invest.model;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.storage.Storage;

public class QuoteModelTest extends AbstractModelTest<QuoteModel>{

	public QuoteModelTest() {
		super(QuoteModel.class);
	}

	@Override
	public QuoteModel createModelWithMockedStorage() {
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






}

class TestQuoteModel extends QuoteModel {
	@Override
	protected Storage createStorage() {
		return mock(Storage.class);
	}
}

