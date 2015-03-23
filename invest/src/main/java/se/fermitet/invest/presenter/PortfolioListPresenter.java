package se.fermitet.invest.presenter;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.model.PortfolioModel;
import se.fermitet.invest.viewinterface.ListView;

public class PortfolioListPresenter extends ListPresenter<ListView<Portfolio>, Portfolio, PortfolioModel> {

	public PortfolioListPresenter(ListView<Portfolio> view) {
		super(view, PortfolioModel.class);
	}

}
