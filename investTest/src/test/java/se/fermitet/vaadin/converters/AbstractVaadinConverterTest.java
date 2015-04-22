package se.fermitet.vaadin.converters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstractVaadinConverterTest<PRESENTATION, MODEL, CONVERTER extends AbstractVaadinConverter<PRESENTATION, MODEL>> {

	protected CONVERTER converter;
	
	@Before
	public void setUp() throws Exception {
		this.converter = createConverter();
	}
	
	protected abstract CONVERTER createConverter();
	protected abstract Class<MODEL> getExpectedModelClass();
	protected abstract Class<PRESENTATION> getExpectedPresentationClass();
	protected abstract List<VaadinConverterTestCase> getTestCases();
	
	@Test
	public void testGetClasses() throws Exception {
		assertEquals("model", getExpectedModelClass(), converter.getModelType());
		assertEquals("presentation", getExpectedPresentationClass(), converter.getPresentationType());
	}
	
	@Test
	public void testConversions() throws Exception {
		List<AbstractVaadinConverterTest<PRESENTATION, MODEL, CONVERTER>.VaadinConverterTestCase> testCases = getTestCases();
		testCases.add(new VaadinConverterTestCase(null, null));
		
		for (VaadinConverterTestCase testCase : testCases) {
			assertEquals("to model", testCase.modelObject, converter.convertToModel(testCase.presentationObject,  null, null));
			assertEquals("to presentation", testCase.presentationObject, converter.convertToPresentation(testCase.modelObject, null, null));
		}
	}
	
	class VaadinConverterTestCase {
		MODEL modelObject;
		PRESENTATION presentationObject;
		
		public VaadinConverterTestCase(MODEL modelObject, PRESENTATION presentationObject) {
			super();
			this.modelObject = modelObject;
			this.presentationObject = presentationObject;
		}
		
	}
}

