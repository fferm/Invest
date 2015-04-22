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
		cols.add(new ColumnDefinition("bid", "K�p"));
		cols.add(new ColumnDefinition("ask", "S�lj"));
		cols.add(new ColumnDefinition("last", "Senast"));
		cols.add(new ColumnDefinition("high", "H�gst"));
		cols.add(new ColumnDefinition("low", "L�gst"));
		cols.add(new ColumnDefinition("volume", "Antal"));
		cols.add(new ColumnDefinition("turnover", "Oms�ttning"));
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
