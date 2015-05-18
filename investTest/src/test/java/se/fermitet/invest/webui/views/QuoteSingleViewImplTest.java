package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.money.Money;
import org.joda.time.LocalDate;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.QuoteSinglePresenter;
import se.fermitet.vaadin.converters.MoneyVaadinConverter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

public class QuoteSingleViewImplTest extends POJOSingleViewImplTest<QuoteSingleViewImpl, QuoteSinglePresenter, Quote> {

	public QuoteSingleViewImplTest() {
		super(Quote.class);
	}

	@Override
	protected QuoteSingleViewImpl createViewImpl() {
		return new TestQuoteSingleViewImpl();
	}

	@Override
	protected Quote getTestPojo() {
		Quote ret = new Quote();
		ret.setStock(new Stock("TEST", "Test"));
		ret.setAsk(Money.parse("SEK 100.5"));
		ret.setBid(Money.parse("SEK 100"));
		ret.setDate(LocalDate.now());
		ret.setHigh(Money.parse("SEK 105"));
		ret.setLast(Money.parse("SEK 100"));
		ret.setLow(Money.parse("SEK 99"));
		ret.setTurnover(Money.parse("SEK 100000"));
		ret.setVolume(1000);

		return ret;
	}

	@Override
	protected void checkUIAgainstPojo(Quote pojo) {
		MoneyVaadinConverter conv = new MoneyVaadinConverter();

		assertEquals("Date", pojo.getDate().toDate(), view.dateAdapter.getUI().getValue());

		if (pojo.getStock() == null || pojo.getStock().getSymbol() == null) 
			assertNull("Stock", view.stockComboAdapter.getUI().getValue());
		else
			assertEquals("Symbol", pojo.getStock().getSymbol(), view.stockComboAdapter.getUI().getItemCaption(view.stockComboAdapter.getUI().getValue()));
	}

	@Override
	protected void updateUIFromPOJO(Quote updated) {
		throw new UnsupportedOperationException("unimplemented");
	}

	@Override
	protected void makeUIDataInvalid() {
		throw new UnsupportedOperationException("unimplemented");
	}

	@Override
	protected void checkFieldValidity(boolean shouldBeValid) {
		throw new UnsupportedOperationException("unimplemented");
	}

}

@SuppressWarnings("serial")
class TestQuoteSingleViewImpl extends QuoteSingleViewImpl {
	@Override
	protected QuoteSinglePresenter createPresenter() {
		return mock(QuoteSinglePresenter.class);
	}
	
	@Override
	protected DirectionalNavigator createNavigator() {
		return mock(DirectionalNavigator.class);
	}
}

