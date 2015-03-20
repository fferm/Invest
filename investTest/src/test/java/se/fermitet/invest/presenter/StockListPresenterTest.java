package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Stock;
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
