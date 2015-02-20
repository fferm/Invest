package se.fermitet.invest.viewinterface;

import java.util.List;

import se.fermitet.invest.domain.Transaction;

public interface TransactionListView {
	
	public void displayData(List<Transaction> data);

}
