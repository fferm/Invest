package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.PortfolioModel;
import se.fermitet.invest.viewinterface.ListView;

public class PortfolioListPresenterTest extends ListPresenterTest<PortfolioListPresenter, Portfolio, PortfolioModel, ListView<Portfolio>>{

	public PortfolioListPresenterTest() {
		super(ListView.class, Portfolio.class);
	}

	@Override
	protected PortfolioListPresenter createPresenter(ListView<Portfolio> view) {
		return new TestPortfolioListPresenter(view);
	}

}

class TestPortfolioListPresenter extends PortfolioListPresenter {
	public TestPortfolioListPresenter(ListView<Portfolio> view) {
		super(view);
	}
	
	@Override
	protected PortfolioModel createModel() {
		return mock(PortfolioModel.class);
	}
}

