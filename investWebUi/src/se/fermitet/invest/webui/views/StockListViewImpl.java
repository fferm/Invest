package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.widgets.ColumnDefinition;

public class StockListViewImpl extends ListViewImpl<StockListPresenter, Stock> {

	private static final long serialVersionUID = 1037623075906362499L;

	public StockListViewImpl() {
		super(Stock.class, "Aktier");
	}

	@Override
	protected StockListPresenter createPresenter() {
		return new StockListPresenter(this);
	}
	
	@Override
	protected List<ColumnDefinition> getColumnDefinitions() {
		List<ColumnDefinition> cols = new ArrayList<ColumnDefinition>();
		cols.add(new ColumnDefinition("symbol", "Ticker"));
		cols.add(new ColumnDefinition("name", "Namn"));
		
		return cols;
	}
	
	@Override
	protected void setSortOrder() {
		tableAdapter.setSortOrder("symbol");
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.STOCK_SINGLE;
	}
}
