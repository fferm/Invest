package se.fermitet.invest.storage.converter;

import java.util.Map;

import org.joda.money.Money;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import se.fermitet.invest.storage.StorageException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JodaMoneyConverter extends TypeConverter {

	static final String AMOUNT_PROPERTY = "amount";
	static final String CURRENCYCODE_PROPERTY = "currencycode";

	public JodaMoneyConverter() {
		super(Money.class);
	}

	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		if (value == null) return null;
		if (! (value instanceof Money)) throw new StorageException("Expected a Money.  Instead it was a " + value.getClass().getName());

		DBObject res = new BasicDBObject();
		Money money = (Money) value;

		res.put(AMOUNT_PROPERTY, money.getAmount().toString());
		res.put(CURRENCYCODE_PROPERTY, money.getCurrencyUnit().getCode());

		return res;
	}

	@Override
	public Object decode(Class<?> targetClass, Object fromDBObject,	MappedField optionalExtraInfo) {
		if (fromDBObject == null) return null;

		if (! (fromDBObject instanceof Map)) {
			throw new StorageException("Did not expect an object of class " + fromDBObject.getClass().getName());
		}

		@SuppressWarnings("rawtypes")
		Map mapFromDb = (Map) fromDBObject;

		String amountString = (String) mapFromDb.get(AMOUNT_PROPERTY);
		String currencyString = (String) mapFromDb.get(CURRENCYCODE_PROPERTY);

		return Money.parse(currencyString + " " + amountString);
	}

}
