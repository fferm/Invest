package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

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


	
	@Test
	public void testEmptySymbolShouldGiveValidationFault() throws Exception {
		Stock initialStock = new Stock("Name", "Symbol");
		
		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initialStock);
		
		view.enter(mock(ViewChangeEvent.class));

		
		assertTrue("ok button enabled before", view.okButton.isEnabled());
		assertTrue("symbol field valid before", view.symbolAdapter.getUI().isValid());
		assertTrue("form valid before", view.isValid());
		
		view.symbolAdapter.getUI().setValue(null);
		
		assertFalse("ok button disabled after", view.okButton.isEnabled());
		assertFalse("symbol field not valid after", view.symbolAdapter.getUI().isValid());
		assertFalse("form not valid after", view.isValid());
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
