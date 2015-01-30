package se.fermitet.invest.webui;

import javax.servlet.annotation.WebServlet;

import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.webui.views.StockListViewImpl;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("investwebui")
public class IvestWebUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = IvestWebUI.class)
	public static class Servlet extends VaadinServlet {
	} 

	@Override
	protected void init(VaadinRequest request) {
		StockListViewImpl view = new StockListViewImpl();
		new StockListPresenter(view);
		
		setContent(view);
	}

}