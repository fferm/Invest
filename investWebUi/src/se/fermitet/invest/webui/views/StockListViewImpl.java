package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.viewinterface.StockListView;
import se.fermitet.vaadin.widgets.ColumnDefinition;
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

	Button deleteButton;
	Button newButton;
	StockForm stockForm;
	Table stockTable;

	private List<StockListViewListener> listeners;



	public StockListViewImpl() {
		super();

		this.listeners = new ArrayList<StockListView.StockListViewListener>();
		
		init();
	}
	
	private void init() {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);

		initMainPanel(mainLayout);
		initButtonPanel(mainLayout);
		
		setCompositionRoot(mainLayout);
	}
	
	private void initMainPanel(Layout parent) {
		HorizontalLayout horiz = new HorizontalLayout();
		
		initStockTable(horiz);
		initForm(horiz);
		
		parent.addComponent(horiz);
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
	
	private void initForm(Layout parent) {
		stockForm = new StockForm();
		stockForm.setVisible(false);
		parent.addComponent(stockForm);
	}
	
	private void initButtonPanel(Layout parent) {
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setMargin(new MarginInfo(true, false, true, false));
		
		initNewButton(buttonPanel);
		initDeleteButton(buttonPanel);

		parent.addComponent(buttonPanel);
	}

	private void initNewButton(Layout parent) {
		this.newButton = new Button("L�gg till");
		
		newButton.addClickListener((Button.ClickListener) l -> {
			fireNewButtonClickedEvent();
		});
		parent.addComponent(newButton);
	}

	private void initDeleteButton(Layout parent) {
		this.deleteButton = new Button("Ta bort");
		deleteButton.setEnabled(false);
		
		deleteButton.addClickListener((Button.ClickListener) l -> {
			fireDeleteButtonClickedEvent();
		});
		
		parent.addComponent(deleteButton);
	}

	@Override
	public void displayStocks(List<Stock> stocks) {
		stockTableAdapter.setData(stocks);
	}
	
	@Override
	public void showStockForm(Stock stockToWorkOn) {
		stockForm.setVisible(true);
	}

	
	private void handleSelectionEvent(Integer idx, Stock selectedStock) {
		deleteButton.setEnabled(idx != null);
	}

	@Override
	public void addListener(StockListViewListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(StockListViewListener listener) {
		listeners.remove(listener);
	}

	private void fireNewButtonClickedEvent() {
		for (StockListViewListener listener : listeners) {
			listener.onNewButtonClick();
		}
	}
	private void fireDeleteButtonClickedEvent() {
		Stock selectedStock = this.stockTableAdapter.getSelectedData();
		
		for (StockListViewListener listener : listeners) {
			listener.onDeleteButtonClick(selectedStock);
		}
	}


}
