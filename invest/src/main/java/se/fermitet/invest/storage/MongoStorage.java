package se.fermitet.invest.storage;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import se.fermitet.invest.domain.Stock;

import com.mongodb.MongoClient;

class MongoStorage implements Storage {

	private static final String DB_NAME = "invest";
	private static Morphia morphia;

	public List<Stock> getAllStocks() {
		try {
			Datastore ds = getDataStore();
			
			return ds.createQuery(Stock.class).asList();
		} catch (UnknownHostException e) {
			// TODO Better error handling
			e.printStackTrace();
			return null;
		}
	}
	
	private Datastore getDataStore() throws UnknownHostException {
		initMorphia();
		MongoClient client = new MongoClient();
		
		Datastore ds = morphia.createDatastore(client, DB_NAME);
		
		return ds;
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
