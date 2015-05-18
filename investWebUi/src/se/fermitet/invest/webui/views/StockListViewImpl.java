package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.StockListPresenter;
import se.fermitet.invest.viewinterface.StockListView;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.invest.webui.navigation.EntityNameHelper;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.ColumnDefinition;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;

public class StockListViewImpl extends ListViewImpl<StockListPresenter, Stock> implements StockListView {

	private static final long serialVersionUID = 1037623075906362499L;
	
	Button quotesButton;

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
	protected void initExtraButtons(Layout parent) {
		this.quotesButton = new Button("Kurser");
		quotesButton.setEnabled(false);
		
		quotesButton.addClickListener((Button.ClickListener) l -> {
			Stock selected = this.tableAdapter.getSelectedData();
			this.presenter.onQuotesButtonClick(selected);
		});

		parent.addComponent(quotesButton);
	}
	
	@Override
	protected List<Component> getComponentsToEnableWhenItemSelectedInTable() {
		List<Component> superList = super.getComponentsToEnableWhenItemSelectedInTable();
		
		superList.add(quotesButton);
		
		return superList;
	}
	
	@Override
	protected void setSortOrder() {
		tableAdapter.setSortOrder("symbol");
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.STOCK_SINGLE;
	}

	@Override
	public void navigateToQuotesList(Stock stock) {
		getNavigator().navigateTo(InvestWebUI.QUOTE_LIST, new URIParameter(EntityNameHelper.entityNameFor(Quote.class), stock.getId().toString()));
	}
}
