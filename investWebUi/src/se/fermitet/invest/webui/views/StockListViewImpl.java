package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter.StockListView;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class StockListViewImpl extends VerticalLayout implements StockListView {

	private static final long serialVersionUID = 1037623075906362499L;
	Table stockTable;

	static final String CODE_PROP_ID = "symbol";
	static final String NAME_PROP_ID = "name";

	public StockListViewImpl() {
		super();
		
		init();
	}
	
	private void init() {
		setMargin(true);
		
		stockTable = new Table("Aktier");

		stockTable.addContainerProperty(CODE_PROP_ID, String.class, null);
		stockTable.addContainerProperty(NAME_PROP_ID, String.class, null);
		stockTable.setColumnHeader(CODE_PROP_ID, "Symbol");
		stockTable.setColumnHeader(NAME_PROP_ID, "Namn");

		addComponent(stockTable);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		stockTable.removeAllItems();
		for (Stock stock: stocks) {
			stockTable.addItem(new Object[] {stock.getSymbol(), stock.getName()}, stock.getSymbol());
		}
	}

}
