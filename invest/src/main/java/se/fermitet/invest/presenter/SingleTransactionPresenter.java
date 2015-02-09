package se.fermitet.invest.presenter;

import se.fermitet.invest.model.Models;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.SingleTransactionView;


public class SingleTransactionPresenter extends Presenter<SingleTransactionView, TransactionModel>{

	protected StocksModel stocksModel;

	public SingleTransactionPresenter(SingleTransactionView view) {
		super(view, TransactionModel.class);
		
		this.stocksModel = createStocksModel();
	}

	public void provideAllStocks() {
		this.view.showStocksInSelection(stocksModel.getAllStocks());
	}

	protected StocksModel createStocksModel() {
		return (StocksModel) Models.fromClass(StocksModel.class);
	}

}
