package se.fermitet.vaadin.converters;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;

public class MoneyConverterTest extends AbstractConverterTest<String, Money, MoneyConverter> {

	@Override
	protected MoneyConverter createConverter() {
		return new MoneyConverter();
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
	protected List<AbstractConverterTest<String, Money, MoneyConverter>.ConverterTestCase> getTestCases() {
		List<ConverterTestCase> testCases = new ArrayList<AbstractConverterTest<String,Money,MoneyConverter>.ConverterTestCase>();
		
		testCases.add(new ConverterTestCase(Money.parse("SEK 1"), "1,00"));
		testCases.add(new ConverterTestCase(Money.parse("SEK 1.25"), "1,25"));
		testCases.add(new ConverterTestCase(Money.parse("SEK 1000.25"), "1 000,25"));
		testCases.add(new ConverterTestCase(Money.parse("SEK 1000000.25"), "1 000 000,25"));
		testCases.add(new ConverterTestCase(Money.parse("SEK -1"), "-1,00"));

		
		
		return testCases;
	}

}
