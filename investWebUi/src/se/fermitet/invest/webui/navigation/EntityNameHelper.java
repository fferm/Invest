package se.fermitet.invest.webui.navigation;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;


public class EntityNameHelper {

	public static String entityNameFor(Class<?> clz) {
		if (clz.equals(Stock.class)) return "stock";
		else if (clz.equals(Quote.class)) return "quote";
		else if (clz.equals(Transaction.class)) return "transaction";
		else if (clz.equals(Portfolio.class)) return "portfolio";
		
		else throw new EntityNameHelperException("Unknown class: " + clz.getName());
	}

}
