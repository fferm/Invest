package se.fermitet.invest.webui;

import javax.servlet.annotation.WebServlet;

import se.fermitet.invest.webui.views.SingleStockViewImpl;
import se.fermitet.invest.webui.views.SingleTransactionViewImpl;
import se.fermitet.invest.webui.views.StockListViewImpl;
import se.fermitet.invest.webui.views.TransactionListViewImpl;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("investwebui")
public class InvestWebUI extends UI {

	public final static String STOCKLISTVIEW = "stockListView";
	public final static String SINGLESTOCKVIEW = "singleStock";
	public final static String TRANSACTIONVIEW = "transactionView";
	public final static String SINGLETRANSACTIONVIEW = "singletransaction";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = InvestWebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private DirectionalNavigator navigator; 

	@Override
	protected void init(VaadinRequest request) {
		initNavigator();

		navigator.navigateTo(STOCKLISTVIEW);
	}

	protected void initNavigator() {
		navigator = new DirectionalNavigator(this, this);
		
		navigator.addView(STOCKLISTVIEW, StockListViewImpl.class);
		navigator.addView(SINGLESTOCKVIEW, SingleStockViewImpl.class);
		navigator.addView(SINGLETRANSACTIONVIEW, SingleTransactionViewImpl.class);
		navigator.addView(TRANSACTIONVIEW, TransactionListViewImpl.class);
	}
	
	public DirectionalNavigator getDirectionalNavigator() {
		return this.navigator;
	}

}
