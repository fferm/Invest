package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.StockListView;

public class StockListPresenterTest extends ListPresenterTest<StockListPresenter, Stock, StockModel, StockListView> {
	public StockListPresenterTest() {
		super(StockListView.class, Stock.class);
	}

	@Override
	protected StockListPresenter createPresenter(StockListView view) {
		return new TestStockListPresenter(mockedView);
	}
	
	@Test
	public void testDeleteFromViewCallsModelDelete() throws Exception {
		Stock toDelete = new Stock("TEST");
		List<Stock> list = new ArrayList<Stock>();
		when(mockedModel.getAll()).thenReturn(list);
		
		presenter.onDeleteButtonClick(toDelete);
		
		verify(mockedModel).delete(toDelete);
		verify(mockedView).displayData(list);
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
