package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter.StockListView;
import se.fermitet.vaadin.widgets.POJOTable;

import com.vaadin.ui.VerticalLayout;

public class StockListViewImpl extends VerticalLayout implements StockListView {

	private static final long serialVersionUID = 1037623075906362499L;
	POJOTable<Stock> stockTable;

	static final String CODE_PROP_ID = "symbol";
	static final String NAME_PROP_ID = "name";

	public StockListViewImpl() {
		super();
		
		init();
	}
	
	private void init() {
		setMargin(true);
		
		stockTable = new POJOTable<Stock>(Stock.class, "Aktier");
		stockTable.addColumn("getSymbol", "Symbol");
		stockTable.addColumn("getName", "Namn");

		addComponent(stockTable);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		stockTable.setDisplayedData(stocks);
	}

}
