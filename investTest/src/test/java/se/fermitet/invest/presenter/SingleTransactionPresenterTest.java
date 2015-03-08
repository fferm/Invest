package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.SingleTransactionView;

public class SingleTransactionPresenterTest {
	private SingleTransactionPresenter presenter;
	private TransactionModel mockedModel;
	private StockModel mockedStocksModel;
	private SingleTransactionView mockedView;
	
	@Before
	public void setUp() throws Exception {
		this.mockedView = mock(SingleTransactionView.class);
		this.presenter = new TestSingleTransactionPresenter(mockedView);
		this.mockedModel = presenter.model;
		this.mockedStocksModel = presenter.stocksModel;
	}
	
	@Test
	public void testProvideAllStocks() throws Exception {
		List<Stock> list = new ArrayList<Stock>();
		when(mockedStocksModel.getAll()).thenReturn(list);
	
		presenter.provideAllStocks();
		
		verify(mockedStocksModel).getAll();
		verify(mockedView).showStocksInSelection(list);
	}

	@Test
	public void testGetTransactionWithNullArgument() throws Exception {
		Transaction answer = presenter.getTransactionBasedOnIdString(null);
		
		assertNotNull(answer);
		assertNull(answer.getStock());
		assertEquals(LocalDate.now(), answer.getDate());
		assertNull(answer.getFee());
		assertNull(answer.getPrice());
		assertEquals(0, answer.getNumber());
		assertNotNull(answer.getId());
	}

	@Test
	public void testGetTransactionWithZeroLenghtArgument() throws Exception {
		Transaction answer = presenter.getTransactionBasedOnIdString("");
		
		assertNotNull(answer);
		assertNull(answer.getStock());
		assertEquals(LocalDate.now(), answer.getDate());
		assertNull(answer.getFee());
		assertNull(answer.getPrice());
		assertEquals(0, answer.getNumber());
		assertNotNull(answer.getId());
	}
	
	@Test
	public void testGetTransactionWithData() throws Exception {
		UUID id = UUID.randomUUID();
		Transaction expected = new Transaction();
		
		when(mockedModel.getById(id)).thenReturn(expected);
		
		Transaction answer = presenter.getTransactionBasedOnIdString(id.toString());
		
		assertSame(expected, answer);
	}
	
	@Test
	public void testCancelNavigatesToTransactionList() throws Exception {
		presenter.onCancelButtonClick();
		
		verify(mockedView).navigateBack();
	}
	
	@Test
	public void testOkSavesAndNavigatesBack() throws Exception {
		fail("unimplemented");
//		Stock testStock = new Stock("name", "symbol");
//		
//		presenter.onOkButtonClick(testStock);
//		
//		verify(mockedModel).save(testStock);
//		verify(mockedView).navigateBack();
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
	

