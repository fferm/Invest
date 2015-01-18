package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter.StockListView;

import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class StockListViewImpl extends VerticalLayout implements StockListView {

	private static final long serialVersionUID = 1037623075906362499L;
	private Table stockTable;

	public StockListViewImpl() {
		super();
		
		init();
	}
	
	private void init() {
		setMargin(true);
		
		stockTable = new Table("Aktier");
		String codePropertyId = "symbol";
		String namePropertyId = "name";

		stockTable.addContainerProperty(codePropertyId, String.class, null);
		stockTable.addContainerProperty(namePropertyId, String.class, null);
		stockTable.setColumnHeader(codePropertyId, "Symbol");
		stockTable.setColumnHeader(namePropertyId, "Namn");

		addComponent(stockTable);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		for (Stock stock: stocks) {
			stockTable.addItem(new Object[] {stock.getSymbol(), stock.getName()}, stock.getSymbol());
		}
	}

}
