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

		if (type.equals(ModelExceptionType.CANNOT_SAVE_STOCK_SINCE_THERE_IS_ALREADY_A_STOCK_WITH_THAT_SYMBOL))
			return "Kan ej spara en aktie med ticker " + ((Stock) exception.getContext().get(0)).getSymbol() + " eftersom det redan finns en sådan";

		if (type.equals(ModelExceptionType.CANNOT_SAVE_PORTFOLIO_SINCE_THERE_IS_ALREADY_A_PORTFOLIO_WITH_THAT_NAME))
			return "Kan ej spara en portfölj med namnet " + ((Portfolio) exception.getContext().get(0)).getName() + " eftersom det redan finns en sådan";

		return "Okänt fel";
	}

}
