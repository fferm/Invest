package se.fermitet.invest.webui.views;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.testData.StockDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;
 
public class StockListViewImplTest extends ListViewImplTest<StockListViewImpl, StockListPresenter, Stock> {
	@Override
	protected StockListViewImpl createViewImpl() {
		return new TestStockListViewImpl();
	}

	@Override
	protected void initTestData() {
		testDataUnsorted = new ArrayList<Stock>(new StockDataProvider().getTestData());

		testDataSorted = new ArrayList<Stock>(testDataUnsorted);
		testDataSorted.sort((Stock o1, Stock o2) -> {
			String o1Symbol = o1.getSymbol();
			String o2Symbol = o2.getSymbol();
			return o1Symbol.compareTo(o2Symbol);
		});
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.STOCK_SINGLE;
	}
}

@SuppressWarnings("serial")
class TestStockListViewImpl extends StockListViewImpl {
	TestStockListViewImpl() {
		super();
	}
	
	@Override
	protected StockListPresenter createPresenter() {
		return mock(StockListPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}
