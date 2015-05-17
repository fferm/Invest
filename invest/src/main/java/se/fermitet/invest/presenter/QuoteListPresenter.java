package se.fermitet.invest.presenter;

import java.util.List;

import se.fermitet.invest.domain.Quote;
import se.fermitet.invest.model.QuoteModel;
import se.fermitet.invest.viewinterface.ListView;

public class QuoteListPresenter extends ListPresenter<ListView<Quote>, Quote, QuoteModel> {

	public QuoteListPresenter(ListView<Quote> view) {
		super(view, QuoteModel.class);
	}

	public List<Quote> getQuotesByStockId(String anyString) {
		throw new UnsupportedOperationException("unimplemented");
	}

}
