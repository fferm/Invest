package se.fermitet.invest.storage;

import java.net.UnknownHostException;
import java.util.List;

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

	public void saveStock(Stock stock) {
		Datastore ds = getDatastore();

		ds.save(stock);
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

	//	public static void main(String[] args) {
	//		MongoStorage obj = new MongoStorage();
	//		obj.run();
	//	}
	//	
	//	private void run() {
	//		try {
	//			Datastore ds = getDataStore();
	//
	//			Stock s1 = new Stock("AXIS").setName("Axis");
	//			Stock s2 = new Stock("SHB B").setName("Handelsbanken B");
	//			
	//			ds.save(s1);
	//			ds.save(s2);
	//			
	//			System.out.println("Saved");
	//		} catch (UnknownHostException e) {
	//			e.printStackTrace();
	//		}
	//	}
	//	
}
