package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleTransactionPresenter;
import se.fermitet.invest.testData.StockDataProvider;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class SingleTransactionViewImplTest {
	private SingleTransactionViewImpl view;
	private SingleTransactionPresenter mockedPresenter;

	@Before
	public void setUp() {
		view = new TestSingleTransactionViewImpl();
		mockedPresenter = view.presenter;
	}
	
	@Test
	public void testHasComponents() throws Exception {
		assertNotNull("stock", view.stockCombo);
		assertNotNull("date", view.datePopup);
		assertNotNull("price", view.priceField);
		assertNotNull("fee", view.feeField);
		assertNotNull("number", view.numberField);
	}
	
	@Test
	public void testEnterCallsFillStockCombo() throws Exception {
		view.enter(mock(ViewChangeEvent.class));
		
		verify(mockedPresenter).provideAllStocks();
	}
	
	@Test
	public void testCallingShowStocksInSelectionDisplaysStocks() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestStocks();
		
		view.showStocksInSelection(testStocks);

		fail("continue this test");
	}




}

@SuppressWarnings("serial")
class TestSingleTransactionViewImpl extends SingleTransactionViewImpl {
	@Override
	protected SingleTransactionPresenter createPresenter() {
		return mock(SingleTransactionPresenter.class);
	}
}