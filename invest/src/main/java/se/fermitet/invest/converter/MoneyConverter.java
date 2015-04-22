package se.fermitet.invest.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;


public class MoneyConverter {
	private DecimalFormat formatter;
	private CurrencyUnit curr;

	public MoneyConverter() {
		super();
		
		formatter = (DecimalFormat) DecimalFormat.getInstance();
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
		symbols.setGroupingSeparator((char) 32);
		formatter.setDecimalFormatSymbols(symbols);
		formatter.setGroupingSize(3);
		formatter.setGroupingUsed(true);
		
		curr = CurrencyUnit.of("SEK");
	}
	
	public Money moneyFromString(String value) {
		try {
			if (value == null) return null;
			
			Number amountNumber = formatter.parse(value.replace((char) 160, (char) 32).replace(" ", ""));

			BigDecimal amount = null;
			
			if (amountNumber instanceof Long) amount = new BigDecimal((Long) amountNumber);
			if (amountNumber instanceof Double) amount = new BigDecimal((Double) amountNumber);

			amount = amount.setScale(curr.getDecimalPlaces(), RoundingMode.HALF_UP);

			if (amount != null) return Money.of(curr, amount);
			else return null;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String stringFromMoney(Money value) {
		if (value == null) return null;
		
		String presentationValue = formatter.format(value.getAmount());
		
		return presentationValue;
	}

}
