package se.fermitet.invest.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
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
	public void testDeleteStockWithAssociatedTransactions() throws Exception {
		Stock okStock = new Stock("OK");
		Stock errorStock = new Stock("ERROR");
		
		doThrow(new ModelException(ModelExceptionType.DUMMY)).when(mockedModel).delete(errorStock);
		
		presenter.onDeleteButtonClick(okStock);
		
		verify(mockedView, never()).displayApplicationException(anyObject());
		reset(mockedView);
		
		presenter.onDeleteButtonClick(errorStock);
		verify(mockedView).displayApplicationException(new ModelException(ModelExceptionType.DUMMY));
		reset(mockedView);
		
		presenter.onDeleteButtonClick(okStock);
		verify(mockedView).clearApplicationException();
		reset(mockedView);
	}
	
	@Test
	public void testQuotesButtonClickCallsNavigateToQuotesList() throws Exception {
		Stock stock = new Stock();
		presenter.onQuotesButtonClick(stock);

		verify((StockListView) mockedView).navigateToQuotesList(stock);
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
