package se.fermitet.invest.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class ModelExceptionTest {
	
	@Test
	public void testCannotDeleteStocSinceItHasAssociatedTransactions() throws Exception {
		Stock s1 = new Stock("TEST");
		Stock s2 = new Stock("WRONG");
		
		ModelException fromS1 = new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS,  s1);
		ModelException fromS2 = new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS,  s2);

		assertNotNull(fromS1);
		assertTrue(fromS1.equals(fromS1));
		assertFalse(fromS1.equals(new ModelException(ModelExceptionType.DUMMY)));
		assertFalse(fromS1.equals(fromS2));
		
		assertEquals("ModelException: CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS  Context: " + s1.toString(), fromS1.toString());
	}
	
	@Test
	public void testGetType() throws Exception {
		Stock s1 = new Stock("TEST");
		
		ModelException fromS1 = new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS,  s1);
		
		assertEquals(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, fromS1.getType());
	}
	
	@Test
	public void testGetContext() throws Exception {
		Stock s1 = new Stock("TEST");
		
		ModelException fromS1 = new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS,  s1);

		List<Stock> expected = new ArrayList<Stock>();
		expected.add(s1);
		
		assertEquals(expected, fromS1.getContext());
	}
}
