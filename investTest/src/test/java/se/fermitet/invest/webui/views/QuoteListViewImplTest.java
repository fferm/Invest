package se.fermitet.invest.webui.views;

import static org.mockito.Mockito.*;

import java.util.Comparator;
import java.util.List;

import org.joda.time.LocalDate;

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
	protected List<Quote> getTestData() {
		return new TestDataProvider().getQuotes();
	}

	@Override
	protected Comparator<? super Quote> getComparator() {
		return (Quote o1, Quote o2) -> {
			String o1Symbol = o1.getStock().getSymbol();
			String o2Symbol = o2.getStock().getSymbol();
			int compareSymbols = o1Symbol.compareTo(o2Symbol);
			if (compareSymbols != 0) return compareSymbols;
			
			LocalDate o1Date = o1.getDate();
			LocalDate o2Date = o2.getDate();
			return o1Date.compareTo(o2Date);
		};
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

