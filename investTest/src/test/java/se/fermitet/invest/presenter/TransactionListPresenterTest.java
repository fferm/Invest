package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.TransactionListView;

public class TransactionListPresenterTest extends ListPresenterTest<TransactionListPresenter, Transaction, TransactionModel, TransactionListView> {
	public TransactionListPresenterTest() {
		super(TransactionListView.class, Transaction.class);
	}

	@Override
	protected TransactionListPresenter createPresenter(TransactionListView view) {
		return new TestTransactionListPresenter(mockedView);
	}
}

class TestTransactionListPresenter extends TransactionListPresenter {
	public TestTransactionListPresenter(TransactionListView view) {
		super(view);
	}
	
	@Override
	protected TransactionModel createModel() {
		return mock(TransactionModel.class);
	}
}

