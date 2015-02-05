package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.invest.viewinterface.SingleStockView;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class SingleStockViewImpl extends ViewImpl<SingleStockPresenter> implements SingleStockView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1028596715063809826L;

	TextField symbolField;
	TextField nameField;

	Button okButton;
	Button cancelButton;

	private Stock stock;

	@Override
	protected Component createMainLayout() {
		FormLayout layout = new FormLayout();

		initFields();

		layout.addComponent(symbolField);
		layout.addComponent(nameField);

		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.addComponent(okButton);
		buttonPanel.addComponent(cancelButton);

		layout.addComponent(buttonPanel);

		return layout;
	}

	private void initFields() {
		symbolField = new TextField("Ticker");
		symbolField.setImmediate(true);
		symbolField.addValidator(new BeanValidator(Stock.class, "symbol"));
		symbolField.addValueChangeListener(e -> valueChanged());
		symbolField.setNullRepresentation("");
		symbolField.setNullSettingAllowed(true);

		nameField = new TextField("Namn");
		nameField.setImmediate(true);
		nameField.addValidator(new BeanValidator(Stock.class, "name"));
		nameField.addValueChangeListener(e -> valueChanged());
		nameField.setNullRepresentation("");
		nameField.setNullSettingAllowed(true);

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
		BeanItem<Stock> item = new BeanItem<Stock>(stock);

		symbolField.setPropertyDataSource(item.getItemProperty("symbol"));
		nameField.setPropertyDataSource(item.getItemProperty("name"));
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
