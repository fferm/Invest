package se.fermitet.invest.presenter;

import se.fermitet.invest.model.Models;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.SingleTransactionView;


public class SingleTransactionPresenter extends Presenter<SingleTransactionView, TransactionModel>{

	protected StockModel stocksModel;

	public SingleTransactionPresenter(SingleTransactionView view) {
		super(view, TransactionModel.class);
		
		this.stocksModel = createStocksModel();
	}

	public void provideAllStocks() {
		this.view.showStocksInSelection(stocksModel.getAllStocks());
	}

	protected StockModel createStocksModel() {
		return (StockModel) Models.fromClass(StockModel.class);
	}

}
