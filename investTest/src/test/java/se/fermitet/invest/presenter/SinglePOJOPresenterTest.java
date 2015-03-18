package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Test;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.SinglePOJOView;

@SuppressWarnings("rawtypes")
public abstract class SinglePOJOPresenterTest<PRESENTER extends SinglePOJOPresenter, POJO, MODEL extends Model<POJO>, VIEWINTERFACE extends SinglePOJOView> extends PresenterTest<PRESENTER, POJO, MODEL, VIEWINTERFACE> {
	public SinglePOJOPresenterTest(Class<?> viewInterfaceClass,	Class<?> pojoClass) {
		super(viewInterfaceClass, pojoClass);
	}

	protected abstract void assessDefaultDO(POJO obj);
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetPOJOWithNullArgument() throws Exception {
		POJO answer = (POJO) presenter.getDOBasedOnIdString(null);
		
		assessDefaultDO(answer);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPOJOWithZeroLenghtArgument() throws Exception {
		POJO answer = (POJO) presenter.getDOBasedOnIdString("");
		
		assessDefaultDO(answer);
	}
	
	@Test
	public void testGetTransactionWithData() throws Exception {
		UUID id = UUID.randomUUID();
		POJO expected = createDefaultPOJO();
		
		when(mockedModel.getById(id)).thenReturn(expected);
		
		@SuppressWarnings("unchecked")
		POJO answer = (POJO) presenter.getDOBasedOnIdString(id.toString());
		
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
		POJO testData = createDefaultPOJO();
		
		presenter.onOkButtonClick(testData);
		
		verify(mockedModel).save(testData);
		verify(mockedView).navigateBack();
	}



	@SuppressWarnings("unchecked")
	private POJO createDefaultPOJO() {
		try {
			return (POJO) pojoClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			fail("Exception: " + e.getMessage());
			return null;
		}
	}


}
