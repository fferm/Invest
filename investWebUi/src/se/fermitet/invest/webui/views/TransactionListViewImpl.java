package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.presenter.TransactionListPresenter;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.widgets.ColumnDefinition;

public class TransactionListViewImpl extends ListViewImpl<TransactionListPresenter, Transaction> {
	private static final long serialVersionUID = 2798795542114548594L;

	public TransactionListViewImpl() {
		super(Transaction.class, "Affärer");
	}
	
	@Override
	protected TransactionListPresenter createPresenter() {
		return new TransactionListPresenter(this);
	}

	protected List<ColumnDefinition> getColumnDefinitions() {
		List<ColumnDefinition> cols = new ArrayList<ColumnDefinition>();
		cols.add(new ColumnDefinition("portfolio.name", "Portfölj"));
		cols.add(new ColumnDefinition("stock.symbol", "Aktie"));
		cols.add(new ColumnDefinition("date", "Datum"));
		cols.add(new ColumnDefinition("number", "Antal"));
		cols.add(new ColumnDefinition("price", "Pris"));
		cols.add(new ColumnDefinition("fee", "Courtage"));
		return cols;
	}
	
	@Override
	protected void setSortOrder() {
		List<String> sortOrder = new ArrayList<String>();
		sortOrder.add("portfolio.name");
		sortOrder.add("stock.symbol");
		sortOrder.add("date");
		tableAdapter.setSortOrder(sortOrder);
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.TRANSACTION_SINGLE;
	}

}
