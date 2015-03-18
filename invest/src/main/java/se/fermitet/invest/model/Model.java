package se.fermitet.invest.model;

import java.util.List;
import java.util.UUID;

import se.fermitet.invest.storage.Storage;
import se.fermitet.invest.storage.StorageFactory;

public abstract class Model<POJO> {
	Storage storage;

	protected Model() {
		super();
		storage = createStorage();
	}
	
	protected Storage createStorage() {
		return new StorageFactory().getStorage();
	}
	
	public abstract List<POJO> getAll();
	public abstract POJO getById(UUID id);
	public abstract void save(POJO obj);
	public abstract void delete(POJO obj);

	
}
