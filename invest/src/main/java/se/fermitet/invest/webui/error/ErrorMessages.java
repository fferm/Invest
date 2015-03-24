package se.fermitet.invest.webui.error;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;

public class ErrorMessages {

	public static String getMessage(ModelException exception) {
		if (exception.getType().equals(ModelExceptionType.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS))
			return "Aktien " + ((Stock) exception.getContext().get(0)).getSymbol() + " kan ej tas bort eftersom den har tillh�rande aff�rer";
		
		return "Ok�nt fel";
	}

}
