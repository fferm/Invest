package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.QuoteModel;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.ListView;

public class QuoteListPresenterTest extends ListPresenterTest<QuoteListPresenter, Quote, QuoteModel, ListView<Quote>> {

	public QuoteListPresenterTest() {
		super(ListView.class, Quote.class);
	}

	@Override
	protected QuoteListPresenter createPresenter(ListView<Quote> view) {
		return new TestQuoteListPresenter(view);
	}
	
	@Test
	public void testGetStockByIdCallsStockModel() throws Exception {
		String stockId = UUID.randomUUID().toString();
		
		this.presenter.getStockById(stockId);
		
		verify(this.presenter.stockModel).getById(UUID.fromString(stockId));
	}
	
	@Test
	public void testGetQuotesByStockCallsModel() throws Exception {
		Stock stock = new Stock("TEST", "Test");
		
		this.presenter.getQuotesByStock(stock);
		
		verify(mockedModel).getQuotesByStock(stock);
	}

}

class TestQuoteListPresenter extends QuoteListPresenter {
	public TestQuoteListPresenter(ListView<Quote> view) {
		super(view);
	}
	
	@Override
	protected QuoteModel createModel() {
		return mock(QuoteModel.class);
	}
	
	@Override
	protected StockModel createStockModel() {
		return mock(StockModel.class);
	}
}

