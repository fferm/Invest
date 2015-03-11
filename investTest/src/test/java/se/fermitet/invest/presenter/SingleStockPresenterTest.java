package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.SinglePOJOView;
import se.fermitet.invest.viewinterface.SingleStockView;

public class SingleStockPresenterTest extends SinglePOJOPresenterTest<SingleStockPresenter, Stock, StockModel, SingleStockView> {
	public SingleStockPresenterTest() {
		super(SingleStockView.class, Stock.class);
	}

	@Override
	protected void assessDefaultDO(Stock obj) {
		assertNotNull(obj);
		assertNull(obj.getName());
		assertNull(obj.getSymbol());
		assertNotNull(obj.getId());
	}

	@Test
	public void testCancelNavigatesToStockList() throws Exception {
		presenter.onCancelButtonClick();
		
		verify(mockedView).navigateBack();
	}
	
	@Test
	public void testOkSavesAndNavigatesBack() throws Exception {
		Stock testStock = new Stock("name", "symbol");
		
		presenter.onOkButtonClick(testStock);
		
		verify(mockedModel).save(testStock);
		verify(mockedView).navigateBack();
	}

	@Override
	protected SingleStockPresenter createPresenter(SinglePOJOView view) {
		return new TestSingleStockPresenter((SingleStockView) view);
	}
}

class TestSingleStockPresenter extends SingleStockPresenter {
	public TestSingleStockPresenter(SingleStockView view) {
		super(view);
	}

	@Override
	protected StockModel createModel() {
		return mock(StockModel.class);
	}
}
