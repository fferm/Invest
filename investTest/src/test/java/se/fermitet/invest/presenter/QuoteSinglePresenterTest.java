package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.QuoteModel;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.QuoteSingleView;

public class QuoteSinglePresenterTest extends POJOSinglePresenterTest<QuoteSinglePresenter, Quote, QuoteModel, QuoteSingleView>{

	private StockModel mockedStocksModel;
	
	public QuoteSinglePresenterTest() {
		super(QuoteSingleView.class, Quote.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.mockedStocksModel = presenter.stocksModel;
	}

	@Override
	protected void assessDefaultDO(Quote obj) {
		assertEquals("date", LocalDate.now(), obj.getDate());
		System.out.println("!!!! stock: " + obj.getStock());
		fail("continue");
	}

	@Override
	protected QuoteSinglePresenter createPresenter(QuoteSingleView view) {
		return new TestSingleQuotePresenter((QuoteSingleView) view);
	}
	
	@Test
	public void testProvideAllStocks() throws Exception {
		List<Stock> list = new ArrayList<Stock>();
		when(mockedStocksModel.getAll()).thenReturn(list);
	
		presenter.provideAllStocks();
		
		verify(mockedStocksModel).getAll();
		verify(mockedView).showStocksInSelection(list);
	}
	
}

class TestSingleQuotePresenter extends QuoteSinglePresenter {
	public TestSingleQuotePresenter(QuoteSingleView view) {
		super(view);
	}

	@Override
	protected QuoteModel createModel() {
		return mock(QuoteModel.class);
	}
	
	@Override
	protected StockModel createStocksModel() {
		return mock(StockModel.class);
	}
}

