package se.fermitet.invest.storage.converter;

import java.util.Date;
import java.util.Map;

import org.joda.time.LocalDate;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;

import se.fermitet.invest.storage.StorageException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JodaLocalDateConverter extends TypeConverter {

	static final String DATE_PROPERTY = "date";

	public JodaLocalDateConverter() {
		super(LocalDate.class);
	}

	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
		if (value == null) return null;
		if (! (value instanceof LocalDate)) throw new StorageException("Expected a LocalDate.  Instead it was a " + value.getClass().getName());

		LocalDate ld = (LocalDate) value;

		DBObject res = new BasicDBObject(DATE_PROPERTY, ld.toDate());

		return res;
	}

	@Override
	public Object decode(Class<?> targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
		if (fromDBObject == null) return null;

		if (! (fromDBObject instanceof Map)) {
			throw new StorageException("Did not expect an object of class " + fromDBObject.getClass().getName());
		}

		@SuppressWarnings("rawtypes")
		Map mapFromDb = (Map) fromDBObject;

		Date dateProp = (Date) mapFromDb.get(DATE_PROPERTY);
		return new LocalDate(dateProp);
	}

}
