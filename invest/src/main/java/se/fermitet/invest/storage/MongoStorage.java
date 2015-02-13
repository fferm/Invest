package se.fermitet.invest.storage;

import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import se.fermitet.invest.domain.Stock;
import se.fermitet.invest.domain.Transaction;
import se.fermitet.invest.storage.converter.JodaLocalDateConverter;
import se.fermitet.invest.storage.converter.JodaMoneyConverter;

import com.mongodb.MongoClient;

class MongoStorage implements Storage {

	private final String DB_NAME = getDbName();
	static Morphia morphia;
	static MongoClient client;

	@SuppressWarnings("unchecked")
	public List<Stock> getAllStocks() {
		return (List<Stock>) getAll(Stock.class);
	}
	
	public Stock getStockById(UUID id) {
		Datastore ds = getDatastore();
		
		return ds.get(Stock.class, id);
	}

	public void saveStock(Stock stock) {
		save(stock);
	}

	public void deleteStock(Stock stock) {
		Datastore ds = getDatastore();
		
		ds.delete(stock);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAllTransactions() {
		return (List<Transaction>) getAll(Transaction.class);
	}

	public void saveTransaction(Transaction transaction) {
		save(transaction);
	}

	
	private List<?> getAll(Class<?> clazz) {
		return getDatastore().createQuery(clazz).asList();
	}
	
	private void save(Object obj) {
		getDatastore().save(obj);
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
			morphia.getMapper().getConverters().addConverter(JodaLocalDateConverter.class);
			morphia.getMapper().getConverters().addConverter(JodaMoneyConverter.class);
			morphia.mapPackage(Stock.class.getPackage().getName());
		}
	}


}
