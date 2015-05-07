package se.fermitet.invest.webui.views;

import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.presenter.QuoteListPresenter;
import se.fermitet.invest.testData.TestDataProvider;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class QuoteListViewImplTest extends ListViewImplTest<QuoteListViewImpl, QuoteListPresenter, Quote> {

	@Override
	protected QuoteListViewImpl createViewImpl() {
		return new TestQuoteListViewImpl();
	}

	@Override
	protected void initTestData() {
		testDataUnsorted = new TestDataProvider().getQuotes();
		
		for (Quote quote : testDataUnsorted) {
			System.out.println("!!!! " + quote);
		}
		// TODO: !!!! HŠr
		throw new UnsupportedOperationException("unimplemented");
	}

	@Override
	protected String getSingleViewName() {
		throw new UnsupportedOperationException("unimplemented");
	}

}

@SuppressWarnings("serial")
class TestQuoteListViewImpl extends QuoteListViewImpl {
	TestQuoteListViewImpl() {
		super();
	}
	
	@Override
	protected QuoteListPresenter createPresenter() {
		return mock(QuoteListPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}

