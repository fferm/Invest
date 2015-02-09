package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.SingleTransactionPresenter;
import se.fermitet.invest.viewinterface.SingleTransactionView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.POJOAttributeTextField;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;

public class SingleTransactionViewImpl extends ViewImpl<SingleTransactionPresenter> implements SingleTransactionView {

	private static final long serialVersionUID = 8004896867328107503L;
	ComboBox stockCombo;
	PopupDateField datePopup;
	POJOAttributeTextField<Transaction> priceField;
	POJOAttributeTextField<Transaction> feeField;
	POJOAttributeTextField<Transaction> numberField;
	private Label titleLabel;

	@Override
	protected Component createMainLayout() {
		FormLayout mainLayout = new FormLayout();
		
		initFields();
		
		mainLayout.addComponent(titleLabel);
		mainLayout.addComponent(stockCombo);
		mainLayout.addComponent(datePopup);
//		mainLayout.addComponent(numberField);
		mainLayout.addComponent(priceField);
		mainLayout.addComponent(feeField);
		
		return mainLayout;
	}

	private void initFields() {
		titleLabel = new Label("Affär");
		
		stockCombo = new ComboBox("Aktie");
		
		datePopup = new PopupDateField("Datum");
		
		priceField = new POJOAttributeTextField<Transaction>("Pris", Transaction.class, "price");
		
		feeField = new POJOAttributeTextField<Transaction>("Avgift", Transaction.class, "fee");
		
		numberField = new POJOAttributeTextField<Transaction>("Antal", Transaction.class, "number");
	}

	@Override
	public void showStocksInSelection(List<Stock> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected SingleTransactionPresenter createPresenter() {
		// TODO.  This could possibly be refactored to ViewImpl
		return new SingleTransactionPresenter(this);
	}

	@Override
 	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		presenter.provideAllStocks();
		
		bindToData();
	}

	private void bindToData() {
		Transaction trans = new Transaction();
		
		// TODO Bind stock
		// TODO bind date
		priceField.bindToData(trans);
//		numberField.bindToData(trans);
		feeField.bindToData(trans);
		// TODO Auto-generated method stub
		
	}


}
