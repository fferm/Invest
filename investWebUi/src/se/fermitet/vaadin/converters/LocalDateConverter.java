package se.fermitet.vaadin.converters;

import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;

public class LocalDateConverter extends AbstractConverter<Date, LocalDate> {
	private static final long serialVersionUID = -3288012479071159027L;

	public LocalDateConverter() {
		super(Date.class, LocalDate.class);
	}

	@Override
	public LocalDate convertToModel(Date value, Class<? extends LocalDate> targetType, Locale locale) throws ConversionException {
		return LocalDate.fromDateFields(value);
	}

	@Override
	public Date convertToPresentation(LocalDate value, Class<? extends Date> targetType, Locale locale)	throws ConversionException {
		return value.toDate();
	}
}