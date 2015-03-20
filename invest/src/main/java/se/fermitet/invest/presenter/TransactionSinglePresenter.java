package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.Models;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.TransactionSingleView;


public class TransactionSinglePresenter extends POJOSinglePresenter<TransactionSingleView, Transaction, TransactionModel> {

	protected StockModel stocksModel;

	public TransactionSinglePresenter(TransactionSingleView view) {
		super(view, TransactionModel.class, Transaction.class);
		
		this.stocksModel = createStocksModel();
	}

	public void provideAllStocks() {
		this.view.showStocksInSelection(stocksModel.getAll());
	}
	
	protected StockModel createStocksModel() {
		return (StockModel) Models.fromClass(StockModel.class);
	}

}
