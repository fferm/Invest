package se.fermitet.invest.webui.error;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class ErrorMessages {

	public static String getMessage(ModelException exception) {
		ModelExceptionType type = exception.getType();
		
		if (type.equals(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS))
			return "Aktien " + ((Stock) exception.getContext().get(0)).getSymbol() + " kan ej tas bort eftersom den har tillhörande affärer";

		if (type.equals(ModelExceptionType.CANNOT_DELETE_PORTFOLIO_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS))
			return "Portföljen " + ((Portfolio) exception.getContext().get(0)).getName() + " kan ej tas bort eftersom den har tillhörande affärer";

			
		return "Okänt fel";
	}

}
