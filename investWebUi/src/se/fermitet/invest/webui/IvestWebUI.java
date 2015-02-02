package se.fermitet.invest.webui;

import javax.servlet.annotation.WebServlet;

import se.fermitet.invest.webui.views.SingleStockViewImpl;
import se.fermitet.invest.webui.views.StockListViewImpl;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("investwebui")
public class IvestWebUI extends UI {

	public final String STOCKLISTVIEW = "stockListView";
	public final String SINGLESTOCKVIEW = "singleStock";

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = IvestWebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private Navigator navigator; 

	@Override
	protected void init(VaadinRequest request) {
		navigator = new Navigator(this, this);
		
		navigator.addView(STOCKLISTVIEW, StockListViewImpl.class);
		navigator.addView(SINGLESTOCKVIEW, SingleStockViewImpl.class);

		navigateTo(STOCKLISTVIEW);
	}
	
	public void navigateTo(String screenName) {
		navigator.navigateTo(screenName);
	}

}