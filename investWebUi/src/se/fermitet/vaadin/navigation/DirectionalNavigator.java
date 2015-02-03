package se.fermitet.vaadin.navigation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

public class DirectionalNavigator {

	Navigator navigator;
	private List<String> addedViews;

	public DirectionalNavigator(UI ui, SingleComponentContainer container) {
		super();
		
		this.addedViews = new ArrayList<String>();
		this.navigator = createNavigator(ui, container);
	}

	protected Navigator createNavigator(UI ui, SingleComponentContainer container) {
		return new Navigator(ui, container);
	}
	
	public void addView(String name, Class<? extends View> clz) {
		addedViews.add(name);
		navigator.addView(name,  clz);
	}
	
	public void navigateTo(String viewName) {
		this.navigateTo(viewName, (String) null);
	}
	
	public void navigateTo(String viewName, URIParameter param) {
		this.navigateTo(viewName, param.toString());
	}

	public void navigateTo(String viewName, List<URIParameter> parameters) {
		String parameterString = "";
		for (Iterator<URIParameter> iter = parameters.iterator(); iter.hasNext(); ) {
			URIParameter parameter = iter.next();
			
			parameterString += parameter.toString();
			
			if (iter.hasNext()) parameterString += "&";
		}
		
		this.navigateTo(viewName, parameterString);
	}
	
	private void navigateTo(String viewName, String parameterString) {
		if (! addedViews.contains(viewName)) throw new DirectionalNavigatorException("A view with the name " + viewName + " is not added to this DirectionalNavigator");
		
		String navString = viewName;
		if (parameterString != null) navString += "/" + parameterString;
		
		navigator.navigateTo(navString);
	}


	public class DirectionalNavigatorException extends RuntimeException {
		private static final long serialVersionUID = 6895818559360339693L;

		public DirectionalNavigatorException(String msg) {
			super(msg);
		}
	}
}
