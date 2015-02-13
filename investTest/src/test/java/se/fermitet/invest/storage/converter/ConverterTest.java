package se.fermitet.invest.storage.converter;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.converters.TypeConverter;

import se.fermitet.invest.storage.StorageException;

public abstract class ConverterTest<CONVERTER extends TypeConverter> {
	protected CONVERTER converter;
	private Class<?> modelClass;

	protected ConverterTest(Class<?> modelClass) {
		this.modelClass = modelClass;
	}
	
	@Before
	public void setUp() throws Exception {
		this.converter = createConverter();
	}
	
	protected abstract CONVERTER createConverter();
	
	@Test
	public void testConstructor() throws Exception {
		CONVERTER converter = createConverter();
		assertNotNull(converter);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testIsSupported() throws Exception {
		List<Class> supportTypes = Arrays.asList(converter.getSupportTypes());
		
		assertTrue("Contains", supportTypes.contains(modelClass));
	}
	
	@Test
	public void testEncodingNull() throws Exception {
		Object res = converter.encode(null);
		
		assertNull(res);
	}
	
	@Test(expected=StorageException.class)
	public void testEncodingSomeOtherClass() throws Exception {
		converter.encode("STRING");
	}
	
	@Test
	public void testDecodingNull() throws Exception {
		Object res = converter.decode(modelClass, null);
		
		assertNull(res);
	}
	
	@Test(expected=StorageException.class)
	public void testDecodeSomeOtherClass() throws Exception {
		converter.decode(modelClass, "STRING");
	}

	

	

	

}
