package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleTransactionPresenter;
import se.fermitet.invest.testData.StockDataProvider;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;

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
		assertNotNull("stock", view.stockComboAdapter);
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
	public void testCallingShowStocksInSelectionDisplaysSymbolsSorted() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestStocks();
		
		view.showStocksInSelection(testStocks);

		ComboBox stockCombo = view.stockComboAdapter.getUI();
		assertEquals("size", testStocks.size(), stockCombo.size());
		
		List<String> desiredCaptions = new ArrayList<String>();
		for (Stock testStock : testStocks) {
			desiredCaptions.add(testStock.getSymbol());
		}
		Collections.sort(desiredCaptions);
		
		int i = 0;
		for(Object itemId : stockCombo.getItemIds()) {
			String caption = stockCombo.getItemCaption(itemId);
			assertEquals("Idx: " + i, desiredCaptions.get(i), caption);
			
			i++;
		}
	}




}

@SuppressWarnings("serial")
class TestSingleTransactionViewImpl extends SingleTransactionViewImpl {
	@Override
	protected SingleTransactionPresenter createPresenter() {
		return mock(SingleTransactionPresenter.class);
	}
}