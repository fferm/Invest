package se.fermitet.invest.webui.views;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.general.IdAble;
import se.fermitet.invest.model.ModelException;
import se.fermitet.invest.model.ModelException.ModelExceptionType;
import se.fermitet.invest.presenter.ListPresenter;
import se.fermitet.vaadin.navigation.URIParameter;

import com.vaadin.ui.Button;

public abstract class ListViewImplTest<VIEWIMPL extends ListViewImpl<?, POJO>, PRESENTER extends ListPresenter<?, POJO, ?>, POJO extends IdAble<?>> {
	protected VIEWIMPL view;
	protected List<POJO> testDataUnsorted;
	protected List<POJO> testDataSorted;
	protected PRESENTER mockedPresenter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		view = createViewImpl();
		mockedPresenter = (PRESENTER) view.presenter;

		initTestData();
		
		view.displayData(testDataUnsorted);
	}
	
	protected abstract VIEWIMPL createViewImpl();
	protected abstract void initTestData();
	protected abstract String getSingleViewName();

	@Test
	public void testEnterCallsFillViewWithData() throws Exception {
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
		Button deleteButton = view.deleteButton;
		Button editButton = view.editButton;
		
		assertFalse("Before - delete", deleteButton.isEnabled());
		assertFalse("Before - edit", editButton.isEnabled());
		
		view.tableAdapter.getUI().select(this.testDataSorted.get(1).getId());
		assertTrue("After select - delete", deleteButton.isEnabled());
		assertTrue("After select - edit", editButton.isEnabled());

		view.tableAdapter.getUI().select(null);
		assertFalse("After unselect - delete", deleteButton.isEnabled());
		assertFalse("After unselect - edit", editButton.isEnabled());
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
		verify(view.getNavigator()).navigateTo(getSingleViewName(), new URIParameter(testData.getId().toString()));
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
