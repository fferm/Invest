package se.fermitet.invest.webui.views;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;

import com.vaadin.data.Item;
import com.vaadin.ui.Table;

public class StockListViewImplTest {
	@Test
	public void testCallingDisplayStocksDisplaysStocks() throws Exception {
		StockListViewImpl view = new StockListViewImpl();
		
		Table table = view.stockTable;
		
		assertEquals("Empty before", 0, table.size());
		
		List<Stock> testStocks = getTestStocks();
		
		view.displayStocks(testStocks);
		
		assertEquals("Size after", testStocks.size(), table.size());

		Collection<?> itemIds = table.getItemIds();
		for (Object itemId : itemIds) {
			Item item = table.getItem(itemId);

			Stock compareStock = new Stock(item.getItemProperty(StockListViewImpl.CODE_PROP_ID).getValue().toString());
			
			
			Object nameObj = item.getItemProperty(StockListViewImpl.NAME_PROP_ID).getValue();
			if (nameObj != null) compareStock.setName(nameObj.toString());
			
			assertTrue("the table should contain a Stock like " + compareStock, testStocks.contains(compareStock));
		}
		
		// Call again and check size
		List<Stock> testStocksSecond = getTestStocksSecond();
		view.displayStocks(testStocksSecond);
		assertEquals("Size after second", testStocksSecond.size(), table.size());

		
	}

	private List<Stock> getTestStocks() {
		List<Stock> ret = new ArrayList<Stock>();
		
		ret.add(new Stock("TST1").setName("Name 1"));
		ret.add(new Stock("TST2").setName("Name 2"));
		ret.add(new Stock("TST3"));
		
		return ret;
	}
	
	private List<Stock> getTestStocksSecond() {
		List<Stock> ret = new ArrayList<Stock>();
		
		ret.add(new Stock("Second 1").setName("Second 1"));
		ret.add(new Stock("Second 2"));
		
		return ret;
	}

}
