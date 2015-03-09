package se.fermitet.invest.presenter;

import java.util.UUID;

import se.fermitet.invest.domain.Transaction;
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
		this.view.showStocksInSelection(stocksModel.getAll());
	}
	
	public Transaction getTransactionBasedOnIdString(String idString) {
		if (idString == null || idString.length() == 0) {
			return new Transaction();
		} else {
			UUID id = UUID.fromString(idString);
			
			return model.getById(id);
		}
	}
	
	public void onCancelButtonClick() {
		view.navigateBack();
	}

	public void onOkButtonClick(Transaction trans) {
		model.save(trans);
		view.navigateBack();
	}



	protected StockModel createStocksModel() {
		return (StockModel) Models.fromClass(StockModel.class);
	}

}
