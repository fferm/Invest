package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.ListView;

public class TransactionListPresenter extends ListPresenter<ListView<Transaction>, Transaction, TransactionModel>{

	public TransactionListPresenter(ListView<Transaction> view) {
		super(view, TransactionModel.class);
	}
}
