package se.fermitet.invest.webui.views;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class StockForm extends CustomComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1028596715063809826L;

	TextField symbolField;
	TextField nameField;
	
	public StockForm() {
		FormLayout layout = new FormLayout();
		
		initFields();
		
		layout.addComponent(symbolField);
		layout.addComponent(nameField);
		
		setCompositionRoot(layout);
	}
	
	private void initFields() {
		symbolField = new TextField("Ticker");
		nameField = new TextField("Namn");
	}
	

}
