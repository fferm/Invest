package se.fermitet.invest.webui.views;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockSinglePresenter;
import se.fermitet.invest.viewinterface.StockSingleView;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.ui.Layout;

public class StockSingleViewImpl extends POJOSingleViewImpl<StockSinglePresenter, Stock> implements StockSingleView {
	private static final long serialVersionUID = 1028596715063809826L;

	POJOPropertyTextFieldAdapter<Stock, String> symbolAdapter;
	POJOPropertyTextFieldAdapter<Stock, String> nameAdapter;

	@Override
	protected void initAndAddFields(Layout layout) {
		symbolAdapter = new POJOPropertyTextFieldAdapter<Stock, String>(Stock.class, "Ticker");
		symbolAdapter.getUI().addValueChangeListener(e -> valueChanged());
		layout.addComponent(symbolAdapter.getUI());
		
		nameAdapter = new POJOPropertyTextFieldAdapter<Stock, String>(Stock.class, "Namn");
		nameAdapter.getUI().addValueChangeListener(e -> valueChanged());
		layout.addComponent(nameAdapter.getUI());
	}

	protected StockSinglePresenter createPresenter() {
		return new StockSinglePresenter(this);
	}

	@Override
	protected void bindToData() {
		symbolAdapter.bindToProperty(pojo, "symbol");
		nameAdapter.bindToProperty(pojo, "name");
	}
}
