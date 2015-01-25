package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter.StockListView;
import se.fermitet.vaadin.widgets.POJOTableAdapter;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class StockListViewImpl extends VerticalLayout implements StockListView {

	private static final long serialVersionUID = 1037623075906362499L;
	POJOTableAdapter<Stock> stockTableAdapter;
	Table stockTable;

	static final String CODE_PROP_ID = "symbol";
	static final String NAME_PROP_ID = "name";

	public StockListViewImpl() {
		super();
		
		init();
	}
	
	private void init() {
		setMargin(true);
		
		stockTableAdapter = new POJOTableAdapter<Stock>(Stock.class, "Aktier");
		stockTable = stockTableAdapter.getTable();
		
		stockTableAdapter.addColumn("getSymbol", "Symbol");
		stockTableAdapter.addColumn("getName", "Namn");

		stockTable.setSelectable(true);
		stockTable.setImmediate(true);
		
		addComponent(stockTable);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		stockTableAdapter.setdData(stocks);
	}

}
