package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.TransactionListPresenter;
import se.fermitet.invest.testData.TransactionDataProvider;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class TransactionListViewImplTest {
	private TransactionListViewImpl view;
	private List<Transaction> testDataUnsorted;
	private List<Transaction> testDataSorted;
	private TransactionListPresenter mockedPresenter;

	@Before
	public void setUp() {
		view = new TestTransactionListViewImpl();
		mockedPresenter = view.presenter;

		initTestData();
		
		view.displayData(testDataUnsorted);
	}

	private void initTestData() {
		TransactionDataProvider provider = new TransactionDataProvider();
		
		testDataUnsorted = provider.getTestData();

		testDataSorted = new ArrayList<Transaction>(testDataUnsorted);
		testDataSorted.sort((Transaction o1, Transaction o2) -> {
			String o1Symbol = o1.getStock().getSymbol();
			String o2Symbol = o2.getStock().getSymbol();
			int compareSymbols = o1Symbol.compareTo(o2Symbol);
			if (compareSymbols != 0) return compareSymbols;
			
			LocalDate o1Date = o1.getDate();
			LocalDate o2Date = o2.getDate();
			return o1Date.compareTo(o2Date);
		});

	}

	@Test
	public void testEnterCallsFillStocks() throws Exception {
		view.enter(null);
	
		verify(mockedPresenter).fillViewWithData();
	}
	
	@Test
	public void testCallingDisplayDataDisplaysData() throws Exception {
		view.displayData(testDataUnsorted);
		List<Transaction> displayedData = view.tableAdapter.getData();
		assertArrayEquals(testDataSorted.toArray(), displayedData.toArray());  // Sorting first by symbol, then by date
	}
	

	


}

@SuppressWarnings("serial")
class TestTransactionListViewImpl extends TransactionListViewImpl {
	@Override
	protected TransactionListPresenter createPresenter() {
		return mock(TransactionListPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}

