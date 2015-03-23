package se.fermitet.invest.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ModelExceptionTest {
	
	@Test
	public void testCannotDeleteStocSinceItHasAssociatedTransactions() throws Exception {
		assertNotNull(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS);
		assertTrue(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS.equals(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS));
		assertFalse(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS.equals(new ModelException("DUMMY")));
	}
}
