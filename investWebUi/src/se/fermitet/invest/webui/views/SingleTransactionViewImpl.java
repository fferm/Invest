package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.SingleTransactionPresenter;
import se.fermitet.invest.viewinterface.SingleTransactionView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.POJOComboBoxAdapter;
import se.fermitet.vaadin.widgets.POJOPropertyDatePopupAdapter;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;

public class SingleTransactionViewImpl extends ViewImpl<SingleTransactionPresenter> implements SingleTransactionView {

	private static final long serialVersionUID = 8004896867328107503L;
	POJOComboBoxAdapter<Stock> stockComboAdapter;
	POJOPropertyDatePopupAdapter<Transaction> dateAdapter;
	POJOPropertyTextFieldAdapter<Transaction> priceFieldAdapter;
	POJOPropertyTextFieldAdapter<Transaction> feeFieldAdapter;
	POJOPropertyTextFieldAdapter<Transaction> numberFieldAdapter;
	private Label titleLabel;
	private Transaction transaction;

	@Override
	protected Component createMainLayout() {
		FormLayout mainLayout = new FormLayout();
		
		initFields();
		
		mainLayout.addComponent(titleLabel);
		mainLayout.addComponent(stockComboAdapter.getCombo());
		mainLayout.addComponent(dateAdapter.getUI());
		mainLayout.addComponent(numberFieldAdapter.getUI());
		mainLayout.addComponent(priceFieldAdapter.getUI());
		mainLayout.addComponent(feeFieldAdapter.getUI());
		
		return mainLayout;
	}

	private void initFields() {
		titleLabel = new Label("Affär");
		
		stockComboAdapter = new POJOComboBoxAdapter<Stock>(Stock.class, "Aktie");
		stockComboAdapter.setDisplayColumn("symbol");
		stockComboAdapter.setSortOrder("symbol");
		
		dateAdapter = new POJOPropertyDatePopupAdapter<Transaction>(Transaction.class, "date", "Datum");
		
		priceFieldAdapter = new POJOPropertyTextFieldAdapter<Transaction>(Transaction.class, "price", "Pris");
		
		feeFieldAdapter = new POJOPropertyTextFieldAdapter<Transaction>(Transaction.class, "fee", "Avgift");
		
		numberFieldAdapter = new POJOPropertyTextFieldAdapter<Transaction>(Transaction.class, "number", "Antal");
	}
	
	
	@Override
	public void showStocksInSelection(List<Stock> list) {
		stockComboAdapter.setData(list);
	}

	@Override
	protected SingleTransactionPresenter createPresenter() {
		// TODO.  This could possibly be refactored to ViewImpl
		return new SingleTransactionPresenter(this);
	}

	@Override
 	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		presenter.provideAllStocks();
		
		if (parameters.size() == 0) this.transaction = presenter.getTransactionBasedOnIdString(null);
		else this.transaction = presenter.getTransactionBasedOnIdString(parameters.get(0).getValue());

		bindToData();
	}

	private void bindToData() {
		if (this.transaction == null) return;

		// TODO Bind stock
		dateAdapter.bindToData(transaction);
		priceFieldAdapter.bindToData(transaction);
		numberFieldAdapter.bindToData(transaction);
		feeFieldAdapter.bindToData(transaction);
	}


}
