package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.TransactionListView;

public class TransactionListPresenterTest {
	private TransactionListView mockedView;
	private TransactionListPresenter presenter;
	private TransactionModel mockedModel;

	@Before
	public void setUp() throws Exception {
		mockedView = mock(TransactionListView.class);
		presenter = new TestTransactionListPresenter(mockedView);
		mockedModel = presenter.model;
		
		reset(mockedView);
		reset(mockedModel);
	}

	@Test
	public void testFillListWithAllTransactions() throws Exception {
		List<Transaction> list = new ArrayList<Transaction>();
		when(mockedModel.getAll()).thenReturn(list);
	
		presenter.fillViewWithData();
		
		verify(mockedModel).getAll();
		verify(mockedView).displayData(list);
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
	public void testNewButtonClickCallsEditSingleStock() throws Exception {
		fail("unimplemented");
//		presenter.onNewButtonClick();
//		
//		verify(mockedView).navigateToSingleStockView(null);
	}
	
	@Test
	public void testEditButtonClickCallsEditSingleStock() throws Exception {
		fail("unimplemented");
//		Stock editStock = new Stock("TST", "TST");
//		
//		presenter.onEditButtonClick(editStock);
//
//		verify(mockedView).navigateToSingleStockView(editStock);
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

