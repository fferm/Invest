package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class SingleStockViewImplTest extends SinglePOJOViewImplTest<SingleStockViewImpl, SingleStockPresenter, Stock> {
	
	public SingleStockViewImplTest() {
		super(Stock.class);
	}

	@Override
	protected SingleStockViewImpl createViewImpl() {
		return new TestSingleStockViewImpl();
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
class TestSingleStockViewImpl extends SingleStockViewImpl {
	@Override
	protected SingleStockPresenter createPresenter() {
		return mock(SingleStockPresenter.class);
	}
	
	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}
