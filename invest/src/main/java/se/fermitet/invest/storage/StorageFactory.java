package se.fermitet.invest.storage;

public class StorageFactory {
	private static StorageFactory instance;
	private static Storage storage;
	
	private StorageFactory() {
		super();
	}
	
	public static StorageFactory instance() {
		if (instance == null)  {
			instance = new StorageFactory();
		}
		return instance;
	}
	
	public Storage getStorage() {
		if (storage == null) {
			storage = createStorage();
		}
		return storage;
	}
	
	protected Storage createStorage() {
		return new MongoStorage();
	}
}
