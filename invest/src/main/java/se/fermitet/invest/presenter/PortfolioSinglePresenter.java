package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.PortfolioModel;
import se.fermitet.invest.viewinterface.PortfolioSingleView;

public class PortfolioSinglePresenter extends POJOSinglePresenter<PortfolioSingleView, Portfolio, PortfolioModel>{

	public PortfolioSinglePresenter(PortfolioSingleView view) {
		super(view, PortfolioModel.class, Portfolio.class);
	}

}
