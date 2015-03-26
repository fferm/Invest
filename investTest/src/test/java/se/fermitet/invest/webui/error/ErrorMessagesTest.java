package se.fermitet.invest.webui.error;

import static org.junit.Assert.*;

import org.junit.Test;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class ErrorMessagesTest {
	@Test
	public void testCannotDeleteStockSinceItHasAssociatedTransactions() throws Exception {
		Stock stock = new Stock("TEST");
		
		String expected = "Aktien " + stock.getSymbol() + " kan ej tas bort eftersom den har tillhörande affärer";
		String actual = ErrorMessages.getMessage(new ModelException(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, stock));

		assertEquals(expected, actual);
	}
	
	@Test
	public void testCannotDeletePortfolioSinceItHasAssociatedTransactions() throws Exception {
		Portfolio portfolio = new Portfolio("TEST");
		
		String expected = "Portföljen " + portfolio.getName() + " kan ej tas bort eftersom den har tillhörande affärer";
		String actual = ErrorMessages.getMessage(new ModelException(ModelExceptionType.CANNOT_DELETE_PORTFOLIO_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS, portfolio));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCannotSaveStockSinceThereIsAlreadyAStockWithThatSymbol() throws Exception {
		Stock stock = new Stock("TEST");
		
		String expected = "Kan ej spara en aktie med ticker " + stock.getSymbol() + " eftersom det redan finns en sådan";
		String actual = ErrorMessages.getMessage(new ModelException(ModelExceptionType.CANNOT_SAVE_STOCK_SINCE_THERE_IS_ALREADY_A_STOCK_WITH_THAT_SYMBOL, stock));

		assertEquals(expected, actual);
		
	}

}
