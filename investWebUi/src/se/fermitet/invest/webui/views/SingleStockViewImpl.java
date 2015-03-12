package se.fermitet.invest.webui.views;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.invest.viewinterface.SingleStockView;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

public class SingleStockViewImpl extends SinglePOJOViewImpl<SingleStockPresenter, Stock> implements SingleStockView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1028596715063809826L;

	POJOPropertyTextFieldAdapter<Stock, String> symbolAdapter;
	POJOPropertyTextFieldAdapter<Stock, String> nameAdapter;

	Button okButton;
	Button cancelButton;

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
		symbolAdapter = new POJOPropertyTextFieldAdapter<Stock, String>(Stock.class, "Ticker");
		symbolAdapter.getUI().addValueChangeListener(e -> valueChanged());

		nameAdapter = new POJOPropertyTextFieldAdapter<Stock, String>(Stock.class, "Namn");
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

//	@Override
//	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
//		if (parameters.size() == 0) this.pojo = presenter.getDOBasedOnIdString(null);
//		else this.pojo = presenter.getDOBasedOnIdString(parameters.get(0).getValue());
//
//		bindToData();
//	}

	protected SingleStockPresenter createPresenter() {
		return new SingleStockPresenter(this);
	}

	@Override
	protected void bindToData() {
		symbolAdapter.bindToProperty(pojo, "symbol");
		nameAdapter.bindToProperty(pojo, "name");
	}

	private void onOkClick() {
		if (isValid()) presenter.onOkButtonClick(this.pojo);
	}

	private void onCancelClick() {
		this.presenter.onCancelButtonClick();
	}

	@Override
	public void navigateBack() {
		this.getNavigator().navigateBack();
	}
}
