package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.SinglePOJOView;

@SuppressWarnings("rawtypes")
public abstract class SinglePOJOPresenterTest<PRESENTER extends SinglePOJOPresenter, POJOCLASS, MODEL extends Model<POJOCLASS>, VIEWINTERFACECLASS extends SinglePOJOView> extends PresenterTest<PRESENTER, POJOCLASS, MODEL, VIEWINTERFACECLASS> {
	public SinglePOJOPresenterTest(Class<?> viewInterfaceClass,	Class<?> pojoClass) {
		super(viewInterfaceClass, pojoClass);
	}

	protected abstract void assessDefaultDO(POJOCLASS obj);
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetPOJOWithNullArgument() throws Exception {
		POJOCLASS answer = (POJOCLASS) presenter.getDOBasedOnIdString(null);
		
		assessDefaultDO(answer);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPOJOWithZeroLenghtArgument() throws Exception {
		POJOCLASS answer = (POJOCLASS) presenter.getDOBasedOnIdString("");
		
		assessDefaultDO(answer);
	}
	
	@Test
	public void testGetTransactionWithData() throws Exception {
		UUID id = UUID.randomUUID();
		POJOCLASS expected = createDefaultPOJO();
		
		when(mockedModel.getById(id)).thenReturn(expected);
		
		@SuppressWarnings("unchecked")
		POJOCLASS answer = (POJOCLASS) presenter.getDOBasedOnIdString(id.toString());
		
		assertSame(expected, answer);
	}
	
	@Test
	public void testCancelNavigatesToListView() throws Exception {
		presenter.onCancelButtonClick();
		
		verify(mockedView).navigateBack();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testOkSavesAndNavigatesBack() throws Exception {
		POJOCLASS testData = createDefaultPOJO();
		
		presenter.onOkButtonClick(testData);
		
		verify(mockedModel).save(testData);
		verify(mockedView).navigateBack();
	}



	@SuppressWarnings("unchecked")
	private POJOCLASS createDefaultPOJO() {
		try {
			return (POJOCLASS) pojoClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			fail("Exception: " + e.getMessage());
			return null;
		}
	}


}
