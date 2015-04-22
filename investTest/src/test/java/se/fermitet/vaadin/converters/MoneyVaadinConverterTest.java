package se.fermitet.vaadin.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;

public class MoneyVaadinConverterTest extends AbstractVaadinConverterTest<String, Money, MoneyVaadinConverter> {

	@Override
	protected MoneyVaadinConverter createConverter() {
		return new MoneyVaadinConverter();
	}

	@Override
	protected Class<Money> getExpectedModelClass() {
		return Money.class;
	}

	@Override
	protected Class<String> getExpectedPresentationClass() {
		return String.class;
	}

	@Override
	protected List<AbstractVaadinConverterTest<String, Money, MoneyVaadinConverter>.VaadinConverterTestCase> getTestCases() {
		List<VaadinConverterTestCase> testCases = new ArrayList<AbstractVaadinConverterTest<String,Money,MoneyVaadinConverter>.VaadinConverterTestCase>();
		
		testCases.add(new VaadinConverterTestCase(Money.parse("SEK 1"), "1,00"));
		testCases.add(new VaadinConverterTestCase(Money.parse("SEK 1.25"), "1,25"));
		testCases.add(new VaadinConverterTestCase(Money.parse("SEK 1000.25"), "1 000,25"));
		testCases.add(new VaadinConverterTestCase(Money.parse("SEK 1000000.25"), "1 000 000,25"));
		testCases.add(new VaadinConverterTestCase(Money.parse("SEK -1"), "-1,00"));
		testCases.add(new VaadinConverterTestCase(Money.parse("SEK 9.81"), "9,81"));
		testCases.add(new VaadinConverterTestCase(null, null));
		
		
		return testCases;
	}

}
