package se.fermitet.invest.webui.views;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.invest.viewinterface.SingleStockView;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;

public class SingleStockViewImpl extends SinglePOJOViewImpl<SingleStockPresenter, Stock> implements SingleStockView {
	private static final long serialVersionUID = 1028596715063809826L;

	POJOPropertyTextFieldAdapter<Stock, String> symbolAdapter;
	POJOPropertyTextFieldAdapter<Stock, String> nameAdapter;

	@Override
	protected Component createMainLayout() {
		FormLayout layout = new FormLayout();

		initFields();

		layout.addComponent(symbolAdapter.getUI());
		layout.addComponent(nameAdapter.getUI());

		layout.addComponent(initButtonPanel());

		return layout;
	}

	private void initFields() {
		symbolAdapter = new POJOPropertyTextFieldAdapter<Stock, String>(Stock.class, "Ticker");
		symbolAdapter.getUI().addValueChangeListener(e -> valueChanged());

		nameAdapter = new POJOPropertyTextFieldAdapter<Stock, String>(Stock.class, "Namn");
		nameAdapter.getUI().addValueChangeListener(e -> valueChanged());

	}

	protected SingleStockPresenter createPresenter() {
		return new SingleStockPresenter(this);
	}

	@Override
	protected void bindToData() {
		symbolAdapter.bindToProperty(pojo, "symbol");
		nameAdapter.bindToProperty(pojo, "name");
	}
}
