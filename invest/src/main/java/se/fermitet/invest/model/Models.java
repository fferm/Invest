package se.fermitet.invest.model;

import se.fermitet.invest.exception.InvestException;

public class Models {

	private static StocksModel stocksModel;
	private static TransactionModel transactionModel;
	
	public static Model fromClass(Class<? extends Model> clz) {
		if (clz == null) throw new InvestException("Null model class");
		
		if (clz.equals(StocksModel.class)) return stocksModel();
		if (clz.equals(TransactionModel.class)) return transactionModel();
		
		throw new InvestException("Unknonw model class: " + clz);
	}

	static StocksModel stocksModel() {
		if (stocksModel == null) {
			stocksModel = new StocksModel();
		}
		return stocksModel;
	}
	
	static TransactionModel transactionModel() {
		if (transactionModel == null) {
			transactionModel = new TransactionModel();
		}
		return transactionModel;
	}

}
