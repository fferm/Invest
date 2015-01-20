package se.fermitet.invest.storage;

public class StorageFactory {
	private static Storage storage;
	
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
