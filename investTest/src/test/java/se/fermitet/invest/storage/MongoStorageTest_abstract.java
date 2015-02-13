package se.fermitet.invest.storage;

import java.net.UnknownHostException;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.MappedClass;

import com.mongodb.MongoClient;

public abstract class MongoStorageTest_abstract {
	protected Storage objUnderTest;
	
	@Before
	public void setUp() throws Exception {
		objUnderTest = new TestMongoStorage();
		((TestMongoStorage) objUnderTest).cleanUpDatabase();
	}
	
	@After
	public void tearDown() throws Exception {
		((TestMongoStorage) objUnderTest).cleanUpDatabase();
	}
	

}

class TestMongoStorage extends MongoStorage {
	@Override
	protected String getDbName() {
		return "testInvest";
	}

	public void cleanUpDatabase() throws Exception {
		Datastore ds = getDatastore();

		Collection<MappedClass> mappedClasses = morphia.getMapper().getMappedClasses();
		for (MappedClass mappedClass : mappedClasses) {
			Class<?> pojoClass = mappedClass.getClazz();
			ds.delete(ds.find(pojoClass));
		}
	}
}

class ExceptionMongoStorage extends TestMongoStorage {
	private String message;

	public ExceptionMongoStorage(String exceptionMessage) {
		super();
		this.message = exceptionMessage;
		client = null;
	}
	
	@Override
	protected MongoClient createMongoClient() throws UnknownHostException {
		throw new UnknownHostException(message);
	}
}

