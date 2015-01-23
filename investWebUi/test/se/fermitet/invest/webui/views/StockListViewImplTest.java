package se.fermitet.invest.webui.views;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.vaadin.widgets.POJOTable;

public class StockListViewImplTest {
	@Test
	public void testCallingDisplayStocksDisplaysStocks() throws Exception {
		StockListViewImpl view = new StockListViewImpl();
		
		POJOTable<Stock> table = view.stockTable;
		
		List<Stock> testStocks = getTestStocks();
		view.displayStocks(testStocks);

		List<Stock> displayedData = table.getDisplayedData();
		assertArrayEquals(testStocks.toArray(), displayedData.toArray());
	}

	private List<Stock> getTestStocks() {
		List<Stock> ret = new ArrayList<Stock>();
		
		ret.add(new Stock("TST1").setName("Name 1"));
		ret.add(new Stock("TST2").setName("Name 2"));
		ret.add(new Stock("TST3"));
		
		return ret;
	}
	
}