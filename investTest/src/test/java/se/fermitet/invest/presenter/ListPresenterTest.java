package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.ListView;

public abstract class ListPresenterTest<PRESENTER extends ListPresenter<?, POJO, ?>, POJO, MODEL extends Model<POJO>, VIEWINTERFACE extends ListView<POJO>> extends PresenterTest<PRESENTER, POJO, MODEL, VIEWINTERFACE> {

	public ListPresenterTest(Class<?> viewInterfaceClass, Class<?> pojoClass) {
		super(viewInterfaceClass, pojoClass);
	}

	@Test
	public void testFillListWithAllPojos() throws Exception {
		List<POJO> list = new ArrayList<POJO>();
		when(mockedModel.getAll()).thenReturn(list);
	
		presenter.fillViewWithData();
		
		verify(mockedModel).getAll();
		verify(mockedView).displayData(list);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteFromViewCallsModelDelete() throws Exception {
		POJO toDelete = (POJO) pojoClass.newInstance();
		List<POJO> list = new ArrayList<POJO>();
		when(mockedModel.getAll()).thenReturn(list);
		
		presenter.onDeleteButtonClick(toDelete);
		
		verify(mockedModel).delete(toDelete);
		verify(mockedView).displayData(list);
	}
	
	@Test
	public void testNewButtonClickCallsNavigateToSingleView() throws Exception {
		presenter.onNewButtonClick();
		
		verify(mockedView).navigateToSingleView(null);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEditButtonClickCallsEditSingleStock() throws Exception {
		POJO editObj = (POJO) pojoClass.newInstance();
		presenter.onEditButtonClick(editObj);

		verify(mockedView).navigateToSingleView(editObj);
	}




}
