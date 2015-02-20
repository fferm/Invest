package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.TransactionListPresenter;
import se.fermitet.invest.viewinterface.TransactionListView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.ColumnDefinition;
import se.fermitet.vaadin.widgets.POJOTableAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class TransactionListViewImpl extends ViewImpl<TransactionListPresenter> implements TransactionListView {
	private static final long serialVersionUID = 2798795542114548594L;
	
	POJOTableAdapter<Transaction> tableAdapter;
	private Table table;
	private Button newButton;
	private Button editButton;
	private Button deleteButton;

	@Override
	protected Component createMainLayout() {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);

		initTable(mainLayout);
		initButtonPanel(mainLayout);

		return mainLayout;
	}
	
	@Override
	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		this.presenter.fillViewWithData();
	}
	
	private void initTable(Layout parent) {
		tableAdapter = new POJOTableAdapter<Transaction>(Transaction.class, "Affärer");
		table = tableAdapter.getTable();

		List<ColumnDefinition> cols = new ArrayList<ColumnDefinition>();
		cols.add(new ColumnDefinition("date", "Datum"));
		cols.add(new ColumnDefinition("stock.symbol", "Aktie"));
		cols.add(new ColumnDefinition("number", "Antal"));
		cols.add(new ColumnDefinition("price", "Pris"));
		cols.add(new ColumnDefinition("fee", "Courtage"));

		tableAdapter.setVisibleData(cols);
		tableAdapter.addSelectionListener((Integer idx, Transaction selected) -> handleSelectionEvent(idx, selected));
		setSortOrder();
		
		table.setSelectable(true);
		table.setImmediate(true);
		table.setPageLength(10);

		parent.addComponent(table);
	}

	private void setSortOrder() {
		List<String> sortOrder = new ArrayList<String>();
		sortOrder.add("stock.symbol");
		sortOrder.add("date");
		tableAdapter.setSortOrder(sortOrder);
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
		this.newButton = new Button("Lägg till");

		newButton.addClickListener((Button.ClickListener) l -> this.presenter.onNewButtonClick());
		parent.addComponent(newButton);
	}

	private void initEditButton(Layout parent) {
		this.editButton = new Button("Ändra");
		editButton.setEnabled(false);

		editButton.addClickListener((Button.ClickListener) l -> {
			Transaction selected = this.tableAdapter.getSelectedData();
			this.presenter.onEditButtonClick(selected);
		});

		parent.addComponent(editButton);
	}

	private void initDeleteButton(Layout parent) {
		this.deleteButton = new Button("Ta bort");
		deleteButton.setEnabled(false);

		deleteButton.addClickListener((Button.ClickListener) l -> {
			Transaction selected = this.tableAdapter.getSelectedData();
			this.presenter.onDeleteButtonClick(selected);
		});

		parent.addComponent(deleteButton);
	}



	private void handleSelectionEvent(Integer idx, Transaction selected) {
		throw new UnsupportedOperationException("unimplemented");
	}


	@Override
	protected TransactionListPresenter createPresenter() {
		throw new UnsupportedOperationException("unimplemented");
	}

	@Override
	public void displayData(List<Transaction> data) {
		tableAdapter.setData(data);
	}


}
