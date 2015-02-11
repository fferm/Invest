package se.fermitet.vaadin.widgets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vaadin.ui.ComboBox;

@SuppressWarnings("rawtypes")
public class POJOComboBoxAdapterTest extends POJOAbstractSelectAdapterTest<POJOComboBoxAdapter>{

	@Override
	protected POJOComboBoxAdapter createAdapter() {
		return new POJOComboBoxAdapter<TestPOJO>(TestPOJO.class);
	}
	
	@Test
	public void testSimpleConstructor() throws Exception {
		POJOComboBoxAdapter<TestPOJO> adapter = new POJOComboBoxAdapter<TestPOJO>(TestPOJO.class);
		
		assertNotNull(adapter);
	}
	
	@Test
	public void testConstructorWithCaption() throws Exception {
		String myCaption = "MY TEST CAPTION";
		POJOComboBoxAdapter<TestPOJO> adapter = new POJOComboBoxAdapter<TestPOJO>(TestPOJO.class, myCaption);

		assertEquals(myCaption, adapter.getCombo().getCaption());
	}

	@Test
	public void testGetComboBox() throws Exception {
		ComboBox combo = adapter.getCombo();
		
		assertNotNull(combo);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayedData_strAttribute() throws Exception {
		adapter.setData(testData);
		adapter.setDisplayColumn("strAttribute");

		int i = 0;
		for (Object itemId : adapter.getCombo().getItemIds()) {
			TestPOJO pojo = testData.get(i);
			
			String expected = pojo.getStrAttribute();
			
			String itemCaption = adapter.getCombo().getItemCaption(itemId);
			if (expected == null) assertEquals("(null) i: " + i, "", itemCaption);  // TODO, should it be a check for null instead
			else if (expected.length() == 0) assertEquals("(empty) i: " + i, "", itemCaption);
			else assertEquals("(non null) i: " + i, expected, itemCaption);
			
			i++;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayedData_intAttribute() throws Exception {
		adapter.setData(testData);
		adapter.setDisplayColumn("intAttribute");

		int i = 0;
		for (Object itemId : adapter.getCombo().getItemIds()) {
			TestPOJO pojo = testData.get(i);
			
			String expected = "" + pojo.getIntAttribute();
			
			String itemCaption = adapter.getCombo().getItemCaption(itemId);
			assertEquals("i: " + i, expected, itemCaption);
			
			i++;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayedData_linkedAttribute() throws Exception {
		adapter.setData(testData);
		adapter.setDisplayColumn("linkedAttribute.stringAttribute");

		int i = 0;
		for (Object itemId : adapter.getCombo().getItemIds()) {
			TestPOJO pojo = testData.get(i);
			
			String expected = "";
			
			TestPOJO_Linked linkedAttribute = pojo.getLinkedAttribute();
			if (linkedAttribute != null) {
				String stringAttribute = linkedAttribute.getStringAttribute();
				
				if (stringAttribute != null) expected = stringAttribute;
			}
			
			String itemCaption = adapter.getCombo().getItemCaption(itemId);
			assertEquals("i: " + i, expected, itemCaption);
			
			i++;
		}
	}
	
	@Test
	public void testGetDisplayColumn() throws Exception {
		adapter.setDisplayColumn("strAttribute");
		assertEquals("strAttribute", adapter.getDisplayColumn());
	}
	

	@Override
	protected void defineVisibleData() {
		adapter.setDisplayColumn("strAttribute");
	}

	@Override
	protected void defineIllegalColumn() {
		adapter.setDisplayColumn("ILLEGAL");
		
	}

	@Override
	protected void defineIllegalNestedColumn() {
		adapter.setDisplayColumn("linkedAttribute.ILLEGAL");
	}



}
