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
import se.fermitet.vaadin.converters.MoneyConverter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;

public class SingleTransactionViewImplTest extends SinglePOJOViewImplTest<SingleTransactionViewImpl, SingleTransactionPresenter, Transaction> {
	private List<Stock> testStocks;

	public SingleTransactionViewImplTest() {
		super(Transaction.class);
	}

	@Before
	@Override
	public void setUp() {
		super.setUp();

		testStocks = new StockDataProvider().getTestStocks();
		view.showStocksInSelection(testStocks);
	}

	@Override
	protected SingleTransactionViewImpl createViewImpl() {
		return new TestSingleTransactionViewImpl();
	}

	@Override
	protected Transaction getTestPojo() {
		Stock stock = testStocks.get(0);
		LocalDate date = LocalDate.now();
		int number = 10;
		Money price = Money.parse("SEK 200");
		Money fee = Money.parse("SEK 10");
		Transaction trans = new Transaction(stock, date, number, price, fee);

		return trans;
	}

	@Override
	protected void checkUIAgainstPojo(Transaction pojo) {
		MoneyConverter conv = new MoneyConverter();

		assertEquals("Date", pojo.getDate().toDate(), view.dateAdapter.getUI().getValue());

		if (pojo.getStock() == null || pojo.getStock().getSymbol() == null) 
			assertNull("Stock", view.stockComboAdapter.getUI().getValue());
		else
			assertEquals("Symbol", pojo.getStock().getSymbol(), view.stockComboAdapter.getUI().getItemCaption(view.stockComboAdapter.getUI().getValue()));
		
		assertEquals("Number", "" + pojo.getNumber(), view.numberFieldAdapter.getUI().getValue());
		assertEquals("Price", conv.convertToPresentation(pojo.getPrice(),  null, null), view.priceFieldAdapter.getUI().getValue());
		assertEquals("Fee", conv.convertToPresentation(pojo.getFee(),  null, null), view.feeFieldAdapter.getUI().getValue());
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
	public void testCancelButtonCallsPresenter() throws Exception {
		Transaction testTransaction = new Transaction();

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(testTransaction);

		view.enter(mock(ViewChangeEvent.class));

		view.cancelButton.click();

		verify(mockedPresenter).onCancelButtonClick();
	}

	@Test
	public void testNavigateBack() throws Exception {
		DirectionalNavigator mockedNavigator = view.getNavigator();

		view.navigateBack();

		verify(mockedNavigator).navigateBack();
	}

	@Test
	public void testOKButtonCallsPresenterWithUpdatedData() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestStocks();
		view.showStocksInSelection(testStocks);

		Transaction initialTransaction = new Transaction();
		Stock stock = testStocks.get(2);

		int newNumber = 10;
		LocalDate newDate = LocalDate.now().minusDays(2);
		Money newPrice = Money.parse("SEK 200");
		Money newFee = Money.parse("SEK 20");

		Transaction updatedTransaction = new Transaction(stock, newDate, newNumber, newPrice, newFee);

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initialTransaction);

		view.enter(mock(ViewChangeEvent.class));

		view.stockComboAdapter.select(stock);
		view.dateAdapter.setValue(newDate);
		view.numberFieldAdapter.setValue(newNumber);
		view.priceFieldAdapter.setValue(newPrice);
		view.feeFieldAdapter.setValue(newFee);

		reset(mockedPresenter);

		view.okButton.click();

		verify(mockedPresenter).onOkButtonClick(eq(updatedTransaction));
	}

	@Test
	public void testInvalidTransactionHandling() throws Exception {
		Transaction initialTransaction = new Transaction(new Stock("Name", "Symbol"), LocalDate.now(), 10, Money.parse("SEK 200"), Money.parse("SEK 2"));;

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initialTransaction);

		view.enter(mock(ViewChangeEvent.class));

		assertTrue("ok button enabled before", view.okButton.isEnabled());
		assertTrue("date field valid before", view.dateAdapter.getUI().isValid());
		assertTrue("form valid before", view.isValid());

		view.dateAdapter.setValue(null);

		assertFalse("ok button disabled after", view.okButton.isEnabled());
		assertFalse("date field not valid after", view.dateAdapter.getUI().isValid());
		assertFalse("form not valid after", view.isValid());
	}

	@Test
	public void testErrorShouldNotAppearWhenClearingOutNumberField() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestStocks();
		view.showStocksInSelection(testStocks);

		Transaction initialTransaction = new Transaction(testStocks.get(1), LocalDate.now(), 10, Money.parse("SEK 200"), Money.parse("SEK 2"));

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initialTransaction);

		view.enter(mock(ViewChangeEvent.class));

		view.numberFieldAdapter.getUI().setValue("");
	}



}

@SuppressWarnings("serial")
class TestSingleTransactionViewImpl extends SingleTransactionViewImpl {
	@Override
	protected SingleTransactionPresenter createPresenter() {
		return mock(SingleTransactionPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}

}