package se.fermitet.invest.model;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.Before;
import org.junit.Test;

import se.fermitet.invest.storage.Storage;

@SuppressWarnings("rawtypes")
public abstract class AbstractModelTest<MODELCLASS extends Model> {
	protected MODELCLASS model;
	protected Storage mockedStorage;
	private Class<? extends Model> modelClass;

	public AbstractModelTest(Class<? extends Model> modelClass) {
		super();
		this.modelClass = modelClass;
	}
	
	@Before
	public void setUp() throws Exception {
		this.model = createModelWithMockedStorage();
		mockedStorage = model.storage;
	}
	
	public abstract MODELCLASS createModelWithMockedStorage();
	
	@Test
	public void testNoPublicConstructor() throws Exception {
		Constructor<?>[] constructors = modelClass.getConstructors();
		for (Constructor constructor : constructors) {
			assertTrue("Found a public constructor: " + constructor, (constructor.getModifiers() & Modifier.PUBLIC) == 0);
		}
	}

}
