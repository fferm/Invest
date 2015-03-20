package se.fermitet.invest.webui.views;

import java.util.List;

import se.fermitet.invest.presenter.SinglePOJOPresenter;
import se.fermitet.invest.viewinterface.SinglePOJOView;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

																			   //  VIEWINTERFACE extends SinglePOJOView, POJO, MODEL extends Model<POJO>
public abstract class SinglePOJOViewImpl<PRESENTER extends SinglePOJOPresenter<?, POJO, ?>, POJO> extends ViewImpl<PRESENTER> implements SinglePOJOView {
	private static final long serialVersionUID = 7033937523664849158L;

	protected POJO pojo;
	
	Button okButton;
	Button cancelButton;

	public SinglePOJOViewImpl() {
		super();
		super.init();
	}

	protected abstract void bindToData();
	protected abstract void initAndAddFields(Layout layout);
	
	@Override
	protected Component createMainLayout() {
		Layout layout = new FormLayout();

		initAndAddFields(layout);
		initAndAddButtons(layout);

		return layout;
	}
	
	private void initAndAddButtons(Layout layout) {
		HorizontalLayout buttonPanel = new HorizontalLayout();
		buttonPanel.setSpacing(true);

		okButton = new Button("OK");
		okButton.addClickListener((Button.ClickListener) l -> {
			onOkClick();
		});
		
		cancelButton = new Button("Avbryt");
		cancelButton.addClickListener((Button.ClickListener) l -> {
			onCancelClick();
		});

		buttonPanel.addComponent(okButton);
		buttonPanel.addComponent(cancelButton);
		
		layout.addComponent(buttonPanel);
	}

	@Override
	protected void enter(ViewChangeEvent event, List<URIParameter> parameters) {
		if (parameters.size() == 0) this.pojo = (POJO) presenter.getDOBasedOnIdString(null);
		else this.pojo = (POJO) presenter.getDOBasedOnIdString(parameters.get(0).getValue());

		bindToData();
	}
	
	@Override
	public void navigateBack() {
		this.getNavigator().navigateBack();
	}

	private void onOkClick() {
		if (isValid()) presenter.onOkButtonClick(this.pojo);
	}

	private void onCancelClick() {
		this.presenter.onCancelButtonClick();
	}
	
	protected void valueChanged() {
		okButton.setEnabled(isValid());
	}



}
