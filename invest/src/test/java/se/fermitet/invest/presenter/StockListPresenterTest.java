package se.fermitet.invest.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenterTest {
	@Test
	public void testConstructorCreatesModelFillsViewAndSetsPresenterAsViewListener() throws Exception {
		StockListView mockedView = mock(StockListView.class);
		StocksModel mockedModel = mock(StocksModel.class);
		
		List<Stock> list = new ArrayList<Stock>();
		
		when(mockedModel.getAllStocks()).thenReturn(list);
		
		StockListPresenter presenter = new StockListPresenter(mockedView, mockedModel);
		
		verify(mockedModel).getAllStocks();
		verify(mockedView).displayStocks(list);
		verify(mockedView).addListener(presenter);
	}
}
