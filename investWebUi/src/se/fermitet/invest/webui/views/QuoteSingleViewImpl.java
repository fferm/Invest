package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.presenter.QuoteSinglePresenter;
import se.fermitet.invest.viewinterface.QuoteSingleView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.POJOComboBoxAdapter;
import se.fermitet.vaadin.widgets.POJOPropertyDatePopupAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;

public class QuoteSingleViewImpl extends POJOSingleViewImpl<QuoteSinglePresenter, Quote> implements QuoteSingleView {

	private static final long serialVersionUID = 1356831164885978731L;

	POJOComboBoxAdapter<Stock> stockComboAdapter;
	POJOPropertyDatePopupAdapter<Quote> dateAdapter;

	@Override
	protected void bindToData() {
		if (this.pojo == null) return;

		stockComboAdapter.bindSelectionToProperty(pojo, "stock");
		dateAdapter.bindToProperty(pojo, "date");
	}

	@Override
	protected void initAndAddFields(Layout layout) {
		stockComboAdapter = new POJOComboBoxAdapter<Stock>(Stock.class, "Aktie");
		stockComboAdapter.setDisplayColumn("symbol");
		stockComboAdapter.setSortOrder("symbol");
		stockComboAdapter.getUI().addValueChangeListener(e -> valueChanged());
		layout.addComponent(stockComboAdapter.getUI());
		
		dateAdapter = new POJOPropertyDatePopupAdapter<Quote>(Quote.class, "Datum");
		dateAdapter.getUI().addValueChangeListener(e -> valueChanged());
		layout.addComponent(dateAdapter.getUI());
	}

	@Override
	protected QuoteSinglePresenter createPresenter() {
		return new QuoteSinglePresenter(this);
	}
	
	@Override
 	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		presenter.provideAllStocks();
		
		super.enter(event, parameters);
	}

	@Override
	public void showStocksInSelection(List<Stock> list) {
		throw new UnsupportedOperationException("unimplemented");
	}


}
