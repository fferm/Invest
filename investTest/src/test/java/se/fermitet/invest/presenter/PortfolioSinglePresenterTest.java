package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.PortfolioModel;
import se.fermitet.invest.viewinterface.PortfolioSingleView;

public class PortfolioSinglePresenterTest extends POJOSinglePresenterTest<PortfolioSinglePresenter, Portfolio, PortfolioModel, PortfolioSingleView> {

	public PortfolioSinglePresenterTest() {
		super(PortfolioSingleView.class, Portfolio.class);
	}

	@Override
	protected void assessDefaultDO(Portfolio obj) {
		assertNotNull(obj);
		assertNull(obj.getName());
	}

	@Override
	protected PortfolioSinglePresenter createPresenter(PortfolioSingleView view) {
		return new TestPortfolioSinglePresenter(view);
	}

}

class TestPortfolioSinglePresenter extends PortfolioSinglePresenter {
	public TestPortfolioSinglePresenter(PortfolioSingleView view) {
		super(view);
	}

	@Override
	protected PortfolioModel createModel() {
		return mock(PortfolioModel.class);
	}
}

