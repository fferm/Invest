package se.fermitet.invest.webui.views;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.joda.time.LocalDate;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.TransactionListPresenter;
import se.fermitet.invest.testData.TransactionDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class TransactionListViewImplTest extends ListViewImplTest<TransactionListViewImpl, TransactionListPresenter, Transaction> {
	@Override
	protected TransactionListViewImpl createViewImpl() {
		return new TestTransactionListViewImpl();
	}

	@Override
	protected void initTestData() {
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
	
	@Override
	protected String getSingleViewName() {
		return InvestWebUI.SINGLETRANSACTIONVIEW;
	}
}

@SuppressWarnings("serial")
class TestTransactionListViewImpl extends TransactionListViewImpl {
	TestTransactionListViewImpl() {
		super();
	}
	
	@Override
	protected TransactionListPresenter createPresenter() {
		return mock(TransactionListPresenter.class);
	}

	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}

