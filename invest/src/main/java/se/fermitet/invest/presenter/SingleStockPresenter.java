package se.fermitet.invest.presenter;

import java.util.UUID;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StocksModel;
import se.fermitet.invest.viewinterface.SingleStockView;

public class SingleStockPresenter extends Presenter<SingleStockView, StocksModel>{
	public SingleStockPresenter(SingleStockView view) {
		super(view, StocksModel.class);
	}
	
	public Stock getStockBasedOnIdString(String idString) {
		if (idString == null || idString.length() == 0) {
			return new Stock();
		} else {
			UUID id = UUID.fromString(idString);
			
			return model.getStockById(id);
		}
	}

	public void onCancelButtonClick() {
		view.navigateBack();
	}
}
