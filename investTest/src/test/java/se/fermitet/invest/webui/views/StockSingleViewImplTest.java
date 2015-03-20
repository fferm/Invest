package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockSinglePresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class StockSingleViewImplTest extends POJOSingleViewImplTest<StockSingleViewImpl, StockSinglePresenter, Stock> {
	
	public StockSingleViewImplTest() {
		super(Stock.class);
	}

	@Override
	protected StockSingleViewImpl createViewImpl() {
		return new TestSingleSingleViewImpl();
	}

	@Override
	protected Stock getTestPojo() {
		return new Stock("my name", "my symbol");
	}

	@Override
	protected void checkUIAgainstPojo(Stock pojo) {
		assertEquals(pojo.getName(), view.nameAdapter.getUI().getValue());
		assertEquals(pojo.getSymbol(), view.symbolAdapter.getUI().getValue());
	}

	@Override
	protected void updateUIFromPOJO(Stock updated) {
		view.nameAdapter.setValue(updated.getName());
		view.symbolAdapter.setValue(updated.getSymbol());
	}

	@Override
	protected void makeUIDataInvalid() {
		view.symbolAdapter.getUI().setValue(null);
	}

	@Override
	protected void checkFieldValidity(boolean shouldBeValid) {
		assertTrue("symbol field validity should be " + shouldBeValid, view.symbolAdapter.getUI().isValid() == shouldBeValid);
	}
	
	@Test
	public void testHasComponents() throws Exception {
		assertNotNull("symbol", view.symbolAdapter);
		assertNotNull("name", view.nameAdapter);
	}

}

@SuppressWarnings("serial")
class TestSingleSingleViewImpl extends StockSingleViewImpl {
	@Override
	protected StockSinglePresenter createPresenter() {
		return mock(StockSinglePresenter.class);
	}
	
	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}
