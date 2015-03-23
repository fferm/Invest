package se.fermitet.invest.webui.views;

import se.fermitet.invest.webui.InvestWebUI;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

public class NavBarViewImpl extends CustomComponent {

	private static final long serialVersionUID = 6062040243787568085L;
	
	Button stock;
	Button transaction;
	Button portfolio;

	public NavBarViewImpl() {
		super();
		
		setCompositionRoot(createMainLayout());
	}

	protected Component createMainLayout() {
		HorizontalLayout mainLayout = new HorizontalLayout();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(new MarginInfo(true, true, false, true));
		
		stock = new Button("Aktier");
		stock.addClickListener((Button.ClickListener) l -> {
			getNavigator().navigateTo(InvestWebUI.STOCK_LIST);
		});
		mainLayout.addComponent(stock);

		transaction = new Button("AffŠrer");
		transaction.addClickListener((Button.ClickListener) l-> {
			getNavigator().navigateTo(InvestWebUI.TRANSACTION_LIST);
		});
		mainLayout.addComponent(transaction);
		
		portfolio = new Button("Portfšljer");
		portfolio.addClickListener((Button.ClickListener) l-> {
			getNavigator().navigateTo(InvestWebUI.PORTFOLIO_LIST);
		});
		mainLayout.addComponent(portfolio);
		
		return mainLayout;
	}
	
	protected DirectionalNavigator getNavigator() {
		InvestWebUI ui = (InvestWebUI) getUI();
		
		if (ui != null) return ui.getDirectionalNavigator();
		return null;
	}

}
