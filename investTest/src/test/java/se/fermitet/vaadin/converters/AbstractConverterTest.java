package se.fermitet.vaadin.converters;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public abstract class AbstractConverterTest<PRESENTATION, MODEL, CONVERTER extends AbstractConverter<PRESENTATION, MODEL>> {

	protected CONVERTER converter;
	
	@Before
	public void setUp() throws Exception {
		this.converter = createConverter();
	}
	
	protected abstract CONVERTER createConverter();
	protected abstract Class<MODEL> getExpectedModelClass();
	protected abstract Class<PRESENTATION> getExpectedPresentationClass();
	protected abstract List<ConverterTestCase> getTestCases();
	
	@Test
	public void testGetClasses() throws Exception {
		assertEquals("model", getExpectedModelClass(), converter.getModelType());
		assertEquals("presentation", getExpectedPresentationClass(), converter.getPresentationType());
	}
	
	@Test
	public void testConversions() throws Exception {
		List<AbstractConverterTest<PRESENTATION, MODEL, CONVERTER>.ConverterTestCase> testCases = getTestCases();
		testCases.add(new ConverterTestCase(null, null));
		
		for (ConverterTestCase testCase : testCases) {
			assertEquals("to model", testCase.modelObject, converter.convertToModel(testCase.presentationObject,  null, null));
			assertEquals("to presentation", testCase.presentationObject, converter.convertToPresentation(testCase.modelObject, null, null));
		}
	}
	
	class ConverterTestCase {
		MODEL modelObject;
		PRESENTATION presentationObject;
		
		public ConverterTestCase(MODEL modelObject, PRESENTATION presentationObject) {
			super();
			this.modelObject = modelObject;
			this.presentationObject = presentationObject;
		}
		
	}
}

