package se.fermitet.invest.storage.converter;

import static org.junit.Assert.*;

import org.joda.money.Money;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class JodaMoneyConverterTest extends ConverterTest<JodaMoneyConverter>{

	public JodaMoneyConverterTest() {
		super(Money.class);
	}

	@Override
	protected JodaMoneyConverter createConverter() {
		return new JodaMoneyConverter();
	}
	
	@Test
	public void testNormalEncoding() throws Exception {
		String currencyCode = "SEK";
		String amountString = "100.54";
		Money money = Money.parse(currencyCode + " " + amountString);
		
		Object res = converter.encode(money);
		
		assertTrue("instanceof.  Was: " + res.getClass(), res instanceof DBObject);
		
		DBObject dbObj = (DBObject) res;

		assertEquals("amount", amountString, dbObj.get(JodaMoneyConverter.AMOUNT_PROPERTY));
		assertEquals("currencyCode", currencyCode, dbObj.get(JodaMoneyConverter.CURRENCYCODE_PROPERTY));
	}
	
	@Test
	public void testNormalDecoding() throws Exception {
		Money expected = Money.parse("SEK 200.32");
		
		DBObject dbObj = new BasicDBObject();
		dbObj.put(JodaMoneyConverter.AMOUNT_PROPERTY, expected.getAmount().toString());
		dbObj.put(JodaMoneyConverter.CURRENCYCODE_PROPERTY, expected.getCurrencyUnit().toString());
		
		Object res = converter.decode(Money.class, dbObj);
		
		assertTrue("instanceof.  Was: " + res.getClass(), res instanceof Money);
		
		Money money = (Money) res;
		
		assertEquals("money", expected, money);
	}


}
