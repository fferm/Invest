package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.ListView;

public class TransactionListPresenterTest extends ListPresenterTest<TransactionListPresenter, Transaction, TransactionModel, ListView<Transaction>> {
	public TransactionListPresenterTest() {
		super(ListView.class, Transaction.class);
	}

	@Override
	protected TransactionListPresenter createPresenter(ListView<Transaction> view) {
		return new TestTransactionListPresenter(mockedView);
	}
}

class TestTransactionListPresenter extends TransactionListPresenter {
	public TestTransactionListPresenter(ListView<Transaction> view) {
		super(view);
	}
	
	@Override
	protected TransactionModel createModel() {
		return mock(TransactionModel.class);
	}
}

