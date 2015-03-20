package se.fermitet.invest.webui;

import javax.servlet.annotation.WebServlet;

import se.fermitet.invest.webui.views.StockListViewImpl;
import se.fermitet.invest.webui.views.StockSingleViewImpl;
import se.fermitet.invest.webui.views.TransactionListViewImpl;
import se.fermitet.invest.webui.views.TransactionSingleViewImpl;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("investwebui")
public class InvestWebUI extends UI {

	public final static String STOCK_LIST = "stockListView";
	public final static String STOCK_SINGLE = "singleStock";
	public final static String TRANSACTION_LIST = "transactionView";
	public final static String TRANSACTION_SINGLE = "singletransaction";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = InvestWebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private DirectionalNavigator navigator; 

	@Override
	protected void init(VaadinRequest request) {
		initNavigator();

		navigator.navigateTo(STOCK_LIST);
	}

	protected void initNavigator() {
		navigator = new DirectionalNavigator(this, this);
		
		navigator.addView(STOCK_LIST, StockListViewImpl.class);
		navigator.addView(STOCK_SINGLE, StockSingleViewImpl.class);
		navigator.addView(TRANSACTION_SINGLE, TransactionSingleViewImpl.class);
		navigator.addView(TRANSACTION_LIST, TransactionListViewImpl.class);
	}
	
	public DirectionalNavigator getDirectionalNavigator() {
		return this.navigator;
	}

}
