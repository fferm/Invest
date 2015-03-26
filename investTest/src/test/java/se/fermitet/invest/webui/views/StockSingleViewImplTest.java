package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.presenter.StockSinglePresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class StockSingleViewImplTest extends POJOSingleViewImplTest<StockSingleViewImpl, StockSinglePresenter, Stock> {
	
	public StockSingleViewImplTest() {
		super(Stock.class);
	}

	@Override
	protected StockSingleViewImpl createViewImpl() {
		return new TestStockSingleViewImpl();
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
	
	@Test
	public void testErrorOnAddingSecondStockWithSameSymbol() throws Exception {
		Stock stock = new Stock("TEST");
		
		assertNull("null before", view.okButton.getComponentError());
		assertFalse("view has error", view.hasApplicationException());
		
		view.displayApplicationException(new ModelException(ModelExceptionType.CANNOT_SAVE_STOCK_SINCE_THERE_IS_ALREADY_A_STOCK_WITH_THAT_SYMBOL, stock));
		assertNotNull("not null after", view.okButton.getComponentError());
		assertTrue("view has error", view.hasApplicationException());
		
		view.clearApplicationException();
		assertNull("null again after clear", view.okButton.getComponentError());
		assertFalse(view.hasApplicationException());
	}


}

@SuppressWarnings("serial")
class TestStockSingleViewImpl extends StockSingleViewImpl {
	@Override
	protected StockSinglePresenter createPresenter() {
		return mock(StockSinglePresenter.class);
	}
	
	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}
