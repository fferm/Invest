package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.presenter.Presenter;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.VerticalLayout;

public class NavBarTest {
	private NavBarViewImpl view;
	
	@Before
	public void setUp() throws Exception {
		this.view = new TestNavBarViewImpl();
	}
	
	@Test
	public void testHasComponents() throws Exception {
		assertNotNull("Stock nav", view.stock);
		assertNotNull("Transaction", view.transaction);
		assertNotNull("Portfolio", view.portfolio);
	}
	
	@Test
	public void testStockClick() throws Exception {
		DirectionalNavigator navigator = view.getNavigator();

		view.stock.click();
		
		verify(navigator).navigateTo(InvestWebUI.STOCK_LIST);
	}
	
	@Test
	public void testTransactionClick() throws Exception {
		DirectionalNavigator navigator = view.getNavigator();

		view.transaction.click();
		
		verify(navigator).navigateTo(InvestWebUI.TRANSACTION_LIST);
	}
	
	@Test
	public void testPortfolioClick() throws Exception {
		DirectionalNavigator navigator = view.getNavigator();
		
		view.portfolio.click();
		
		verify(navigator).navigateTo(InvestWebUI.PORTFOLIO_LIST);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testGenericViewImplHasNavBar() throws Exception {
		ViewImpl genericView = new GenericViewImpl();
		
		assertTrue(checkForOneOfNavBarButtonsRecursively(genericView));
	}

	private boolean checkForOneOfNavBarButtonsRecursively(HasComponents component) {
		for (Iterator<Component> iter = component.iterator(); iter.hasNext(); ) {
			Component current = iter.next();
			
			if (current instanceof NavBarViewImpl) return true;
			
			if (current instanceof HasComponents) {
				HasComponents hasComponents = (HasComponents) current;

				return checkForOneOfNavBarButtonsRecursively(hasComponents);
			}
		}
		return false;
	}

}
@SuppressWarnings("serial")
class TestNavBarViewImpl extends NavBarViewImpl {
	DirectionalNavigator navigator = mock(DirectionalNavigator.class);
	@Override
	protected DirectionalNavigator getNavigator() {
		return navigator;
	}
}
@SuppressWarnings({ "rawtypes", "serial" })
class GenericViewImpl extends ViewImpl {
	public GenericViewImpl() {
		super();
		super.init();
	}

	@Override
	protected Component createMainLayout() {
		return new VerticalLayout();
	}

	@Override
	protected Presenter createPresenter() {
		return null;
	}

	@Override
	protected void enter(ViewChangeEvent event, List parameters) {
	}
	
}
