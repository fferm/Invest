package se.fermitet.invest.webui.views;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.presenter.PortfolioListPresenter;
import se.fermitet.invest.testData.PortfolioDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class PortfolioListViewImplTest extends ListViewImplTest<PortfolioListViewImpl, PortfolioListPresenter, Portfolio>{

	@Override
	protected PortfolioListViewImpl createViewImpl() {
		return new TestPortfolioListViewImpl();
	}

	@Override
	protected void initTestData() {
		PortfolioDataProvider provider = new PortfolioDataProvider();
		
		testDataUnsorted = provider.getTestData();

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

