package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.testData.StockDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.ui.Button;
 
public class StockListViewImplTest {
	private StockListViewImpl view;
	private List<Stock> testStocksUnsorted;
	private List<Stock> testStocksSorted;
	private StockListPresenter mockedPresenter;

	@Before
	public void setUp() {
		view = new TestStockListViewImpl();
		mockedPresenter = view.presenter;

		initTestStocks();
		
		view.displayStocks(testStocksUnsorted);
	}
	
	private void initTestStocks() {
		testStocksUnsorted = new ArrayList<Stock>(new StockDataProvider().getTestStocks());

		testStocksSorted = new ArrayList<Stock>(testStocksUnsorted);
		testStocksSorted.sort((Stock o1, Stock o2) -> {
			String o1Symbol = o1.getSymbol();
			String o2Symbol = o2.getSymbol();
			return o1Symbol.compareTo(o2Symbol);
		});
	}

	@Test
	public void testEnterCallsFillStocks() throws Exception {
		view.enter(null);
	
		verify(mockedPresenter).fillStockListWithAllStocks();
	}
	
	@Test
	public void testCallingDisplayStocksDisplaysStocks() throws Exception {
		List<Stock> displayedData = view.stockTableAdapter.getData();
		assertArrayEquals(testStocksSorted.toArray(), displayedData.toArray());
	}
	
	@Test
	public void testSelectionAffectstButtonsEnabledStatus() throws Exception {
		Button deleteButton = view.deleteButton;
		Button editButton = view.editButton;
		
		assertFalse("Before - delete", deleteButton.isEnabled());
		assertFalse("Before - edit", editButton.isEnabled());
		
		view.stockTable.select(1);
		assertTrue("After select - delete", deleteButton.isEnabled());
		assertTrue("After select - edit", editButton.isEnabled());

		view.stockTable.select(null);
		assertFalse("After unselect - delete", deleteButton.isEnabled());
		assertFalse("After unselect - edit", editButton.isEnabled());
	}
	
	@Test
	public void testNewButtonProperties() throws Exception {
		Button newButton = view.newButton;
		
		assertNotNull("not null", newButton);
		assertTrue("Enabled", newButton.isEnabled());
		assertTrue("Visible", newButton.isVisible());
		
		newButton.click();
		
		verify(mockedPresenter).onNewButtonClick();
	}
	
	@Test
	public void testEditButton() throws Exception {
		Button editButton = view.editButton;
		
		assertNotNull("not null", editButton);
		
		view.stockTable.select(0);
		Stock selectedStock = testStocksSorted.get(0);
		
		editButton.click();
		
		verify(mockedPresenter).onEditButtonClick(selectedStock);
	}
	
	@Test
	public void testDeleteButton() throws Exception {
		int idx = 2;
		Stock toDelete = testStocksSorted.get(idx);
		
		view.stockTable.select(idx);
		view.deleteButton.click();
		
		verify(mockedPresenter).onDeleteButtonClick(toDelete);
	}
	
	@Test
	public void testEditSingleStock_nullValue() throws Exception {
		view.navigateToSingleStockView(null);
		
		verify(view.getNavigator()).navigateTo(InvestWebUI.SINGLESTOCKVIEW);
	}

	@Test
	public void testEditSingleStock_notNullValue() throws Exception {
		Stock testStock = new Stock("Test", "TST");
		
		view.navigateToSingleStockView(testStock);
		verify(view.getNavigator()).navigateTo(InvestWebUI.SINGLESTOCKVIEW, new URIParameter(testStock.getId().toString()));
	}
}

@SuppressWarnings("serial")
class TestStockListViewImpl extends StockListViewImpl {
	@Override
	protected StockListPresenter createPresenter() {
		return mock(StockListPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}
