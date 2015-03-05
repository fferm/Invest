package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class SingleStockViewImplTest {
	private SingleStockViewImpl view;
	private SingleStockPresenter mockedPresenter;

	@Before
	public void setUp() {
		view = new TestSingleStockViewImpl();
		mockedPresenter = view.presenter;
	}

	@Test
	public void testEnterShowsSomeData_notNull() throws Exception {
		String nameValue = "my name";
		String symbolValue = "my symbol";
		
		Stock testStock = new Stock(nameValue, symbolValue);
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(testStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		assertEquals(nameValue, view.nameAdapter.getUI().getValue());
		assertEquals(symbolValue, view.symbolAdapter.getUI().getValue());
	}
	
	@Test
	public void testEnterShowsSomeData_null() throws Exception {
		Stock testStock = new Stock();
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(testStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		assertNull(view.nameAdapter.getUI().getValue());
		assertNull(view.symbolAdapter.getUI().getValue());
	}
	
	@Test
	public void testCancelButtonCallsPresenter() throws Exception {
		String nameValue = "my name";
		String symbolValue = "my symbol";
		
		Stock testStock = new Stock(nameValue, symbolValue);
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(testStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		view.cancelButton.click();
		
		verify(mockedPresenter).onCancelButtonClick();
	}
	
	@Test
	public void testNavigateBack() throws Exception {
		DirectionalNavigator mockedNavigator = view.getNavigator();
		
		view.navigateBack();
		
		verify(mockedNavigator).navigateBack();
	}
	
	@Test
	public void testOKButtonCallsPresenterWithUpdatedStock() throws Exception {
		Stock initialStock = new Stock("Name", "Symbol");
		
		String newName = "new name";
		String newSymbol = "new symbol";
		
		Stock updatedStock = new Stock(newName, newSymbol);
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(initialStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		view.nameAdapter.getUI().setValue(newName);
		view.symbolAdapter.getUI().setValue(newSymbol);
		
		reset(mockedPresenter);
		
		view.okButton.click();
		
		verify(mockedPresenter).onOkButtonClick(eq(updatedStock));
	}
	
	@Test
	public void testEmptySymbolShouldGiveValidationFault() throws Exception {
		Stock initialStock = new Stock("Name", "Symbol");
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(initialStock);
		
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
