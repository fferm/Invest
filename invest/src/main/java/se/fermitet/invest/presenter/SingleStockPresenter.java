package se.fermitet.invest.presenter;

import java.util.UUID;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;

public class SingleStockPresenter {
	StocksModel model;

	public SingleStockPresenter() {
		super();
		
		this.model = createModel();
	}
	
	public Stock getStockBasedOnIdString(String idString) {
		if (idString == null || idString.length() == 0) {
			return new Stock();
		} else {
			UUID id = UUID.fromString(idString);
			
			return model.getStockById(id);
		}
	}

	protected StocksModel createModel() {
		return new StocksModel();
	}
}
