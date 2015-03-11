package se.fermitet.invest.presenter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.SinglePOJOView;

@SuppressWarnings("rawtypes")
public abstract class SinglePOJOPresenterTest<PRESENTER extends SinglePOJOPresenter, POJOCLASS, MODEL extends Model<POJOCLASS>, VIEWINTERFACECLASS extends SinglePOJOView> {
	protected PRESENTER presenter;
	protected MODEL mockedModel;
	protected VIEWINTERFACECLASS mockedView;
	private Class<?> viewInterfaceClass;
	private Class<?> pojoClass;
	
	public SinglePOJOPresenterTest(Class<?> viewInterfaceClass, Class<?> pojoClass) {
		super();
		this.viewInterfaceClass = viewInterfaceClass;
		this.pojoClass = pojoClass;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		this.mockedView = (VIEWINTERFACECLASS) mock(viewInterfaceClass);
		this.presenter = createPresenter(mockedView);
		this.mockedModel = (MODEL) presenter.model;
	}

	protected abstract void assessDefaultDO(POJOCLASS obj);
	protected abstract PRESENTER createPresenter(SinglePOJOView view);
	
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
