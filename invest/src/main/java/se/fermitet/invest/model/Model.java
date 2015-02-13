package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public abstract class Model<POJOCLASS> {
	Storage storage;

	protected Model() {
		super();
		storage = createStorage();
	}
	
	protected Storage createStorage() {
		return new StorageFactory().getStorage();
	}
	
	public abstract List<POJOCLASS> getAll();
	public abstract POJOCLASS getById(UUID id);
	public abstract void save(POJOCLASS obj);
	public abstract void delete(POJOCLASS obj);

	
}
