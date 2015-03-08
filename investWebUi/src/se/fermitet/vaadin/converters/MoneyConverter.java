package se.fermitet.vaadin.converters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class MoneyConverter extends AbstractConverter<String, Money> {
	private static final long serialVersionUID = -6251536302559035538L;
	private DecimalFormat formatter;
	private CurrencyUnit curr;

	public MoneyConverter() {
		super(String.class, Money.class);

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

	@Override
	public Money convertToModel(String value, Class<? extends Money> targetType, Locale locale)	throws ConversionException {
		try {
			if (value == null) return null;
			
			Number amountNumber = formatter.parse(value.replace((char) 160, (char) 32).replace(" ", ""));

			BigDecimal amount = null;
			
			if (amountNumber instanceof Long) amount = new BigDecimal((long) amountNumber);
			if (amountNumber instanceof Double) amount = new BigDecimal((double) amountNumber);

			if (amount != null) return Money.of(curr, amount);
			else return null;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String convertToPresentation(Money value, Class<? extends String> targetType, Locale locale)	throws ConversionException {
		if (value == null) return null;
		
		String presentationValue = formatter.format(value.getAmount());
		
		return presentationValue;
	}
}

