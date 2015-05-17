package se.fermitet.invest.model;

import se.fermitet.invest.exception.InvestException;

public class Models {

	private static StockModel stockModel;
	private static TransactionModel transactionModel;
	private static PortfolioModel portfolioModel;
	private static QuoteModel quoteModel;
	
	public static Model<?> fromClass(Class<?> clz) {
		if (clz == null) throw new InvestException("Null model class");
		
		if (clz.equals(StockModel.class)) return stocksModel();
		if (clz.equals(TransactionModel.class)) return transactionModel();
		if (clz.equals(PortfolioModel.class)) return portfolioModel();
		if (clz.equals(QuoteModel.class)) return quoteModel();
		
		throw new InvestException("Unknown model class: " + clz);
	}

	static StockModel stocksModel() {
		if (stockModel == null) {
			stockModel = new StockModel();
		}
		return stockModel;
	}
	
	static TransactionModel transactionModel() {
		if (transactionModel == null) {
			transactionModel = new TransactionModel();
		}
		return transactionModel;
	}
	
	static PortfolioModel portfolioModel() {
		if (portfolioModel == null) {
			portfolioModel = new PortfolioModel();
		}
		return portfolioModel;
	}
	
	static QuoteModel quoteModel() {
		if (quoteModel == null) {
			quoteModel = new QuoteModel();
		}
		return quoteModel;
	}

}
