package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.model.StockModel;
import se.fermitet.invest.viewinterface.SingleStockView;

public class SingleStockPresenter extends SinglePOJOPresenter<SingleStockView, Stock, StockModel> {
	public SingleStockPresenter(SingleStockView view) {
		super(view, StockModel.class, Stock.class);
	}
	
	public void onCancelButtonClick() {
		view.navigateBack();
	}

	public void onOkButtonClick(Stock stock) {
		model.save(stock);
		view.navigateBack();
	}
}
