package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenterTest {
	private StockListView mockedView;
	private StockListPresenter presenter;
	private StockModel mockedModel;

	@Before
	public void setUp() throws Exception {
		mockedView = mock(StockListView.class);
		presenter = new TestStockListPresenter(mockedView);
		mockedModel = presenter.model;
		
		reset(mockedView);
		reset(mockedModel);
	}
	
	@Test
	public void testFillStockListWithAllStock() throws Exception {
		List<Stock> list = new ArrayList<Stock>();
		when(mockedModel.getAll()).thenReturn(list);
	
		presenter.fillStockListWithAllStocks();
		
		verify(mockedModel).getAll();
		verify(mockedView).displayStocks(list);
	}
	
	@Test
	public void testDeleteFromViewCallsModelDelete() throws Exception {
		Stock toDelete = new Stock("TEST");
		List<Stock> list = new ArrayList<Stock>();
		when(mockedModel.getAll()).thenReturn(list);
		
		presenter.onDeleteButtonClick(toDelete);
		
		verify(mockedModel).delete(toDelete);
		verify(mockedView).displayStocks(list);
	}
	
	@Test
	public void testNewButtonClickCallsEditSingleStock() throws Exception {
		presenter.onNewButtonClick();
		
		verify(mockedView).navigateToSingleStockView(null);
	}
	
	@Test
	public void testEditButtonClickCallsEditSingleStock() throws Exception {
		Stock editStock = new Stock("TST", "TST");
		
		presenter.onEditButtonClick(editStock);

		verify(mockedView).navigateToSingleStockView(editStock);
	}
}

class TestStockListPresenter extends StockListPresenter {
	public TestStockListPresenter(StockListView view) {
		super(view);
	}
	
	@Override
	protected StockModel createModel() {
		return mock(StockModel.class);
	}
}
