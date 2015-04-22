package se.fermitet.vaadin.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

public class LocalDateVaadinConverterTest extends AbstractVaadinConverterTest<Date, LocalDate, LocalDateVaadinConverter> {

	@Override
	protected LocalDateVaadinConverter createConverter() {
		return new LocalDateVaadinConverter();
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
	protected List<AbstractVaadinConverterTest<Date, LocalDate, LocalDateVaadinConverter>.VaadinConverterTestCase> getTestCases() {
		List<VaadinConverterTestCase> testCases = new ArrayList<AbstractVaadinConverterTest<Date,LocalDate,LocalDateVaadinConverter>.VaadinConverterTestCase>();

		LocalDate date = LocalDate.now();
		
		testCases.add(new VaadinConverterTestCase(date, date.toDate()));
		
		return testCases;
	}
}
