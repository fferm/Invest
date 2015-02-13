package se.fermitet.invest.storage.converter;

import static org.junit.Assert.*;

import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JodaLocalDateConverterTest extends ConverterTest<JodaLocalDateConverter> {

	public JodaLocalDateConverterTest() {
		super(LocalDate.class);
	}
	
	protected JodaLocalDateConverter createConverter() {
		return new JodaLocalDateConverter();
	}
	
	@Test
	public void testNormalEncoding() throws Exception {
		LocalDate ld = LocalDate.now();
		Date date = ld.toDate();
		
		Object res = converter.encode(ld);
		
		assertTrue("instanceof.  Was: " + res.getClass(), res instanceof DBObject);
		
		DBObject dbObj = (DBObject) res;
		
		assertEquals("date", date, dbObj.get(JodaLocalDateConverter.DATE_PROPERTY));
	}
	
	@Test
	public void testNormalDecoding() throws Exception {
		LocalDate ld = LocalDate.now();
		Date date = ld.toDate();
		
		DBObject dbObj = new BasicDBObject(JodaLocalDateConverter.DATE_PROPERTY, date);
		
		Object res = converter.decode(LocalDate.class, dbObj);
		
		assertTrue("instanceof.  Was: " + res.getClass(), res instanceof LocalDate);
		
		assertEquals("date", ld, res);
	}
}
