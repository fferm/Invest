package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.presenter.SinglePOJOPresenter;
import se.fermitet.vaadin.navigation.DirectionalNavigator;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SuppressWarnings("rawtypes")
public abstract class SinglePOJOViewImplTest<VIEWIMPL extends SinglePOJOViewImpl, PRESENTER extends SinglePOJOPresenter, POJOCLASS> {
	protected VIEWIMPL view;
	protected PRESENTER mockedPresenter;
	private Class<POJOCLASS> pojoClass;

	public SinglePOJOViewImplTest(Class<POJOCLASS> pojoClass) {
		super();
		this.pojoClass = pojoClass;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		view = createViewImpl();
		mockedPresenter = (PRESENTER) view.presenter;
	}

	protected abstract VIEWIMPL createViewImpl();
	protected abstract POJOCLASS getTestPojo();
	protected abstract void checkUIAgainstPojo(POJOCLASS pojo);
	protected abstract void updateUIFromPOJO(POJOCLASS updated);
	protected abstract void makeUIDataInvalid();
	protected abstract void checkFieldValidity(boolean shouldBeValid);


	protected POJOCLASS getDefaultPojo() throws Exception {
		return pojoClass.newInstance();
	}
	

	@Test
	public void testEnterShowsSomeData_withValues() throws Exception {
		POJOCLASS pojo = getTestPojo();

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(pojo);
		
		view.enter(mock(ViewChangeEvent.class));

		checkUIAgainstPojo(pojo);
	}

	@Test
	public void testEnterShowsSomeData_default() throws Exception {
		POJOCLASS defaultPojo = getDefaultPojo();
		
		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(defaultPojo);
		
		view.enter(mock(ViewChangeEvent.class));
		
		checkUIAgainstPojo(defaultPojo);
	}

	@Test
	public void testNavigateBack() throws Exception {
		DirectionalNavigator mockedNavigator = view.getNavigator();
		
		view.navigateBack();
		
		verify(mockedNavigator).navigateBack();
	}

	@Test
	public void testCancelButtonCallsPresenter() throws Exception {
		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(getTestPojo());
		
		view.enter(mock(ViewChangeEvent.class));
		
		view.cancelButton.click();
		
		verify(mockedPresenter).onCancelButtonClick();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testOKButtonCallsPresenterWithUpdatedData() throws Exception {
		POJOCLASS initial = getDefaultPojo();
		POJOCLASS updated = getTestPojo();
		
		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initial);

		view.enter(mock(ViewChangeEvent.class));
		
		updateUIFromPOJO(updated);
		checkUIAgainstPojo(updated);

		reset(mockedPresenter);

		view.okButton.click();

		verify(mockedPresenter).onOkButtonClick(eq(updated));
	}
	
	@Test
	public void testHandlingOfInvalidPojo() throws Exception {
		POJOCLASS initial = getTestPojo();

		when(mockedPresenter.getDOBasedOnIdString(anyString())).thenReturn(initial);

		view.enter(mock(ViewChangeEvent.class));

		assertTrue("ok button enabled before", view.okButton.isEnabled());
		
		checkFieldValidity(true);
		assertTrue("form valid before", view.isValid());

		makeUIDataInvalid();

		assertFalse("ok button disabled after", view.okButton.isEnabled());
		
		checkFieldValidity(false);
		assertFalse("form not valid after", view.isValid());
	}



}
