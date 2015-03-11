package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.TransactionListView;

public class TransactionListPresenter extends Presenter<TransactionListView, Transaction, TransactionModel>{

	public TransactionListPresenter(TransactionListView view) {
		super(view, TransactionModel.class);
	}

	public void fillViewWithData() {
		this.view.displayData(model.getAll());
	}

	public void onNewButtonClick() {
		view.navigateToSingleTransactionView(null);
	}

	public void onEditButtonClick(Transaction selected) {
		view.navigateToSingleTransactionView(selected);
	}

	public void onDeleteButtonClick(Transaction selected) {
		model.delete(selected);
		view.displayData(model.getAll());
	}

}
