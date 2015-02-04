package se.fermitet.vaadin.navigation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.vaadin.navigation.DirectionalNavigator.DirectionalNavigatorException;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

public class DirectionalNavigatorTest {
	private static final String TEST_VIEW1 = "TestView1";
	private static final String TEST_VIEW2 = "TestView2";
	private static final String TEST_VIEW3 = "TestView3";
	private static final String TEST_VIEW4 = "TestView4";
	private static final String TEST_VIEW5 = "TestView5";

	private DirectionalNavigator objUnderTest;
	private Navigator vaadinNavigator;

	@Before
	public void setUp() throws Exception {
		this.objUnderTest = new TestDirectionalNavigator(mock(UI.class), mock(SingleComponentContainer.class));
		this.vaadinNavigator = this.objUnderTest.navigator;

		objUnderTest.addView(TEST_VIEW1, TestView1.class);
		objUnderTest.addView(TEST_VIEW2, TestView2.class);
		objUnderTest.addView(TEST_VIEW3, TestView3.class);
		objUnderTest.addView(TEST_VIEW4, TestView4.class);
		objUnderTest.addView(TEST_VIEW5, TestView5.class);

		reset(vaadinNavigator);
	}

	@Test
	public void testAddView() throws Exception {
		String name = "NAME";
		Class<? extends View> clz = View.class;

		objUnderTest.addView(name, clz);

		verify(vaadinNavigator).addView(name, clz);
	}

	@Test
	public void testNavigateWithoutParameters() throws Exception {
		objUnderTest.navigateTo(TEST_VIEW1);

		verify(vaadinNavigator).navigateTo(TEST_VIEW1);
	}

	@Test
	public void testNavigateWithSingleParamter() throws Exception {
		String value = "Value";
		URIParameter param = new URIParameter(value);

		objUnderTest.navigateTo(TEST_VIEW3, param);

		verify(vaadinNavigator).navigateTo(TEST_VIEW3 + DirectionalNavigator.PARAMETERS_START + param.toString());
	}

	@Test
	public void testNavigateWithParameters() throws Exception {
		List<URIParameter> parameters = new ArrayList<URIParameter>();

		URIParameter param1 = new URIParameter("Name1", "Value1");
		URIParameter param2 = new URIParameter("Name2", "Value2");
		parameters.add(param1);
		parameters.add(param2);

		objUnderTest.navigateTo(TEST_VIEW2, parameters);

		verify(vaadinNavigator).navigateTo(TEST_VIEW2 + DirectionalNavigator.PARAMETERS_START + param1.toString() + DirectionalNavigator.PARAMETERS_SEPARATOR + param2.toString());
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testNavigatingToUndeclaredViewIllegal_withoutParameters() throws Exception {
		objUnderTest.navigateTo("NOT DEFINED NAME");
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testNavigatingToUndeclaredViewIllegal_withSingleParameters() throws Exception {
		objUnderTest.navigateTo("NOT DEFINED NAME", new URIParameter(null, null));
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testNavigatingToUndeclaredViewIllegal_withParameters() throws Exception {
		objUnderTest.navigateTo("NOT DEFINED NAME", new ArrayList<URIParameter>());
	}

	@Test
	public void testBackNavigation() throws Exception {
		URIParameter param = new URIParameter("TST", "TST");

		objUnderTest.navigateTo(TEST_VIEW1, param);
		objUnderTest.navigateTo(TEST_VIEW2);

		reset(vaadinNavigator);

		objUnderTest.navigateBack();

		verify(vaadinNavigator).navigateTo(TEST_VIEW1 + DirectionalNavigator.PARAMETERS_START + param.toString());
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testBackNavigationBeyondLimit() throws Exception {
		objUnderTest.navigateTo(TEST_VIEW3);

		objUnderTest.navigateBack();
	}

	@Test
	public void testBackNavigationTwice() throws Exception {
		objUnderTest.navigateTo(TEST_VIEW1);
		objUnderTest.navigateTo(TEST_VIEW2);
		objUnderTest.navigateTo(TEST_VIEW3);

		objUnderTest.navigateBack();

		reset(vaadinNavigator);

		objUnderTest.navigateBack();

		verify(vaadinNavigator).navigateTo(TEST_VIEW1);
	}

	@Test
	public void testForwardNavigation() throws Exception {
		objUnderTest.navigateTo(TEST_VIEW1);
		objUnderTest.navigateTo(TEST_VIEW2);

		objUnderTest.navigateBack();

		reset(vaadinNavigator);

		objUnderTest.navigateForward();

		verify(vaadinNavigator).navigateTo(TEST_VIEW2);
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testForwardNavigationBeyondLimit() {
		objUnderTest.navigateTo(TEST_VIEW1);

		objUnderTest.navigateForward();
	}

	@Test
	public void testForwardNavigationTwice() throws Exception {
		objUnderTest.navigateTo(TEST_VIEW1);
		objUnderTest.navigateTo(TEST_VIEW2);
		objUnderTest.navigateTo(TEST_VIEW3);

		objUnderTest.navigateBack();
		objUnderTest.navigateBack();

		objUnderTest.navigateForward();

		reset(vaadinNavigator);

		objUnderTest.navigateForward();

		verify(vaadinNavigator).navigateTo(TEST_VIEW3);
	}

	@Test
	public void testBackNavigationThenNormalNavigationShouldForgetForwardStack() throws Exception {
		objUnderTest.navigateTo(TEST_VIEW1);
		objUnderTest.navigateTo(TEST_VIEW2);
		objUnderTest.navigateTo(TEST_VIEW3);

		objUnderTest.navigateBack();
		objUnderTest.navigateBack();

		reset(vaadinNavigator);
		objUnderTest.navigateTo(TEST_VIEW5);
		verify(vaadinNavigator).navigateTo(TEST_VIEW5);

		reset(vaadinNavigator);
		objUnderTest.navigateBack();
		verify(vaadinNavigator).navigateTo(TEST_VIEW1);

		reset(vaadinNavigator);
		objUnderTest.navigateForward();
		verify(vaadinNavigator).navigateTo(TEST_VIEW5);

		reset(vaadinNavigator);
		try {
			objUnderTest.navigateForward();
			fail("Should give exception");
		} catch (DirectionalNavigatorException e) {
			// OK
		} catch (Exception e) {
			e.printStackTrace();
			fail("Other exception");
		}
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testBackNavigationFromZero() throws Exception {
		objUnderTest.navigateBack();
	}

	@Test(expected = DirectionalNavigatorException.class)
	public void testForwardNavigationFromZero() throws Exception {
		objUnderTest.navigateForward();
	}


	private class TestDirectionalNavigator extends DirectionalNavigator {

		public TestDirectionalNavigator(UI ui, SingleComponentContainer container) {
			super(ui, container);
		}

		@Override
		protected Navigator createNavigator(UI ui, SingleComponentContainer container) {
			return mock(Navigator.class);
		}

	}

	@SuppressWarnings("serial")
	private abstract class TestView implements View {
		public void enter(ViewChangeEvent event) {}
	}

	@SuppressWarnings("serial")
	private class TestView1 extends TestView {}

	@SuppressWarnings("serial")
	private class TestView2 extends TestView {}

	@SuppressWarnings("serial")
	private class TestView3 extends TestView {}

	@SuppressWarnings("serial")
	private class TestView4 extends TestView {}

	@SuppressWarnings("serial")
	private class TestView5 extends TestView {}

}