package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.presenter.QuoteListPresenter;
import se.fermitet.vaadin.widgets.ColumnDefinition;

public class QuoteListViewImpl extends ListViewImpl<QuoteListPresenter, Quote> {

	private static final long serialVersionUID = 5301419382984303433L;

	public QuoteListViewImpl() {
		super(Quote.class, "Kurser");
	}


	@Override
	protected List<ColumnDefinition> getColumnDefinitions() {
		List<ColumnDefinition> cols = new ArrayList<ColumnDefinition>();
		cols.add(new ColumnDefinition("date", "Datum"));
		cols.add(new ColumnDefinition("bid", "Köp"));
		cols.add(new ColumnDefinition("ask", "Sälj"));
		cols.add(new ColumnDefinition("last", "Senast"));
		cols.add(new ColumnDefinition("high", "Högst"));
		cols.add(new ColumnDefinition("low", "Lägst"));
		cols.add(new ColumnDefinition("volume", "Antal"));
		cols.add(new ColumnDefinition("turnover", "Omsättning"));
		return cols;
	}

	@Override
	protected void setSortOrder() {
		tableAdapter.setSortOrder("date");
	}

	@Override
	protected String getSingleViewName() {
		throw new UnsupportedOperationException("unimplemented");
	}

	@Override
	protected QuoteListPresenter createPresenter() {
		throw new UnsupportedOperationException("unimplemented");
	}

}
