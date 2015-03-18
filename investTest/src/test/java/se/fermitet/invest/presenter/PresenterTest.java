package se.fermitet.invest.presenter;

import static org.mockito.Mockito.*;

import org.junit.Before;

import se.fermitet.invest.model.Model;
import se.fermitet.invest.viewinterface.InvestView;

public abstract class PresenterTest<PRESENTER extends Presenter<?, ?, ?>, POJO, MODEL extends Model<?>, VIEWINTERFACE extends InvestView> {
	protected PRESENTER presenter;
	protected MODEL mockedModel;
	protected VIEWINTERFACE mockedView;
	protected Class<?> viewInterfaceClass;
	protected Class<?> pojoClass;
	
	public PresenterTest(Class<?> viewInterfaceClass, Class<?> pojoClass) {
		super();
		this.viewInterfaceClass = viewInterfaceClass;
		this.pojoClass = pojoClass;
	}

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		this.mockedView = (VIEWINTERFACE) mock(viewInterfaceClass);
		this.presenter = createPresenter(mockedView);
		this.mockedModel = (MODEL) presenter.model;
		
		reset(mockedView);
		reset(mockedModel);
	}
	protected abstract PRESENTER createPresenter(VIEWINTERFACE view);

}
