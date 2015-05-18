package se.fermitet.invest.webui.views;

import static org.mockito.Mockito.*;

import java.util.Comparator;
import java.util.List;

import org.joda.time.LocalDate;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.TransactionListPresenter;
import se.fermitet.invest.testData.TestDataProvider;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class TransactionListViewImplTest extends ListViewImplTest<TransactionListViewImpl, TransactionListPresenter, Transaction> {

	public TransactionListViewImplTest() {
		super(Transaction.class);
	}

	@Override
	protected TransactionListViewImpl createViewImpl() {
		return new TestTransactionListViewImpl();
	}
	
	@Override
	protected List<Transaction> getTestData() {
		return new TestDataProvider().getTransactions();
	}

	@Override
	protected Comparator<? super Transaction> getComparator() {
		return (Transaction o1, Transaction o2) -> {
			String o1Port = o1.getPortfolio().getName();
			String o2Port = o2.getPortfolio().getName();
			int comparePortfolios = o1Port.compareTo(o2Port);
			if (comparePortfolios != 0) return comparePortfolios;
			
			String o1Symbol = o1.getStock().getSymbol();
			String o2Symbol = o2.getStock().getSymbol();
			int compareSymbols = o1Symbol.compareTo(o2Symbol);
			if (compareSymbols != 0) return compareSymbols;
			
			LocalDate o1Date = o1.getDate();
			LocalDate o2Date = o2.getDate();
			return o1Date.compareTo(o2Date);
		};
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.TRANSACTION_SINGLE;
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

