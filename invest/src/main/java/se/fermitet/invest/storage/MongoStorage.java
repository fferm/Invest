package se.fermitet.invest.storage;

import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import se.fermitet.invest.domain.Stock;

import com.mongodb.MongoClient;

class MongoStorage implements Storage {

	private final String DB_NAME = getDbName();
	static Morphia morphia;
	static MongoClient client;

	public List<Stock> getAllStocks() {
		Datastore ds = getDatastore();

		return ds.createQuery(Stock.class).asList();
	}
	
	public Stock getStockById(UUID id) {
		Datastore ds = getDatastore();
		
		return ds.get(Stock.class, id);
	}

	public void saveStock(Stock stock) {
		Datastore ds = getDatastore();

		ds.save(stock);
	}
	
	public void deleteStock(Stock stock) {
		Datastore ds = getDatastore();
		
		ds.delete(stock);
	}

	protected String getDbName() {
		return "invest";
	}

	protected Datastore getDatastore() {
		init();

		Datastore ds = morphia.createDatastore(client, DB_NAME);

		return ds;
	}

	private void init() {
		initClient();
		initMorphia();
	}

	private void initClient() {
		try {
			if (client == null) {
				client = createMongoClient();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new StorageException(e.getMessage(), e);
		}
	}

	protected MongoClient createMongoClient() throws UnknownHostException {
		return new MongoClient();
	}

	private void initMorphia() {
		if (morphia == null) {
			morphia = new Morphia();
			morphia.mapPackage(Stock.class.getPackage().getName());
		}
	}

}
