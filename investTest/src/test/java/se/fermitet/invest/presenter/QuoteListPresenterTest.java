package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;
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

