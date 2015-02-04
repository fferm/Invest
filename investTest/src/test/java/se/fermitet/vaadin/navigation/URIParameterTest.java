package se.fermitet.vaadin.navigation;

import static org.junit.Assert.*;

import org.junit.Test;

import se.fermitet.vaadin.navigation.URIParameter.URIParameterException;

public class URIParameterTest {
	@Test
	public void testNameAndValueParameters() throws Exception {
		String name = "Name";
		String value = "Value";
		URIParameter parameter = new URIParameter(name, value);
		
		assertNotNull("not null", parameter);
		assertEquals("name", name, parameter.getName());
		assertEquals("value", value, parameter.getValue());
		assertEquals("toString", name + "=" + value, parameter.toString());
	}
	
	@Test
	public void testOnlyValueParameters() throws Exception {
		String value = "Value";
		URIParameter parameter = new URIParameter(value);
		
		assertNotNull("not null", parameter);
		assertNull("name", parameter.getName());
		assertEquals("value", value, parameter.getValue());
		assertEquals("toString", value, parameter.toString());
	}
	
	@Test
	public void testIllegalCharacters() throws Exception {
		runTestsForIllegalCharacter("/");
		runTestsForIllegalCharacter("&");
		runTestsForIllegalCharacter("=");
	}

	protected void runTestsForIllegalCharacter(String illegal) {
		try {
			new URIParameter("text" + illegal + "withIllegal");
			fail(illegal + " in value");
		} catch (URIParameterException e) {
			// OK
		} catch (Exception e) {
			e.printStackTrace();
			fail("Other exception from value.  Illegal = " + illegal);
		}
		
		try {
			new URIParameter("text" + illegal + "withIllegal", "Value");
			fail(illegal + " in name");
		} catch (URIParameterException e) {
			// OK
		} catch (Exception e) {
			e.printStackTrace();
			fail("Other exception from name.   Illegal = " + illegal);
		}
	}
	
	@Test
	public void testValueObject() throws Exception {
		URIParameter hasBoth = new URIParameter("Name", "Value");
		URIParameter hasValue = new URIParameter("Value");
		
		assertEquals("both equal to same", hasBoth, hasBoth);
		assertEquals("both equal to another object with same", hasBoth, new URIParameter("Name", "Value"));
		assertNotEquals("both equal to null name", hasBoth, hasValue);
		assertNotEquals("both equal to null value", hasBoth, new URIParameter("Name", null));
		assertNotEquals("both equal to both nulls", hasBoth, new URIParameter(null, null));
		assertNotEquals("both equal to other values", hasBoth, new URIParameter("Other", "Other"));
		assertNotEquals("both equal to null", hasBoth, null);
		assertNotEquals("both equal to object of other class", hasBoth, "TESTSTRING");
		
		assertEquals("value equal to same", hasValue, hasValue);
		assertEquals("value equal to another object with same", hasValue, new URIParameter("Value"));
		assertNotEquals("value equal to object of different values", hasValue, new URIParameter("Another"));
		
		assertTrue("Hash code of both", hasBoth.hashCode() == new URIParameter("Name", "Value").hashCode());
		assertTrue("Hash code of value", hasValue.hashCode() == new URIParameter("Value").hashCode());
	}
	
	@Test
	public void testParseString_nameAndValue() throws Exception {
		String testString = "param=value";
		
		URIParameter param = URIParameter.parse(testString);
		assertEquals("name and value", new URIParameter("param", "value"), param);
	}
	
	@Test
	public void testParseString_onlyValue() throws Exception {
		String testString = "only value";
		
		URIParameter param = URIParameter.parse(testString);
		assertEquals("name and value", new URIParameter(testString), param);
	}

}
