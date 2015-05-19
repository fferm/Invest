package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.testData.TestDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.invest.webui.navigation.EntityNameHelper;
import se.fermitet.vaadin.navigation.DirectionalNavigator;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.ui.Button;
 
public class StockListViewImplTest extends ListViewImplTest<StockListViewImpl, StockListPresenter, Stock> {

	public StockListViewImplTest() {
		super(Stock.class);
	}

	@Override
	protected StockListViewImpl createViewImpl() {
		return new TestStockListViewImpl();
	}

	@Override
	protected List<Stock> getTestData() {
		return new TestDataProvider().getStocks();
	}

	@Override
	protected Comparator<? super Stock> getComparator() {
		return (Stock o1, Stock o2) -> {
			String o1Symbol = o1.getSymbol();
			String o2Symbol = o2.getSymbol();
			return o1Symbol.compareTo(o2Symbol);
		};
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.STOCK_SINGLE;
	}
	
	@Test
	public void testErrorOnDeletingStockWithAssociatedTransactions() throws Exception {
		Stock stock = new Stock("TEST");
		
		assertNull("null before", view.deleteButton.getComponentError());
		assertFalse(view.hasApplicationException());
		
		view.displayApplicationException(new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, stock));
		assertNotNull("not null after", view.deleteButton.getComponentError());
		assertTrue(view.hasApplicationException());
		
		view.clearApplicationException();
		assertNull("null again after clear", view.deleteButton.getComponentError());
		assertFalse(view.hasApplicationException());
	}
	
	@Override
	protected List<Button> getButtonsToTestIsEnabledWhenItemSelectedInList() {
		List<Button> superList = super.getButtonsToTestIsEnabledWhenItemSelectedInList();
		superList.add(view.quotesButton);
		
		return superList;
	}
	
	@Test
	public void testQuotesButton() throws Exception {
		Button quotesButton = view.quotesButton;
		
		assertNotNull("not null", quotesButton);
		
		Stock toSelect = testDataUnsorted.get(0);
		view.tableAdapter.select(toSelect);

		quotesButton.click();
		
		verify(mockedPresenter).onQuotesButtonClick(toSelect);
	}
	
	@Test
	public void testQuotesNavigation() throws Exception {
		Stock testData = testDataSorted.get(2);
		
		view.navigateToQuotesList(testData);
		verify(view.getNavigator()).navigateTo(InvestWebUI.QUOTE_LIST, new URIParameter(EntityNameHelper.entityNameFor(Stock.class), testData.getId().toString()));
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
