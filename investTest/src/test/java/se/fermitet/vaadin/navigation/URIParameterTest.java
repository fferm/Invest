package se.fermitet.vaadin.navigation;

import static org.junit.Assert.*;

import org.junit.Test;

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
	
	@Test(expected=URIParameterException.class)
	public void testNullNameIllegal() throws Exception {
		new URIParameter(null, "value");
	}
	
	@Test(expected=URIParameterException.class)
	public void testEmptyNameIllegal() throws Exception {
		new URIParameter("", "value");
	}
	
	@Test(expected=URIParameterException.class)
	public void testNullValueIllegal() throws Exception {
		new URIParameter("Name", null);
	}
	
	@Test(expected=URIParameterException.class)
	public void testEmptyValueIllegal() throws Exception {
		new URIParameter("Name", "");
	}
	
	@Test
	public void testIllegalCharacters() throws Exception {
		runTestsForIllegalCharacter("/");
		runTestsForIllegalCharacter("&");
		runTestsForIllegalCharacter("=");
	}

	protected void runTestsForIllegalCharacter(String illegal) {
		try {
			new URIParameter("Name", "text" + illegal + "withIllegal");
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
		
		assertEquals("both equal to same", hasBoth, hasBoth);
		assertEquals("both equal to another object with same", hasBoth, new URIParameter("Name", "Value"));
		assertNotEquals("both equal to other values", hasBoth, new URIParameter("Other", "Other"));
		assertNotEquals("both equal to null", hasBoth, null);
		assertNotEquals("both equal to object of other class", hasBoth, "TESTSTRING");
		
		assertTrue("Hash code of both", hasBoth.hashCode() == new URIParameter("Name", "Value").hashCode());
	}
	
	@Test
	public void testParseString_nameAndValue() throws Exception {
		String testString = "param=value";
		
		URIParameter param = URIParameter.parse(testString);
		assertEquals("name and value", new URIParameter("param", "value"), param);
	}
	
	@Test(expected=URIParameterException.class)
	public void testParseString_withoutSeparator() throws Exception {
		URIParameter.parse("hello");
	}
	
	@Test(expected=URIParameterException.class)
	public void testParseString_emptyName() throws Exception {
		URIParameter.parse("=value");
	}
	
	@Test(expected=URIParameterException.class)
	public void testParseString_emptyValue() throws Exception {
		URIParameter.parse("Name=");
	}

}
