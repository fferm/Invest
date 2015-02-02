package se.fermitet.invest.webui.views;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.SingleStockPresenter;
import se.fermitet.invest.viewinterface.SingleStockView;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class SingleStockViewImpl extends CustomComponent implements SingleStockView, View {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1028596715063809826L;

	TextField symbolField;
	TextField nameField;

	Button okButton;
	Button cancelButton;

	private Stock stock;
	
	SingleStockPresenter presenter;

	
	public SingleStockViewImpl() {
		this.presenter = createPresenter();

		FormLayout layout = new FormLayout();
		
		initFields();
		
		layout.addComponent(symbolField);
		layout.addComponent(nameField);
		
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.addComponent(okButton);
		buttonPanel.addComponent(cancelButton);
		
		layout.addComponent(buttonPanel);
		
		setCompositionRoot(layout);
	}
	
	private void initFields() {
		symbolField = new TextField("Ticker");
		nameField = new TextField("Namn");
		
		okButton = new Button("OK");
		okButton.addClickListener((Button.ClickListener) l -> {
			onOkClick();
		});
		
		cancelButton = new Button("Avbryt");
		cancelButton.addClickListener((Button.ClickListener) l -> {
			onCancelClick();
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		Stock desired = presenter.getStockBasedOnIdString(event.getParameters());
		
		syncUiWithStock(desired);
	}

	protected SingleStockPresenter createPresenter() {
		return new SingleStockPresenter();
	}

	
	private void syncUiWithStock(Stock stock) {
		this.stock = stock;

		String nameTxt = stock.getName() == null ? "" : stock.getName();
		String symbolTxt = stock.getSymbol() == null ? "" : stock.getSymbol();

		symbolField.setValue(symbolTxt);
		nameField.setValue(nameTxt);
	}
	
	private void onOkClick() {
		System.out.println("Name: " + stock.getName() + "    Symbol: " + stock.getSymbol());
	}
	
	private void onCancelClick() {
		
	}
}
