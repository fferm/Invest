package se.fermitet.invest.webui.views;

import java.util.ArrayList;
import java.util.List;

import se.fermitet.general.IdAble;
import se.fermitet.invest.presenter.ListPresenter;
import se.fermitet.invest.viewinterface.ListView;
import se.fermitet.vaadin.navigation.URIParameter;
import se.fermitet.vaadin.widgets.ColumnDefinition;
import se.fermitet.vaadin.widgets.POJOTableAdapter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ErrorMessage;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public abstract class ListViewImpl<PRESENTER extends ListPresenter<?, POJO, ?>, POJO extends IdAble<?>> extends ViewImpl<PRESENTER> implements ListView<POJO> {

	private static final long serialVersionUID = 6283336898591281354L;
	
	POJOTableAdapter<POJO> tableAdapter;
	Button deleteButton;
	Button editButton;
	Button newButton;

	private Class<POJO> pojoClass;
	private String tableTitle;

	protected abstract List<ColumnDefinition> getColumnDefinitions();
	protected abstract void setSortOrder();
	protected abstract String getSingleViewName();

	public ListViewImpl(Class<POJO> pojoClass, String tableTitle) {
		super();

		this.pojoClass = pojoClass;
		this.tableTitle = tableTitle;
		
		super.init();
	}
	
	@Override
	protected Component createMainLayout() {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setMargin(true);

		initAndAddTable(mainLayout);
		initAndAddButtonPanel(mainLayout);
		
		return mainLayout;
	}
	
	private void initAndAddTable(Layout parent) {
		tableAdapter = new POJOTableAdapter<POJO>(this.pojoClass, tableTitle);

		tableAdapter.setVisibleData(getColumnDefinitions());
		tableAdapter.addSelectionListener((POJO selected) -> handleSelectionEvent(selected));
		setSortOrder();
		
		tableAdapter.getUI().setSelectable(true);
		tableAdapter.getUI().setImmediate(true);
		tableAdapter.getUI().setPageLength(10);

		parent.addComponent(tableAdapter.getUI());
	}

	private void initAndAddButtonPanel(Layout parent) {
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);
		buttonPanel.setMargin(new MarginInfo(true, false, true, false));

		initNewButton(buttonPanel);
		initEditButton(buttonPanel);
		initDeleteButton(buttonPanel);

		initExtraButtons(buttonPanel);
		
		parent.addComponent(buttonPanel);
	}
	
	protected void initExtraButtons(Layout parent) {};
	
	private void initNewButton(Layout parent) {
		this.newButton = new Button("LŠgg till");

		newButton.addClickListener((Button.ClickListener) l -> this.presenter.onNewButtonClick());
		parent.addComponent(newButton);
	}

	private void initEditButton(Layout parent) {
		this.editButton = new Button("€ndra");
		editButton.setEnabled(false);

		editButton.addClickListener((Button.ClickListener) l -> {
			POJO selected = this.tableAdapter.getSelectedData();
			this.presenter.onEditButtonClick(selected);
		});

		parent.addComponent(editButton);
	}

	private void initDeleteButton(Layout parent) {
		this.deleteButton = new Button("Ta bort");
		deleteButton.setEnabled(false);

		deleteButton.addClickListener((Button.ClickListener) l -> {
			POJO selected = this.tableAdapter.getSelectedData();
			this.presenter.onDeleteButtonClick(selected);
		});

		parent.addComponent(deleteButton);
	}

	private void handleSelectionEvent(POJO selected) {
		for (Component comp : getComponentsToEnableWhenItemSelectedInTable()) {
			comp.setEnabled(selected != null);
		}
	}

	protected List<Component> getComponentsToEnableWhenItemSelectedInTable() {
		List<Component> ret = new ArrayList<Component>();
		
		ret.add(deleteButton);
		ret.add(editButton);
		
		return ret;
	}
	

	@Override
	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		// TODO: Consider having presenter return the list and UIImpl fills its data instead of presenter doing it.  Similar to how Single views do it (and also QuoteListImpl)
		this.presenter.fillViewWithData();
	}

	public void displayData(List<POJO> stocks) {
		tableAdapter.setData(stocks);
	}
	
	@Override
	public void navigateToSingleView(POJO data) {
		if (data == null) {
			getNavigator().navigateTo(getSingleViewName());
		} else {
			getNavigator().navigateTo(getSingleViewName(), new URIParameter(data.getId().toString()));
		}
	}
	
	@Override
	protected void handleApplicationException(ErrorMessage applicationException) {
		deleteButton.setComponentError(applicationException);
	}
}
