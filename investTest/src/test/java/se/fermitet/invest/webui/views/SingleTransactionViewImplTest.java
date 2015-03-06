package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
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
		assertNotNull("date", view.dateAdapter);
		assertNotNull("price", view.priceFieldAdapter);
		assertNotNull("fee", view.feeFieldAdapter);
		assertNotNull("number", view.numberFieldAdapter);
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

	@Test
	public void testEnterShowsSomeData_notNull() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestStocks();
		view.showStocksInSelection(testStocks);

		Stock stock = testStocks.get(0);
		LocalDate date = LocalDate.now();
		int number = 10;
		Money price = Money.parse("SEK 200");
		Money fee = Money.parse("SEK 10");
		Transaction trans = new Transaction(stock, date, number, price, fee);

		when(mockedPresenter.getTransactionBasedOnIdString(anyString())).thenReturn(trans);
		
		view.enter(mock(ViewChangeEvent.class));

		assertEquals("Date", date.toDate(), view.dateAdapter.getUI().getValue());
		assertEquals("Symbol", stock.getSymbol(), view.stockComboAdapter.getUI().getItemCaption(view.stockComboAdapter.getUI().getValue()));
		assertEquals("Number", "" + number, view.numberFieldAdapter.getUI().getValue());
		assertEquals("Price", "200,00", view.priceFieldAdapter.getUI().getValue());
		assertEquals("Fee", "10,00", view.feeFieldAdapter.getUI().getValue());
	}
	
	@Test
	public void testEnterShowsSomeData_null() throws Exception {
		Transaction defaultTrans = new Transaction();
		
		when(mockedPresenter.getTransactionBasedOnIdString(anyString())).thenReturn(defaultTrans);
		
		view.enter(mock(ViewChangeEvent.class));
		
		assertEquals("Date", LocalDate.now().toDate(), view.dateAdapter.getUI().getValue());
		assertNull("Stock", view.stockComboAdapter.getUI().getValue());
		assertEquals("Number", "" + 0, view.numberFieldAdapter.getUI().getValue());
		assertNull("Price", view.priceFieldAdapter.getUI().getValue());
		assertNull("Fee", view.feeFieldAdapter.getUI().getValue());
	}
	
	@Test
	public void testCancelButtonCallsPresenter() throws Exception {
		fail("unimplemented");
//		String nameValue = "my name";
//		String symbolValue = "my symbol";
//		
//		Stock testStock = new Stock(nameValue, symbolValue);
//		
//		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(testStock);
//		
//		view.enter(mock(ViewChangeEvent.class));
//		
//		view.cancelButton.click();
//		
//		verify(mockedPresenter).onCancelButtonClick();
	}
	
	@Test
	public void testNavigateBack() throws Exception {
		fail("unimplemented");
//		DirectionalNavigator mockedNavigator = view.getNavigator();
//		
//		view.navigateBack();
//		
//		verify(mockedNavigator).navigateBack();
	}
	
	@Test
	public void testOKButtonCallsPresenterWithUpdatedStock() throws Exception {
		fail("unimplemented");
//		Stock initialStock = new Stock("Name", "Symbol");
//		
//		String newName = "new name";
//		String newSymbol = "new symbol";
//		
//		Stock updatedStock = new Stock(newName, newSymbol);
//		
//		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(initialStock);
//		
//		view.enter(mock(ViewChangeEvent.class));
//		
//		view.nameField.setValue(newName);
//		view.symbolField.setValue(newSymbol);
//		
//		reset(mockedPresenter);
//		
//		view.okButton.click();
//		
//		verify(mockedPresenter).onOkButtonClick(eq(updatedStock));
	}
	
	@Test
	public void testEmptySymbolShouldGiveValidationFault() throws Exception {
		fail("unimplemented");
//		Stock initialStock = new Stock("Name", "Symbol");
//		
//		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(initialStock);
//		
//		view.enter(mock(ViewChangeEvent.class));
//
//		
//		assertTrue("ok button enabled before", view.okButton.isEnabled());
//		assertTrue("symbol field valid before", view.symbolField.isValid());
//		assertTrue("form valid before", view.isValid());
//		
//		view.symbolField.setValue(null);
//		
//		assertFalse("ok button disabled after", view.okButton.isEnabled());
//		assertFalse("symbol field not valid after", view.symbolField.isValid());
//		assertFalse("form not valid after", view.isValid());
	}




}

@SuppressWarnings("serial")
class TestSingleTransactionViewImpl extends SingleTransactionViewImpl {
	@Override
	protected SingleTransactionPresenter createPresenter() {
		return mock(SingleTransactionPresenter.class);
	}
}