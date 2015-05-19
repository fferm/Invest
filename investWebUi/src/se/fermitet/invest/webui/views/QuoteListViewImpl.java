package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.QuoteListPresenter;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.invest.webui.navigation.EntityNameHelper;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.ColumnDefinition;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

public class QuoteListViewImpl extends ListViewImpl<QuoteListPresenter, Quote> {

	private static final long serialVersionUID = 5301419382984303433L;
	private Stock stock;

	public QuoteListViewImpl() {
		super(Quote.class, "Kurser");
	}

	@Override
	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		String stockId = parameters.get(0).getValue();
		this.stock = presenter.getStockById(stockId);
		
		List<Quote> quotes = presenter.getQuotesByStock(this.stock);
		this.displayData(quotes);
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
		return InvestWebUI.QUOTE_SINGLE;
	}

	@Override
	protected QuoteListPresenter createPresenter() {
		return new QuoteListPresenter(this);
	}

	public Stock getStock() {
		return this.stock;
	}
	
	@Override
	protected void navigateToSingleViewForNew() {
		getNavigator().navigateTo(getSingleViewName(), new URIParameter(EntityNameHelper.entityNameFor(Stock.class), getStock().getId().toString()));
	}

}
