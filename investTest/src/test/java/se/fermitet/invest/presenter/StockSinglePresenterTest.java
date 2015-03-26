package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.StockSingleView;

public class StockSinglePresenterTest extends POJOSinglePresenterTest<StockSinglePresenter, Stock, StockModel, StockSingleView> {
	public StockSinglePresenterTest() {
		super(StockSingleView.class, Stock.class);
	}

	@Override
	protected void assessDefaultDO(Stock obj) {
		assertNotNull(obj);
		assertNull(obj.getName());
		assertNull(obj.getSymbol());
		assertNotNull(obj.getId());
	}

	@Override
	protected StockSinglePresenter createPresenter(StockSingleView view) {
		return new TestStockSinglePresenter((StockSingleView) view);
	}
	
	@Test
	public void testSaveStockWithExistingSymbol() throws Exception {
		Stock okStock = new Stock("OK");
		Stock errorStock = new Stock("ERROR");
		
		doThrow(new ModelException(ModelExceptionType.DUMMY)).when(mockedModel).save(errorStock);
		
		presenter.onOkButtonClick(okStock);
		
		verify(mockedView, never()).displayApplicationException(anyObject());
		verify(mockedView).navigateBack();
		reset(mockedView);
		
		presenter.onOkButtonClick(errorStock);
		verify(mockedView).displayApplicationException(new ModelException(ModelExceptionType.DUMMY));
		verify(mockedView, never()).navigateBack();
		reset(mockedView);
		
		presenter.onOkButtonClick(okStock);
		verify(mockedView).clearApplicationException();
		verify(mockedView).navigateBack();
		reset(mockedView);
	}

}

class TestStockSinglePresenter extends StockSinglePresenter {
	public TestStockSinglePresenter(StockSingleView view) {
		super(view);
	}

	@Override
	protected StockModel createModel() {
		return mock(StockModel.class);
	}
}
