package se.fermitet.invest.webui.views;

import org.junit.Before;

import se.fermitet.invest.presenter.SinglePOJOPresenter;

@SuppressWarnings("rawtypes")
public abstract class SinglePOJOViewImplTest<VIEWIMPL extends SinglePOJOViewImpl, PRESENTER extends SinglePOJOPresenter> {
	protected VIEWIMPL view;
	protected PRESENTER mockedPresenter;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		view = createViewImpl();
		mockedPresenter = (PRESENTER) view.presenter;
	}

	protected abstract VIEWIMPL createViewImpl();
}
