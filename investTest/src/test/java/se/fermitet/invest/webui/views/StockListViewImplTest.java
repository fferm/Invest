package se.fermitet.invest.webui.views;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.viewinterface.StockListView.StockListViewListener;

import com.vaadin.ui.Button;

public class StockListViewImplTest {
	private StockListViewImpl view;
	private ArrayList<Stock> testStocks;

	@Before
	public void setUp() {
		view = new StockListViewImpl();
		initTestStocks();
		view.displayStocks(testStocks);
	}
	
	private void initTestStocks() {
		testStocks = new ArrayList<Stock>();
		
		testStocks.add(new Stock("TST1").setName("Name 1"));
		testStocks.add(new Stock("TST2").setName("Name 2"));
		testStocks.add(new Stock("TST3"));
	}

	@Test
	public void testCallingDisplayStocksDisplaysStocks() throws Exception {
		List<Stock> displayedData = view.stockTableAdapter.getData();
		assertArrayEquals(testStocks.toArray(), displayedData.toArray());
	}
	
	@Test
	public void testSelectionAffectsDeleteButtonEnabledStatus() throws Exception {
		Button deleteButton = view.deleteButton;
		
		assertFalse("Before", deleteButton.isEnabled());
		
		view.stockTable.select(1);
		assertTrue("After select", deleteButton.isEnabled());

		view.stockTable.select(null);
		assertFalse("After unselect", deleteButton.isEnabled());
	}
	
	@Test
	public void testClickingDeleteButtonFiresDeleteEventWithRightData() throws Exception {
		int idx = 1;
		Stock toDelete = testStocks.get(idx);
		
		StockListViewListener listener = mock(StockListViewListener.class);
		view.addListener(listener);
		
		view.stockTable.select(idx);
		view.deleteButton.click();
		
		verify(listener).onDeleteButtonClick(toDelete);
		
		view.removeListener(listener);
	}
	
	@Test
	public void testNewButtonProperties() throws Exception {
		Button newButton = view.newButton;
		
		assertNotNull("not null", newButton);
		assertTrue("Enabled", newButton.isEnabled());
		assertTrue("Visible", newButton.isVisible());
		
		StockListViewListener listener = mock(StockListViewListener.class);
		view.addListener(listener);
		
		newButton.click();
		
		verify(listener).onNewButtonClick();
		
		view.removeListener(listener);
	}
	
	@Test
	public void testShowStockFormMakesItVisible() throws Exception {
		assertFalse("Before", view.stockForm.isVisible());
		
		view.showStockForm(new Stock());
		
		assertTrue("After", view.stockForm.isVisible());
		
	}

	
}
