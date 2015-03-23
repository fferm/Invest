package se.fermitet.invest.webui.error;

import se.fermitet.invest.model.ModelException;

public class ErrorMessages {

	public static String getMessage(ModelException exception) {
		if(exception.equals(ModelException.CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS)) 
			return "Denna aktie kan ej tas bort eftersom den har tillhörande affärer";
		
		return "Okänt fel";
	}

}
