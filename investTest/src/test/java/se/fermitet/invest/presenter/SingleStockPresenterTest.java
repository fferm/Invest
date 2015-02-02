package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;

public class SingleStockPresenterTest {
	private SingleStockPresenter presenter;
	private StocksModel mockedModel;
	
	@Before
	public void setUp() throws Exception {
		this.presenter = new TestSingleStockPresenter();
		this.mockedModel = presenter.model;
	}
	
	@Test
	public void testGetStockWithNullArgument() throws Exception {
		Stock answer = presenter.getStockBasedOnIdString(null);
		
		assertNotNull(answer);
		assertNull(answer.getName());
		assertNull(answer.getSymbol());
		assertNotNull(answer.getId());
	}

	@Test
	public void testGetStockWithZeroLenghtArgument() throws Exception {
		Stock answer = presenter.getStockBasedOnIdString("");
		
		assertNotNull(answer);
		assertNull(answer.getName());
		assertNull(answer.getSymbol());
		assertNotNull(answer.getId());
	}
	
	@Test
	public void testGetStockWithData() throws Exception {
		UUID id = UUID.randomUUID();
		Stock expected = new Stock("TST").setName("TST");
		
		when(mockedModel.getStockById(id)).thenReturn(expected);
		
		Stock answer = presenter.getStockBasedOnIdString(id.toString());
		
		assertSame(expected, answer);
	}
}

class TestSingleStockPresenter extends SingleStockPresenter {
	@Override
	protected StocksModel createModel() {
		return mock(StocksModel.class);
	}
}
