package se.fermitet.invest.webui.views;

import java.util.List;

import org.joda.money.Money;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.SingleTransactionPresenter;
import se.fermitet.invest.viewinterface.SingleTransactionView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.POJOComboBoxAdapter;
import se.fermitet.vaadin.widgets.POJOPropertyDatePopupAdapter;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class SingleTransactionViewImpl extends SinglePOJOViewImpl<SingleTransactionPresenter, Transaction> implements SingleTransactionView {

	private static final long serialVersionUID = 8004896867328107503L;
	
	POJOComboBoxAdapter<Stock> stockComboAdapter;
	POJOPropertyDatePopupAdapter<Transaction> dateAdapter;
	POJOPropertyTextFieldAdapter<Transaction, Money> priceFieldAdapter;
	POJOPropertyTextFieldAdapter<Transaction, Money> feeFieldAdapter;
	POJOPropertyTextFieldAdapter<Transaction, Integer> numberFieldAdapter;
	private Label titleLabel;
	
	Button okButton;
	Button cancelButton;

	@Override
	protected Component createMainLayout() {
		FormLayout mainLayout = new FormLayout();
		
		initFields();
		
		mainLayout.addComponent(titleLabel);
		mainLayout.addComponent(stockComboAdapter.getUI());
		mainLayout.addComponent(dateAdapter.getUI());
		mainLayout.addComponent(numberFieldAdapter.getUI());
		mainLayout.addComponent(priceFieldAdapter.getUI());
		mainLayout.addComponent(feeFieldAdapter.getUI());
		
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.addComponent(okButton);
		buttonPanel.addComponent(cancelButton);

		mainLayout.addComponent(buttonPanel);

		return mainLayout;
	}

	private void initFields() {
		titleLabel = new Label("Aff�r");
		
		stockComboAdapter = new POJOComboBoxAdapter<Stock>(Stock.class, "Aktie");
		stockComboAdapter.setDisplayColumn("symbol");
		stockComboAdapter.setSortOrder("symbol");
		stockComboAdapter.getUI().addValueChangeListener(e -> valueChanged());
		
		dateAdapter = new POJOPropertyDatePopupAdapter<Transaction>(Transaction.class, "Datum");
		dateAdapter.getUI().addValueChangeListener(e -> valueChanged());
		
		priceFieldAdapter = new POJOPropertyTextFieldAdapter<Transaction, Money>(Transaction.class, "Pris");
		priceFieldAdapter.getUI().addValueChangeListener(e -> valueChanged());
		
		feeFieldAdapter = new POJOPropertyTextFieldAdapter<Transaction, Money>(Transaction.class, "Avgift");
		feeFieldAdapter.getUI().addValueChangeListener(e -> valueChanged());
		
		numberFieldAdapter = new POJOPropertyTextFieldAdapter<Transaction, Integer>(Transaction.class, "Antal");
		numberFieldAdapter.getUI().addValueChangeListener(e -> valueChanged());

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
	public void showStocksInSelection(List<Stock> list) {
		stockComboAdapter.setData(list);
	}
	
	private void valueChanged() {
		okButton.setEnabled(isValid());
	}



	@Override
	protected SingleTransactionPresenter createPresenter() {
		// TODO.  This could possibly be refactored to ViewImpl
		return new SingleTransactionPresenter(this);
	}

	@Override
 	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		presenter.provideAllStocks();
		
		super.enter(event, parameters);
	}

	@Override
	protected void bindToData() {
		if (this.pojo == null) return;

		stockComboAdapter.bindSelectionToProperty(pojo, "stock");
		dateAdapter.bindToProperty(pojo, "date");
		priceFieldAdapter.bindToProperty(pojo, "price");
		numberFieldAdapter.bindToProperty(pojo, "number");
		feeFieldAdapter.bindToProperty(pojo, "fee");
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
