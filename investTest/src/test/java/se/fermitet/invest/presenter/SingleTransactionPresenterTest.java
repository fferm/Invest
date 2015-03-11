package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.SinglePOJOView;
import se.fermitet.invest.viewinterface.SingleTransactionView;

public class SingleTransactionPresenterTest extends SinglePOJOPresenterTest<SingleTransactionPresenter, Transaction, TransactionModel, SingleTransactionView> {

	private StockModel mockedStocksModel;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.mockedStocksModel = presenter.stocksModel;
	}

	public SingleTransactionPresenterTest() {
		super(SingleTransactionView.class, Transaction.class);
	}

	@Override
	protected SingleTransactionPresenter createPresenter(SinglePOJOView view) {
		return new TestSingleTransactionPresenter((SingleTransactionView) view);
	}

	
	@Test
	public void testProvideAllStocks() throws Exception {
		List<Stock> list = new ArrayList<Stock>();
		when(mockedStocksModel.getAll()).thenReturn(list);
	
		presenter.provideAllStocks();
		
		verify(mockedStocksModel).getAll();
		((SingleTransactionView) verify(mockedView)).showStocksInSelection(list);
	}

	@Override
	protected void assessDefaultDO(Transaction obj) {
		assertNotNull(obj);
		assertNull(obj.getStock());
		assertEquals(LocalDate.now(), obj.getDate());
		assertNull(obj.getFee());
		assertNull(obj.getPrice());
		assertEquals((Integer) 0, obj.getNumber());
		assertNotNull(obj.getId());
	}
	
	@Test
	public void testCancelNavigatesToTransactionList() throws Exception {
		presenter.onCancelButtonClick();
		
		((SingleTransactionView) verify(mockedView)).navigateBack();
	}
	
	@Test
	public void testOkSavesAndNavigatesBack() throws Exception {
		Transaction testData = new Transaction();
		
		presenter.onOkButtonClick(testData);
		
		verify(mockedModel).save(testData);
		((SingleTransactionView) verify(mockedView)).navigateBack();
	}
}

class TestSingleTransactionPresenter extends SingleTransactionPresenter {
	public TestSingleTransactionPresenter(SingleTransactionView view) {
		super(view);
	}

	@Override
	protected TransactionModel createModel() {
		return mock(TransactionModel.class);
	}
	
	@Override
	protected StockModel createStocksModel() {
		return mock(StockModel.class);
	}
	
}
	

