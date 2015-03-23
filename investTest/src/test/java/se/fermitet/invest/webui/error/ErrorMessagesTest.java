package se.fermitet.invest.webui.error;

import static org.junit.Assert.*;

import org.junit.Test;

import se.fermitet.invest.model.ModelException;

public class ErrorMessagesTest {
	@Test
	public void testCannotDeleteStockSinceItHasAssociatedTransactions() throws Exception {
		assertEquals("Denna aktie kan ej tas bort eftersom den har tillhörande affärer", ErrorMessages.getMessage(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS));
	}
}
