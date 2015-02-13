package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
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
	

