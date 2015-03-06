package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.invest.viewinterface.SingleStockView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

public class SingleStockViewImpl extends ViewImpl<SingleStockPresenter> implements SingleStockView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1028596715063809826L;

	POJOPropertyTextFieldAdapter<Stock> symbolAdapter;
	POJOPropertyTextFieldAdapter<Stock> nameAdapter;

	Button okButton;
	Button cancelButton;

	private Stock stock;

	@Override
	protected Component createMainLayout() {
		FormLayout layout = new FormLayout();

		initFields();

		layout.addComponent(symbolAdapter.getUI());
		layout.addComponent(nameAdapter.getUI());

		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.addComponent(okButton);
		buttonPanel.addComponent(cancelButton);

		layout.addComponent(buttonPanel);

		return layout;
	}

	private void initFields() {
		symbolAdapter = new POJOPropertyTextFieldAdapter<Stock>(Stock.class, "Ticker");
		symbolAdapter.getUI().addValueChangeListener(e -> valueChanged());

		nameAdapter = new POJOPropertyTextFieldAdapter<Stock>(Stock.class, "Namn");
		nameAdapter.getUI().addValueChangeListener(e -> valueChanged());

		okButton = new Button("OK");
		okButton.addClickListener((Button.ClickListener) l -> {
			onOkClick();
		});

		cancelButton = new Button("Avbryt");
		cancelButton.addClickListener((Button.ClickListener) l -> {
			onCancelClick();
		});
	}

	private void valueChanged() {
		okButton.setEnabled(isValid());
	}

	@Override
	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		if (parameters.size() == 0) this.stock = presenter.getStockBasedOnIdString(null);
		else this.stock = presenter.getStockBasedOnIdString(parameters.get(0).getValue());

		bindToData();
	}

	protected SingleStockPresenter createPresenter() {
		return new SingleStockPresenter(this);
	}


	private void bindToData() {
		symbolAdapter.bindToProperty(stock, "symbol");
		nameAdapter.bindToProperty(stock, "name");
	}

	private void onOkClick() {
		if (isValid()) presenter.onOkButtonClick(this.stock);
	}

	private void onCancelClick() {
		this.presenter.onCancelButtonClick();
	}

	@Override
	public void navigateBack() {
		this.getNavigator().navigateBack();
	}
}
