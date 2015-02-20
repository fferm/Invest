package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;

public class TransactionListPresenter extends Presenter<TransactionListPresenter, TransactionModel>{

	public TransactionListPresenter(TransactionListPresenter view) {
		super(view, TransactionModel.class);
	}

	public void fillViewWithData() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void onNewButtonClick() {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void onEditButtonClick(Transaction selected) {
		throw new UnsupportedOperationException("unimplemented");
	}

	public void onDeleteButtonClick(Transaction selected) {
		throw new UnsupportedOperationException("unimplemented");
	}

}
