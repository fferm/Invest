package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.SingleTransactionPresenter;
import se.fermitet.invest.viewinterface.SingleTransactionView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.POJOAttributeTextField;
import se.fermitet.vaadin.widgets.POJOComboBoxAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;

public class SingleTransactionViewImpl extends ViewImpl<SingleTransactionPresenter> implements SingleTransactionView {

	private static final long serialVersionUID = 8004896867328107503L;
	POJOComboBoxAdapter<Stock> stockComboAdapter;
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
		mainLayout.addComponent(stockComboAdapter.getCombo());
		mainLayout.addComponent(datePopup);
//		mainLayout.addComponent(numberField);
		mainLayout.addComponent(priceField);
		mainLayout.addComponent(feeField);
		
		return mainLayout;
	}

	private void initFields() {
		titleLabel = new Label("Affär");
		
		stockComboAdapter = new POJOComboBoxAdapter<Stock>(Stock.class);
		addTestData();
		
		datePopup = new PopupDateField("Datum");
		
		priceField = new POJOAttributeTextField<Transaction>("Pris", Transaction.class, "price");
		
		feeField = new POJOAttributeTextField<Transaction>("Avgift", Transaction.class, "fee");
		
		numberField = new POJOAttributeTextField<Transaction>("Antal", Transaction.class, "number");
	}
	
	
	private void addTestData() {
		List<Stock> data = new ArrayList<Stock>();
		data.add(new Stock("Handelsbanken", "SHB B"));
		data.add(new Stock("Axis", "AXIS"));
		data.add(new Stock("Transmode", "TRMO"));
		data.add(new Stock("Volvo B", "VOLV B"));
		data.add(new Stock("Svolder", "SVOL"));
//		data.add(new Stock("Null ticker", null));
//		data.add(new Stock(null, "NULL"));
		
		stockComboAdapter.setData(data);
		stockComboAdapter.setDisplayColumn("symbol");
		
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
