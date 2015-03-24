package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
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
	
	@Test
	public void testErrorOnDeletingStockWithAssociatedTransactions() throws Exception {
		Stock stock = new Stock("TEST");
		
		assertNull("null before", view.deleteButton.getComponentError());
		
		view.displayApplicationException(new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, stock));
		assertNotNull("not null after", view.deleteButton.getComponentError());
		
		view.clearApplicationException();
		assertNull("null again after clear", view.deleteButton.getComponentError());
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
