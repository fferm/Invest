package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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

	@Test
	public void testDeleteFromViewCallsModelDelete() throws Exception {
		Transaction toDelete = new Transaction();
		List<Transaction> list = new ArrayList<Transaction>();
		when(mockedModel.getAll()).thenReturn(list);
		
		presenter.onDeleteButtonClick(toDelete);
		
		verify(mockedModel).delete(toDelete);
		verify(mockedView).displayData(list);
	}
	
	@Test
	public void testNewButtonClickCallsEditSingle() throws Exception {
		presenter.onNewButtonClick();
		
		verify(mockedView).navigateToSingleTransactionView(null);
	}
	
	@Test
	public void testEditButtonClickCallsEditSingleStock() throws Exception {
		Transaction editObj = new Transaction();
		presenter.onEditButtonClick(editObj);

		verify(mockedView).navigateToSingleTransactionView(editObj);
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

