package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.general.IdAble;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.presenter.ListPresenter;
import se.fermitet.invest.webui.navigation.EntityNameHelper;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.ui.Button;

public abstract class ListViewImplTest<VIEWIMPL extends ListViewImpl<?, POJO>, PRESENTER extends ListPresenter<?, POJO, ?>, POJO extends IdAble<?>> {
	protected VIEWIMPL view;
	protected List<POJO> testDataUnsorted;
	protected List<POJO> testDataSorted;
	protected PRESENTER mockedPresenter;
	private Class<POJO> pojoClass;

	public ListViewImplTest(Class<POJO> pojoClass) {
		super();
		this.pojoClass = pojoClass;
	}
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		view = createViewImpl();
		mockedPresenter = (PRESENTER) view.presenter;

		initTestData();
		
		view.displayData(testDataUnsorted);
	}
	
	protected abstract VIEWIMPL createViewImpl();
	protected abstract String getSingleViewName();
	protected abstract List<POJO> getTestData();
	protected abstract Comparator<? super POJO> getComparator();

	private void initTestData() {
		testDataUnsorted = getTestData();

		testDataSorted = new ArrayList<POJO>(testDataUnsorted);
		testDataSorted.sort(getComparator());
	}



	@Test
	public void testEnterWithNullCallsFillViewWithData() throws Exception {
		view.enter(null);
	
		verify(mockedPresenter).fillViewWithData();
	}
	
	@Test
	public void testCallingDisplayDataDoesDisplays() throws Exception {
		List<POJO> displayedData = view.tableAdapter.getData();
		assertArrayEquals(testDataSorted.toArray(), displayedData.toArray());
	}
	
	@Test
	public void testSelectionAffectstButtonsEnabledStatus() throws Exception {
		List<Button> buttonsToTest = getButtonsToTestIsEnabledWhenItemSelectedInList();
		
		for (Button button : buttonsToTest) {
			assertFalse("Before: " + button.getCaption(), button.isEnabled());
		}
		
		view.tableAdapter.getUI().select(this.testDataSorted.get(1).getId());

		for (Button button : buttonsToTest) {
			assertTrue("After select: " + button.getCaption(), button.isEnabled());
		}

		view.tableAdapter.getUI().select(null);
		
		for (Button button : buttonsToTest) {
			assertFalse("After unselect: " + button.getCaption(), button.isEnabled());
		}
	}
	
	protected List<Button> getButtonsToTestIsEnabledWhenItemSelectedInList() {
		List<Button> ret = new ArrayList<Button>();
		
		ret.add(view.deleteButton);
		ret.add(view.editButton);
		
		return ret;
	}

	@Test
	public void testNewButton() throws Exception {
		Button newButton = view.newButton;
		
		assertNotNull("not null", newButton);
		assertTrue("Enabled", newButton.isEnabled());
		assertTrue("Visible", newButton.isVisible());
		
		newButton.click();
		
		verify(mockedPresenter).onNewButtonClick();
	}
	
	@Test
	public void testEditButton() throws Exception {
		Button editButton = view.editButton;
		
		assertNotNull("not null", editButton);
		
		POJO toSelect = testDataUnsorted.get(0);
		view.tableAdapter.select(toSelect);

		editButton.click();
		
		verify(mockedPresenter).onEditButtonClick(toSelect);
	}
	
	@Test
	public void testDeleteButton() throws Exception {
		POJO toDelete = testDataSorted.get(2);
		
		view.tableAdapter.select(toDelete);
		view.deleteButton.click();
		
		verify(mockedPresenter).onDeleteButtonClick(toDelete);
	}

	@Test
	public void testEditSinglePojo_nullValue() throws Exception {
		view.navigateToSingleView(null);
		
		verify(view.getNavigator()).navigateTo(getSingleViewName());
	}

	@Test
	public void testEditSinglePojo_notNullValue() throws Exception {
		POJO testData = testDataSorted.get(2);
		
		view.navigateToSingleView(testData);
		verify(view.getNavigator()).navigateTo(getSingleViewName(), new URIParameter(EntityNameHelper.entityNameFor(pojoClass), testData.getId().toString()));
	}
	
	@Test
	public void testHasApplicationException() throws Exception {
		assertFalse("false before", view.hasApplicationException());
		
		view.displayApplicationException(new ModelException(ModelExceptionType.DUMMY));
		assertTrue("true after", view.hasApplicationException());
		
		view.clearApplicationException();
		assertFalse("false after clear", view.hasApplicationException());
	}
}
