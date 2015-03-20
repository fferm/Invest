package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Stock;
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
