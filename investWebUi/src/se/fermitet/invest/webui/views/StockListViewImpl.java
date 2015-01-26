package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter.StockListView;
import se.fermitet.vaadin.widgets.POJOTableAdapter;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class StockListViewImpl extends CustomComponent implements StockListView {

	private static final long serialVersionUID = 1037623075906362499L;
	
	POJOTableAdapter<Stock> stockTableAdapter;
	Table stockTable;
	private VerticalLayout mainLayout;

	private Button deleteButton;

	public StockListViewImpl() {
		super();
		
		init();
	}
	
	private void init() {
		this.mainLayout = new VerticalLayout();
		this.mainLayout.setMargin(true);

		initStockTable(mainLayout);
		initButtonPanel(mainLayout);
		
		setCompositionRoot(mainLayout);
	}

	private void initStockTable(Layout mainLayout) {
		stockTableAdapter = new POJOTableAdapter<Stock>(Stock.class, "Aktier");
		stockTable = stockTableAdapter.getTable();
		
		List<String> cols = new ArrayList<String>();
		cols.add("name");

		stockTableAdapter.setColumns(cols);
//		stockTableAdapter.addColumn("getSymbol", "Symbol");
//		stockTableAdapter.addColumn("getName", "Namn");
		
//		stockTableAdapter.addProperty("symbol");
		
		stockTable.setSelectable(true);
		stockTable.setImmediate(true);
		stockTable.setPageLength(10);
		
		mainLayout.addComponent(stockTable);
	}
	
	private void initButtonPanel(Layout mainLayout) {
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setMargin(new MarginInfo(true, false, true, false));
		
		initDeleteButton(buttonPanel);

		mainLayout.addComponent(buttonPanel);
	}


	private void initDeleteButton(Layout buttonPanel) {
		this.deleteButton = new Button("Ta bort");
		deleteButton.setEnabled(false);
		
//		deleteButton.addClickListener((Button.ClickListener) l -> {System.out.println("!!!! hallå");});
		
		buttonPanel.addComponent(deleteButton);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		stockTableAdapter.setdData(stocks);
	}

}
