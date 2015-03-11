package se.fermitet.invest.model;

import se.fermitet.invest.exception.InvestException;

public class Models {

	private static StockModel stockModel;
	private static TransactionModel transactionModel;
	
	public static Model<?> fromClass(Class<?> clz) {
		if (clz == null) throw new InvestException("Null model class");
		
		if (clz.equals(StockModel.class)) return stocksModel();
		if (clz.equals(TransactionModel.class)) return transactionModel();
		
		throw new InvestException("Unknonw model class: " + clz);
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

}
