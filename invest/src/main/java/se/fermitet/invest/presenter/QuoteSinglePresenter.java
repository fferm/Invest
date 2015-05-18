package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.model.Models;
import se.fermitet.invest.model.QuoteModel;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.QuoteSingleView;

public class QuoteSinglePresenter extends POJOSinglePresenter<QuoteSingleView, Quote, QuoteModel> {

	protected StockModel stocksModel;

	public QuoteSinglePresenter(QuoteSingleView view) {
		super(view, QuoteModel.class, Quote.class);

		this.stocksModel = createStocksModel();
	}

	public void provideAllStocks() {
		this.view.showStocksInSelection(stocksModel.getAll());
	}

	protected StockModel createStocksModel() {
		return (StockModel) Models.fromClass(StockModel.class);
	}
}
