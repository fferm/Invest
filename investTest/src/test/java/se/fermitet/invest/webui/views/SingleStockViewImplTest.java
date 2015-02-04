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
	private TestSingleStockViewImpl view;
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
		
		Stock testStock = new Stock(symbolValue).setName(nameValue);
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(testStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		assertEquals(nameValue, view.nameField.getValue());
		assertEquals(symbolValue, view.symbolField.getValue());
	}
	
	@Test
	public void testEnterShowsSomeData_null() throws Exception {
		Stock testStock = new Stock();
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(testStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		assertEquals("", view.nameField.getValue());
		assertEquals("", view.symbolField.getValue());
	}
	
	@Test
	public void testCancelButtonCallsPresenter() throws Exception {
		String nameValue = "my name";
		String symbolValue = "my symbol";
		
		Stock testStock = new Stock(symbolValue).setName(nameValue);
		
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
		Stock initialStock = new Stock().setName("Name").setSymbol("Symbol");
		
		String newName = "new name";
		String newSymbol = "new symbol";
		
		Stock updatedStock = new Stock().setName(newName).setSymbol(newSymbol);
		
		when(mockedPresenter.getStockBasedOnIdString(anyString())).thenReturn(initialStock);
		
		view.enter(mock(ViewChangeEvent.class));
		
		view.nameField.setValue(newName);
		view.symbolField.setValue(newSymbol);
		
		reset(mockedPresenter);
		
		view.okButton.click();
		
		verify(mockedPresenter).onOkButtonClick(eq(updatedStock));
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
