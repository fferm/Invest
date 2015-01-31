package se.fermitet.invest.presenter;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenterTest {
	private StockListView mockedView;
	private StockListPresenter presenter;
	private StocksModel mockedModel;

	@Before
	public void setUp() throws Exception {
		mockedView = mock(StockListView.class);
		presenter = new TestStockListPresenter(mockedView);
		mockedModel = presenter.model;
		
		reset(mockedView);
		reset(mockedModel);
	}

	@Test
	public void testConstructorFillsViewAndSetsPresenterAsViewListener() throws Exception {
		List<Stock> list = new ArrayList<Stock>();
		when(mockedModel.getAllStocks()).thenReturn(list);
		
		presenter = new TestStockListPresenter(mockedView);
		mockedModel = presenter.model;
		
		verify(mockedModel).getAllStocks();
		verify(mockedView).displayStocks(list);
		verify(mockedView).addListener(presenter);
	}
	
	@Test
	public void testDeleteFromViewCallsModelDelete() throws Exception {
		Stock toDelete = new Stock("TEST");
		List<Stock> list = new ArrayList<Stock>();
		when(mockedModel.getAllStocks()).thenReturn(list);
		
		presenter.onDeleteButtonClick(toDelete);
		
		verify(mockedModel).deleteStock(toDelete);
		verify(mockedView).displayStocks(list);
	}
	
	@Test
	public void testNewButtonClickCallsViewShowStockFormWithEmptyStock() throws Exception {
		Stock emptyStock = new Stock();
		
		presenter.onNewButtonClick();
		
		verify(mockedView).showStockForm(eq(emptyStock));
	}
}

class TestStockListPresenter extends StockListPresenter {
	public TestStockListPresenter(StockListView view) {
		super(view);
	}
	
	@Override
	protected StocksModel createStocksModel() {
		return mock(StocksModel.class);
	}
}
