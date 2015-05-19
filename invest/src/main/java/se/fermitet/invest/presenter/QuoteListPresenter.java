package se.fermitet.invest.presenter;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.Models;
import se.fermitet.invest.model.QuoteModel;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.ListView;

public class QuoteListPresenter extends ListPresenter<ListView<Quote>, Quote, QuoteModel> {

	StockModel stockModel;

	public QuoteListPresenter(ListView<Quote> view) {
		super(view, QuoteModel.class);
		
		this.stockModel = createStockModel();
	}

//	public List<Quote> getQuotesByStockId(String stockId) {
//		return model.getQuotesByStockId(UUID.fromString(stockId));
//	}

	protected StockModel createStockModel() {
		return (StockModel) Models.fromClass(StockModel.class);
	}

	public Stock getStockById(String stockId) {
		return this.stockModel.getById(UUID.fromString(stockId));
	}

	public List<Quote> getQuotesByStock(Stock stock) {
		return model.getQuotesByStock(stock);
	}

	public void onNewButtonClick(Stock otherStock) {
		throw new UnsupportedOperationException("unimplemented");
	}

}
