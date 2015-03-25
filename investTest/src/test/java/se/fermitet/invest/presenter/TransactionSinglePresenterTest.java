package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.model.PortfolioModel;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.model.TransactionModel;
import se.fermitet.invest.viewinterface.TransactionSingleView;

public class TransactionSinglePresenterTest extends POJOSinglePresenterTest<TransactionSinglePresenter, Transaction, TransactionModel, TransactionSingleView> {

	private StockModel mockedStocksModel;
	private PortfolioModel mockedPortfolioModel;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.mockedStocksModel = presenter.stocksModel;
		this.mockedPortfolioModel = presenter.portfolioModel;
	}

	public TransactionSinglePresenterTest() {
		super(TransactionSingleView.class, Transaction.class);
	}

	@Override
	protected TransactionSinglePresenter createPresenter(TransactionSingleView view) {
		return new TestSingleTransactionPresenter((TransactionSingleView) view);
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
	public void testProvideAllPortfolios() throws Exception {
		List<Portfolio> list = new ArrayList<Portfolio>();
		when(mockedPortfolioModel.getAll()).thenReturn(list);
	
		presenter.provideAllPortfolios();
		
		verify(mockedPortfolioModel).getAll();
		verify(mockedView).showPortfoliosInSelection(list);
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
		assertNull(obj.getPortfolio());
	}
}

class TestSingleTransactionPresenter extends TransactionSinglePresenter {
	public TestSingleTransactionPresenter(TransactionSingleView view) {
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
	
	@Override
	protected PortfolioModel createPortfolioModel() {
		return mock(PortfolioModel.class);
	}
	
}
	

