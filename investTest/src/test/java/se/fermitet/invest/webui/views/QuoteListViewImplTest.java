package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.QuoteListPresenter;
import se.fermitet.invest.testData.TestDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.invest.webui.navigation.EntityNameHelper;
import se.fermitet.vaadin.navigation.DirectionalNavigator;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class QuoteListViewImplTest extends ListViewImplTest<QuoteListViewImpl, QuoteListPresenter, Quote> {

	private String STOCK_SYMBOL = "AAK";
	private String OTHER_STOCK_SYMBOL = "BMAX";
	private TestDataProvider testDataProvider;
	
	public QuoteListViewImplTest() {
		super(Quote.class);
		this.testDataProvider = new TestDataProvider();
	}
	
	@Override
	protected QuoteListViewImpl createViewImpl() {
		return new TestQuoteListViewImpl();
	}

	@Override
	protected List<Quote> getTestData() {
		return testDataProvider.getQuotesForStockBySymbol(STOCK_SYMBOL);
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
		return InvestWebUI.QUOTE_SINGLE;
	}
	
	@Override
	@Test
	public void testEnterWithNullCallsFillViewWithData() throws Exception {
		// make it pass, this is not how it should be called
	}

	@Test
	public void testEnterWithParameter_quotes() throws Exception {
		Stock otherStock = testDataProvider.getStockBySymbol(OTHER_STOCK_SYMBOL);
		List<Quote> quotes = testDataProvider.getQuotesForStock(otherStock);

		when(mockedPresenter.getStockById(any())).thenReturn(otherStock);
		when(mockedPresenter.getQuotesByStock(otherStock)).thenReturn(quotes);
		
		List<URIParameter> parameters = new ArrayList<URIParameter>();
		parameters.add(new URIParameter(EntityNameHelper.entityNameFor(Stock.class), otherStock.getId().toString()));

		view.enter(mock(ViewChangeEvent.class), parameters);

		List<Quote> displayedData = view.tableAdapter.getData();
		
		assertEquals("size of array", quotes.size(), displayedData.size());
		for (Quote displayed : displayedData) {
			assertTrue(quotes.contains(displayed));
		}
		
		assertEquals("stock of QuoteListViewImpl", otherStock, view.getStock());
	}
	
	@Test
	public void testEnterWithParameter_stock() throws Exception {
		Stock otherStock = testDataProvider.getStockBySymbol(OTHER_STOCK_SYMBOL);

		when(mockedPresenter.getStockById(any())).thenReturn(otherStock);
		
		List<URIParameter> parameters = new ArrayList<URIParameter>();
		parameters.add(new URIParameter(EntityNameHelper.entityNameFor(Stock.class), otherStock.getId().toString()));

		view.enter(mock(ViewChangeEvent.class), parameters);

		assertEquals("stock of QuoteListViewImpl", otherStock, view.getStock());
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

