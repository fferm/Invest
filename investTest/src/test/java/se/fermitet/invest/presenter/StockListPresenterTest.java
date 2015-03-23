package se.fermitet.invest.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.ListView;

public class StockListPresenterTest extends ListPresenterTest<StockListPresenter, Stock, StockModel, ListView<Stock>> {
	public StockListPresenterTest() {
		super(ListView.class, Stock.class);
	}

	@Override
	protected StockListPresenter createPresenter(ListView<Stock> view) {
		return new TestStockListPresenter(mockedView);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteStockWithAssociatedTransactions() throws Exception {
		Stock okStock = new Stock("OK");
		Stock errorStock = new Stock("ERROR");
		
		doThrow(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS).when(mockedModel).delete(errorStock);
		
		presenter.onDeleteButtonClick(okStock);
		
		verify(mockedView, never()).displayApplicationException(anyObject());
		reset(mockedView);
		
		presenter.onDeleteButtonClick(errorStock);
		verify(mockedView).displayApplicationException(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS);
		reset(mockedView);
		
		presenter.onDeleteButtonClick(okStock);
		verify(mockedView).clearApplicationException();
		reset(mockedView);
	}
}

class TestStockListPresenter extends StockListPresenter {
	public TestStockListPresenter(ListView<Stock> view) {
		super(view);
	}
	
	@Override
	protected StockModel createModel() {
		return mock(StockModel.class);
	}
}
