package se.fermitet.invest.webui.error;

import static org.junit.Assert.*;

import org.junit.Test;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class ErrorMessagesTest {
	@Test
	public void testCannotDeleteStockSinceItHasAssociatedTransactions() throws Exception {
		Stock stock = new Stock("TEST");
		assertEquals("Aktien " + stock.getSymbol() + " kan ej tas bort eftersom den har tillh�rande aff�rer", 
				ErrorMessages.getMessage(new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, stock)));
	}
}
