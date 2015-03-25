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

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.TransactionSinglePresenter;
import se.fermitet.invest.testData.PortfolioDataProvider;
import se.fermitet.invest.testData.StockDataProvider;
import se.fermitet.vaadin.converters.MoneyConverter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;

public class TransactionSingleViewImplTest extends POJOSingleViewImplTest<TransactionSingleViewImpl, TransactionSinglePresenter, Transaction> {
	private List<Stock> testStocks;
	private List<Portfolio> testPortfolios;

	public TransactionSingleViewImplTest() {
		super(Transaction.class);
	}

	@Before
	@Override
	public void setUp() {
		super.setUp();

		testStocks = new StockDataProvider().getTestData();
		testPortfolios = new PortfolioDataProvider().getTestData();
		view.showStocksInSelection(testStocks);
		view.showPortfoliosInSelection(testPortfolios);
	}

	@Override
	protected TransactionSingleViewImpl createViewImpl() {
		return new TestTransactionSingleViewImpl();
	}

	@Override
	protected Transaction getTestPojo() {
		Stock stock = testStocks.get(2);
		LocalDate date = LocalDate.now().minusDays(10);
		int number = 10;
		Money price = Money.parse("SEK 200");
		Money fee = Money.parse("SEK 10");
		Portfolio port = testPortfolios.get(0);
		
		Transaction trans = new Transaction(stock, date, number, price, fee, port);

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
		
		if (pojo.getPortfolio() == null || pojo.getPortfolio().getName() == null) 
			assertNull("Portfolio", view.stockComboAdapter.getUI().getValue());
		else
			assertEquals("Symbol", pojo.getPortfolio().getName(), view.portfolioComboAdapter.getUI().getItemCaption(view.portfolioComboAdapter.getUI().getValue()));

		assertEquals("Number", "" + pojo.getNumber(), view.numberFieldAdapter.getUI().getValue());
		assertEquals("Price", conv.convertToPresentation(pojo.getPrice(),  null, null), view.priceFieldAdapter.getUI().getValue());
		assertEquals("Fee", conv.convertToPresentation(pojo.getFee(),  null, null), view.feeFieldAdapter.getUI().getValue());
	}
	
	@Override
	protected void updateUIFromPOJO(Transaction updated) {
		view.portfolioComboAdapter.select(updated.getPortfolio());
		view.stockComboAdapter.select(updated.getStock());
		view.dateAdapter.setValue(updated.getDate());
		view.numberFieldAdapter.setValue(updated.getNumber());
		view.priceFieldAdapter.setValue(updated.getPrice());
		view.feeFieldAdapter.setValue(updated.getFee());
	}
	
	@Override
	protected void makeUIDataInvalid() {
		view.dateAdapter.setValue(null);
	}

	@Override
	protected void checkFieldValidity(boolean shouldBeValid) {
		assertTrue("date field validity should be " + shouldBeValid, view.dateAdapter.getUI().isValid() == shouldBeValid);
	}


	@Test
	public void testHasComponents() throws Exception {
		assertNotNull("portfolio", view.portfolioComboAdapter);
		assertNotNull("stock", view.stockComboAdapter);
		assertNotNull("date", view.dateAdapter);
		assertNotNull("price", view.priceFieldAdapter);
		assertNotNull("fee", view.feeFieldAdapter);
		assertNotNull("number", view.numberFieldAdapter);
	}

	@Test
	public void testEnterCallsPresenterToFillStocks() throws Exception {
		view.enter(mock(ViewChangeEvent.class));

		verify(mockedPresenter).provideAllStocks();
	}

	@Test
	public void testCallingShowStocksInSelectionDisplaysSymbolsSorted() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestData();

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
	public void testEnterCallsPresenterToFillPortfolios() throws Exception {
		view.enter(mock(ViewChangeEvent.class));

		verify(mockedPresenter).provideAllPortfolios();
	}

	@Test
	public void testCallingShowPortfoliosInSelectionDisplaysNamesSorted() throws Exception {
		List<Portfolio> testPortfolios = new PortfolioDataProvider().getTestData();

		view.showPortfoliosInSelection(testPortfolios);

		ComboBox portfolioCombo = view.portfolioComboAdapter.getUI();
		assertEquals("size", testPortfolios.size(), portfolioCombo.size());

		List<String> desiredCaptions = new ArrayList<String>();
		for (Portfolio testPortfolio : testPortfolios) {
			desiredCaptions.add(testPortfolio.getName());
		}
		Collections.sort(desiredCaptions);

		int i = 0;
		for(Object itemId : portfolioCombo.getItemIds()) {
			String caption = portfolioCombo.getItemCaption(itemId);
			assertEquals("Idx: " + i, desiredCaptions.get(i), caption);

			i++;
		}
	}

	@Test
	public void testErrorShouldNotAppearWhenClearingOutNumberField() throws Exception {
		List<Stock> testStocks = new StockDataProvider().getTestData();
		view.showStocksInSelection(testStocks);

		Transaction initialTransaction = new Transaction(testStocks.get(1), LocalDate.now(), 10, Money.parse("SEK 200"), Money.parse("SEK 2"), testPortfolios.get(3));

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initialTransaction);

		view.enter(mock(ViewChangeEvent.class));

		view.numberFieldAdapter.getUI().setValue("");
	}





}

@SuppressWarnings("serial")
class TestTransactionSingleViewImpl extends TransactionSingleViewImpl {
	@Override
	protected TransactionSinglePresenter createPresenter() {
		return mock(TransactionSinglePresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}

}