package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.viewinterface.StockListView;
import se.fermitet.invest.webui.IvestWebUI;
import se.fermitet.vaadin.widgets.ColumnDefinition;
import se.fermitet.vaadin.widgets.POJOTableAdapter;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class StockListViewImpl extends CustomComponent implements StockListView, View {

	private static final long serialVersionUID = 1037623075906362499L;
	
	StockListPresenter presenter;

	POJOTableAdapter<Stock> stockTableAdapter;
	Button deleteButton;
	Button editButton;
	Button newButton;
	Table stockTable;


	public StockListViewImpl() {
		super();

		this.presenter = createPresenter();
		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);

		initStockTable(mainLayout);
		initButtonPanel(mainLayout);
		
		setCompositionRoot(mainLayout);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		this.presenter.fillStockListWithAllStocks();
	}
	
	protected StockListPresenter createPresenter() {
		return new StockListPresenter(this);
	}

	private void initStockTable(Layout parent) {
		stockTableAdapter = new POJOTableAdapter<Stock>(Stock.class, "Aktier");
		stockTable = stockTableAdapter.getTable();
		
		List<ColumnDefinition> cols = new ArrayList<ColumnDefinition>();
		cols.add(new ColumnDefinition("symbol", "Ticker"));
		cols.add(new ColumnDefinition("name", "Namn"));

		stockTableAdapter.setColumns(cols);
		stockTableAdapter.addSelectionListener((Integer idx, Stock selectedStock) -> handleSelectionEvent(idx, selectedStock));
		
		stockTable.setSelectable(true);
		stockTable.setImmediate(true);
		stockTable.setPageLength(10);
		
		parent.addComponent(stockTable);
	}
	
	private void initButtonPanel(Layout parent) {
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.setMargin(new MarginInfo(true, false, true, false));
		
		initNewButton(buttonPanel);
		initEditButton(buttonPanel);
		initDeleteButton(buttonPanel);

		parent.addComponent(buttonPanel);
	}

	private void initNewButton(Layout parent) {
		this.newButton = new Button("L�gg till");
		
		newButton.addClickListener((Button.ClickListener) l -> this.presenter.onNewButtonClick());
		parent.addComponent(newButton);
	}

	private void initEditButton(Layout parent) {
		this.editButton = new Button("�ndra");
		editButton.setEnabled(false);
		
		editButton.addClickListener((Button.ClickListener) l -> {
			Stock selectedStock = this.stockTableAdapter.getSelectedData();
			this.presenter.onEditButtonClick(selectedStock);
		});
		
		parent.addComponent(editButton);
	}

	private void initDeleteButton(Layout parent) {
		this.deleteButton = new Button("Ta bort");
		deleteButton.setEnabled(false);
		
		deleteButton.addClickListener((Button.ClickListener) l -> {
			Stock selectedStock = this.stockTableAdapter.getSelectedData();
			this.presenter.onDeleteButtonClick(selectedStock);
		});
		
		parent.addComponent(deleteButton);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		stockTableAdapter.setData(stocks);
	}
	
	@Override
	public void editSingleStock(Stock stock) {
		IvestWebUI ui = (IvestWebUI) getUI();
		
		String withParameters = ui.SINGLESTOCKVIEW;
		
		if (stock != null) {
			withParameters += "/" + stock.getId().toString();
		}
		ui.navigateTo(withParameters);
	}
	
	private void handleSelectionEvent(Integer idx, Stock selectedStock) {
		deleteButton.setEnabled(idx != null);
		editButton.setEnabled(idx != null);
	}


}
