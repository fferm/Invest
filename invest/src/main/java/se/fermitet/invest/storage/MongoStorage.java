package se.fermitet.invest.storage;

import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import se.fermitet.invest.domain.Portfolio;
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
		return (Stock) getById(Stock.class, id);
	}

	public Stock getStockBySymbol(String symbol) {
		return getDatastore().createQuery(Stock.class).field("symbol").equal(symbol).get();
	}

	public void saveStock(Stock stock) {
		save(stock);
	}

	public void deleteStock(Stock stock) {
		delete(stock);
	}


	@SuppressWarnings("unchecked")
	public List<Transaction> getAllTransactions() {
		return (List<Transaction>) getAll(Transaction.class);
	}

	public Transaction getTransactionById(UUID id) {
		return (Transaction) getById(Transaction.class, id);
	}

	public void saveTransaction(Transaction transaction) {
		save(transaction);
	}

	public void deleteTransaction(Transaction t1) {
		delete(t1);
	}

	public List<Transaction> getTransactionsForStock(Stock stock) {
		return getDatastore().createQuery(Transaction.class).field("stock").equal(stock).asList();
	}

	public List<Transaction> getTransactionsForPortfolio(Portfolio portfolio) {
		return getDatastore().createQuery(Transaction.class).field("portfolio").equal(portfolio).asList();
	}



	@SuppressWarnings("unchecked")
	public List<Portfolio> getAllPortfolios() {
		return (List<Portfolio>) getAll(Portfolio.class);
	}

	public Portfolio getPortfolioById(UUID id) {
		return (Portfolio) getById(Portfolio.class, id);
	}

	public void deletePortfolio(Portfolio toDelete) {
		delete(toDelete);
	}

	public void savePortfolio(Portfolio toSave) {
		save(toSave);
	}

	public Portfolio getPortfolioByName(String name) {
		return getDatastore().createQuery(Portfolio.class).field("name").equal(name).get();
	}



	private List<?> getAll(Class<?> clazz) {
		return getDatastore().createQuery(clazz).asList();
	}

	private Object getById(Class<?> clazz, UUID id) {
		return getDatastore().get(clazz, id);
	}

	private void save(Object obj) {
		getDatastore().save(obj);
	}

	private void delete(Object obj) {
		getDatastore().delete(obj);
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
