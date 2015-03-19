package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;
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
