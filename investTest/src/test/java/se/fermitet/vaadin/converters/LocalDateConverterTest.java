package se.fermitet.vaadin.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

public class LocalDateConverterTest extends AbstractConverterTest<Date, LocalDate, LocalDateConverter> {

	@Override
	protected LocalDateConverter createConverter() {
		return new LocalDateConverter();
	}

	@Override
	protected Class<LocalDate> getExpectedModelClass() {
		return LocalDate.class;
	}

	@Override
	protected Class<Date> getExpectedPresentationClass() {
		return Date.class;
	}

	@Override
	protected List<AbstractConverterTest<Date, LocalDate, LocalDateConverter>.ConverterTestCase> getTestCases() {
		List<ConverterTestCase> testCases = new ArrayList<AbstractConverterTest<Date,LocalDate,LocalDateConverter>.ConverterTestCase>();

		LocalDate date = LocalDate.now();
		
		testCases.add(new ConverterTestCase(date, date.toDate()));
		
		return testCases;
	}
}
