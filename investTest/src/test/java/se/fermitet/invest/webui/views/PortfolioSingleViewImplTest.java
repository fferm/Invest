package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.presenter.PortfolioSinglePresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class PortfolioSingleViewImplTest extends POJOSingleViewImplTest<PortfolioSingleViewImpl, PortfolioSinglePresenter, Portfolio> {

	public PortfolioSingleViewImplTest() {
		super(Portfolio.class);
	}

	@Override
	protected PortfolioSingleViewImpl createViewImpl() {
		return new TestPortfolioSingleViewImpl();
	}

	@Override
	protected Portfolio getTestPojo() {
		return new Portfolio("TEST");
	}

	@Override
	protected void checkUIAgainstPojo(Portfolio pojo) {
		assertEquals(pojo.getName(), view.nameAdapter.getUI().getValue());
	}

	@Override
	protected void updateUIFromPOJO(Portfolio updated) {
		view.nameAdapter.setValue(updated.getName());
	}

	@Override
	protected void makeUIDataInvalid() {
		view.nameAdapter.getUI().setValue(null);
	}

	@Override
	protected void checkFieldValidity(boolean shouldBeValid) {
		assertTrue("name field validity should be " + shouldBeValid, view.nameAdapter.getUI().isValid() == shouldBeValid);
	}

}

@SuppressWarnings("serial")
class TestPortfolioSingleViewImpl extends PortfolioSingleViewImpl {
	@Override
	protected PortfolioSinglePresenter createPresenter() {
		return mock(PortfolioSinglePresenter.class);
	}
	
	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}

