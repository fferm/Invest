package se.fermitet.invest.model;

import se.fermitet.invest.exception.InvestException;

public class Models {

	private static StocksModel stocksModel;

	static StocksModel stocksModel() {
		if (stocksModel == null) {
			stocksModel = new StocksModel();
		}
		return stocksModel;
	}

	public static Model fromClass(Class<? extends Model> clz) {
		if (clz == null) throw new InvestException("Null model class");
		
		if (clz.equals(StocksModel.class)) return stocksModel();
		
		throw new InvestException("Unknonw model class: " + clz);
	}
	
}
