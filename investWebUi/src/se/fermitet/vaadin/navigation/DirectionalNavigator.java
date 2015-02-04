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
	private List<String> navStringsInOrder;
	private int currentIdx = -1;

	public static final String PARAMETERS_START = "/";
	public static final String PARAMETERS_SEPARATOR = "&";

	public DirectionalNavigator(UI ui, SingleComponentContainer container) {
		super();
		
		this.addedViews = new ArrayList<String>();
		this.navStringsInOrder = new ArrayList<String>();
		
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
			
			if (iter.hasNext()) parameterString += PARAMETERS_SEPARATOR;
		}
		
		this.navigateTo(viewName, parameterString);
	}
	
	private void navigateTo(String viewName, String parameterString) {
		if (! addedViews.contains(viewName)) throw new DirectionalNavigatorException("A view with the name " + viewName + " is not added to this DirectionalNavigator");
		
		String navString = viewName;
		if (parameterString != null) navString += PARAMETERS_START + parameterString;
		
		addCurrentNavString(navString);
		
		navigator.navigateTo(navString);
	}

	protected void addCurrentNavString(String navString) {
		while (currentIdx < navStringsInOrder.size() - 1) {
			navStringsInOrder.remove(currentIdx + 1);
		}
		navStringsInOrder.add(navString);
		currentIdx++;
	}

	public void navigateBack() {
		navigateToPosition(currentIdx - 1);
	}

	public void navigateForward() {
		navigateToPosition(currentIdx + 1);
	}
	
	private void navigateToPosition(int idx) {
		if (idx < 0) throw new DirectionalNavigatorException("Cannot navigate back beyond the start point");
		if (idx > navStringsInOrder.size() - 1) throw new DirectionalNavigatorException("Cannot navigate forward beyond the end point");
		
		String navString = navStringsInOrder.get(idx);
		
		navigator.navigateTo(navString);
		
		currentIdx = idx;
		
	}

	public class DirectionalNavigatorException extends RuntimeException {
		private static final long serialVersionUID = 6895818559360339693L;

		public DirectionalNavigatorException(String msg) {
			super(msg);
		}
	}

	
}
