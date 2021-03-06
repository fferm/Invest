package se.fermitet.vaadin.converters;

import java.util.Date;
import java.util.Locale;

import org.joda.time.LocalDate;

public class LocalDateVaadinConverter extends AbstractVaadinConverter<Date, LocalDate> {
	private static final long serialVersionUID = -3288012479071159027L;

	public LocalDateVaadinConverter() {
		super(Date.class, LocalDate.class);
	}

	@Override
	public LocalDate convertToModel(Date value, Class<? extends LocalDate> targetType, Locale locale) throws ConversionException {
		if (value == null) return null;
		return LocalDate.fromDateFields(value);
	}

	@Override
	public Date convertToPresentation(LocalDate value, Class<? extends Date> targetType, Locale locale)	throws ConversionException {
		if (value == null) return null;
		return value.toDate();
	}
}