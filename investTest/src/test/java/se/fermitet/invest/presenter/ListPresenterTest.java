package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.ListView;

public abstract class ListPresenterTest<PRESENTER extends ListPresenter<?, ?, ?>, POJOCLASS, MODEL extends Model<POJOCLASS>, VIEWINTERFACECLASS extends ListView<POJOCLASS>> extends PresenterTest<PRESENTER, POJOCLASS, MODEL, VIEWINTERFACECLASS> {

	public ListPresenterTest(Class<?> viewInterfaceClass, Class<?> pojoClass) {
		super(viewInterfaceClass, pojoClass);
	}

	@Test
	public void testFillListWithAllPojos() throws Exception {
		List<POJOCLASS> list = new ArrayList<POJOCLASS>();
		when(mockedModel.getAll()).thenReturn(list);
	
		presenter.fillViewWithData();
		
		verify(mockedModel).getAll();
		verify(mockedView).displayData(list);
	}

}
