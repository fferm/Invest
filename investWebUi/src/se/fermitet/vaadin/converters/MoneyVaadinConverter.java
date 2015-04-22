package se.fermitet.vaadin.converters;

import java.util.Locale;

import org.joda.money.Money;

import se.fermitet.invest.converter.MoneyConverter;

public class MoneyVaadinConverter extends AbstractVaadinConverter<String, Money> {
	private static final long serialVersionUID = -6251536302559035538L;
	
	private MoneyConverter converter;

	public MoneyVaadinConverter() {
		super(String.class, Money.class);

		this.converter = new MoneyConverter();
	}

	@Override
	public Money convertToModel(String value, Class<? extends Money> targetType, Locale locale)	throws ConversionException {
		return converter.moneyFromString(value);
	}

	@Override
	public String convertToPresentation(Money value, Class<? extends String> targetType, Locale locale)	throws ConversionException {
		return converter.stringFromMoney(value);
	}
}

