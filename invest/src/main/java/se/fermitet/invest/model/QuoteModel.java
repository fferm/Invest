package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.domain.Stock;

public class QuoteModel extends Model<Quote> {

	QuoteModel() {
		super();
	}
	
	@Override
	public List<Quote> getAll() {
		// TODO: Should there be a getAll for quotes?
		return storage.getAllQuotes();
	}

	@Override
	public Quote getById(UUID id) {
		return storage.getQuoteById(id);
	}

	@Override
	public void save(Quote obj) {
		storage.saveQuote(obj);
	}

	@Override
	public void delete(Quote obj) {
		storage.deleteQuote(obj);
	}

	protected StockModel getStockModel() {
		return Models.stocksModel();
	}

	public List<Quote> getQuotesByStock(Stock stock) {
		return storage.getQuotesByStock(stock);
	}

}
