package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.presenter.PortfolioListPresenter;
import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.widgets.ColumnDefinition;

public class PortfolioListViewImpl extends ListViewImpl<PortfolioListPresenter, Portfolio>{
	private static final long serialVersionUID = 6345320670139923780L;

	public PortfolioListViewImpl() {
		super(Portfolio.class, "Portfšljer");
	}

	@Override
	protected List<ColumnDefinition> getColumnDefinitions() {
		List<ColumnDefinition> cols = new ArrayList<ColumnDefinition>();
		cols.add(new ColumnDefinition("name", "Namn"));
		return cols;
	}

	@Override
	protected void setSortOrder() {
		tableAdapter.setSortOrder("name");
	}

	@Override
	protected String getSingleViewName() {
		return InvestWebUI.PORTFOLIO_SINGLE;
	}

	@Override
	protected PortfolioListPresenter createPresenter() {
		return new PortfolioListPresenter(this);
	}


}
