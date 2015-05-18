package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.model.QuoteModel;
import se.fermitet.invest.viewinterface.ListView;

public class QuoteListPresenterTest extends ListPresenterTest<QuoteListPresenter, Quote, QuoteModel, ListView<Quote>> {

	public QuoteListPresenterTest() {
		super(ListView.class, Quote.class);
	}

	@Override
	protected QuoteListPresenter createPresenter(ListView<Quote> view) {
		return new TestQuoteListPresenter(view);
	}
	
	@Test
	public void testGetQuotesByStockIdCallsModel() throws Exception {
		String stockId = UUID.randomUUID().toString();
		
		this.presenter.getQuotesByStockId(stockId);
		
		verify(mockedModel).getQuotesByStockId(UUID.fromString(stockId));
	}

}

class TestQuoteListPresenter extends QuoteListPresenter {
	public TestQuoteListPresenter(ListView<Quote> view) {
		super(view);
	}
	
	@Override
	protected QuoteModel createModel() {
		return mock(QuoteModel.class);
	}
}

