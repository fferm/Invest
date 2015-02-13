package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Transaction;



public class TransactionModel extends Model<Transaction> {
	TransactionModel() {
		super();
	}

	@Override
	public List<Transaction> getAll() {
		return storage.getAllTransactions();
	}

	@Override
	public void delete(Transaction toDelete) {
		storage.deleteTransaction(toDelete);
	}

	@Override
	public void save(Transaction toSave) {
		storage.saveTransaction(toSave);
	}

	@Override
	public Transaction getById(UUID id) {
		return storage.getTransactionById(id);
	}
	
}
