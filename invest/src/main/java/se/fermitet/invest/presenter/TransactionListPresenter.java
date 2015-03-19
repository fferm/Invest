package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.TransactionListView;

public class TransactionListPresenter extends ListPresenter<TransactionListView, Transaction, TransactionModel>{

	public TransactionListPresenter(TransactionListView view) {
		super(view, TransactionModel.class);
	}
}
