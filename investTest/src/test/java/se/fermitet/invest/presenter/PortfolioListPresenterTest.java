package se.fermitet.invest.presenter;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.model.PortfolioModel;
import se.fermitet.invest.viewinterface.ListView;

public class PortfolioListPresenterTest extends ListPresenterTest<PortfolioListPresenter, Portfolio, PortfolioModel, ListView<Portfolio>>{

	public PortfolioListPresenterTest() {
		super(ListView.class, Portfolio.class);
	}

	@Override
	protected PortfolioListPresenter createPresenter(ListView<Portfolio> view) {
		return new TestPortfolioListPresenter(view);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeletePortfolioWithAssociatedTransactions() throws Exception {
		Portfolio okPortfolio = new Portfolio("OK");
		Portfolio errorPortfolio = new Portfolio("ERROR");
		
		doThrow(new ModelException(ModelExceptionType.DUMMY)).when(mockedModel).delete(errorPortfolio);
		
		presenter.onDeleteButtonClick(okPortfolio);
		
		verify(mockedView, never()).displayApplicationException(anyObject());
		reset(mockedView);
		
		presenter.onDeleteButtonClick(errorPortfolio);
		verify(mockedView).displayApplicationException(new ModelException(ModelExceptionType.DUMMY));
		reset(mockedView);
		
		presenter.onDeleteButtonClick(okPortfolio);
		verify(mockedView).clearApplicationException();
		reset(mockedView);
	}

}

class TestPortfolioListPresenter extends PortfolioListPresenter {
	public TestPortfolioListPresenter(ListView<Portfolio> view) {
		super(view);
	}
	
	@Override
	protected PortfolioModel createModel() {
		return mock(PortfolioModel.class);
	}
}

