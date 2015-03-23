package se.fermitet.invest.webui.views;

import se.fermitet.invest.domain.Portfolio;
import se.fermitet.invest.presenter.PortfolioSinglePresenter;
import se.fermitet.invest.viewinterface.PortfolioSingleView;
import se.fermitet.vaadin.widgets.POJOPropertyTextFieldAdapter;

import com.vaadin.ui.Layout;

public class PortfolioSingleViewImpl extends POJOSingleViewImpl<PortfolioSinglePresenter, Portfolio> implements PortfolioSingleView {

	private static final long serialVersionUID = 5576220309319950692L;

	POJOPropertyTextFieldAdapter<Portfolio, String> nameAdapter;

	@Override
	protected void initAndAddFields(Layout layout) {
		nameAdapter = new POJOPropertyTextFieldAdapter<Portfolio, String>(Portfolio.class, "Namn");
		nameAdapter.getUI().addValueChangeListener(e -> valueChanged());
		layout.addComponent(nameAdapter.getUI());
	}

	@Override
	protected void bindToData() {
		nameAdapter.bindToProperty(pojo, "name");
	}

	@Override
	protected PortfolioSinglePresenter createPresenter() {
		return new PortfolioSinglePresenter(this);
	}

}
