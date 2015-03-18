package se.fermitet.invest.viewinterface;

import se.fermitet.invest.domain.Transaction;

public interface TransactionListView extends ListView<Transaction> {
	public void navigateToSingleTransactionView(Transaction data);

}
