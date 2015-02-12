package se.fermitet.invest.presenter;

import java.util.UUID;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.SingleStockView;

public class SingleStockPresenter extends Presenter<SingleStockView, StockModel>{
	public SingleStockPresenter(SingleStockView view) {
		super(view, StockModel.class);
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

	public void onOkButtonClick(Stock stock) {
		model.save(stock);
		view.navigateBack();
	}
}
