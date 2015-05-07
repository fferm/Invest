package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.presenter.PortfolioListPresenter;
import se.fermitet.invest.testData.TestDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class PortfolioListViewImplTest extends ListViewImplTest<PortfolioListViewImpl, PortfolioListPresenter, Portfolio>{

	@Override
	protected PortfolioListViewImpl createViewImpl() {
		return new TestPortfolioListViewImpl();
	}

	@Override
	protected void initTestData() {
		TestDataProvider provider = new TestDataProvider();
		
		testDataUnsorted = provider.getPortfolios();

		testDataSorted = new ArrayList<Portfolio>(testDataUnsorted);
		testDataSorted.sort((Portfolio o1, Portfolio o2) -> {
			String o1Name = o1.getName();
			String o2Name = o2.getName();
			return o1Name.compareTo(o2Name);
		});
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.PORTFOLIO_SINGLE;
	}
	
	@Test
	public void testErrorOnDeletingPortfolioWithAssociatedTransactions() throws Exception {
		Portfolio portfolio = new Portfolio("TEST");
		
		assertNull("null before", view.deleteButton.getComponentError());
		assertFalse(view.hasApplicationException());
		
		view.displayApplicationException(new ModelException(ModelExceptionType.CANNOT_DELETE_PORTFOLIO_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, portfolio));
		assertNotNull("not null after", view.deleteButton.getComponentError());
		assertTrue(view.hasApplicationException());
		
		view.clearApplicationException();
		assertNull("null again after clear", view.deleteButton.getComponentError());
		assertFalse(view.hasApplicationException());
	}


}

@SuppressWarnings("serial")
class TestPortfolioListViewImpl extends PortfolioListViewImpl {
	TestPortfolioListViewImpl() {
		super();
	}
	
	@Override
	protected PortfolioListPresenter createPresenter() {
		return mock(PortfolioListPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}

