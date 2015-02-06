package se.fermitet.vaadin.widgets;

public class TestPOJO {
	private String strAttribute;
	private int intAttribute;
	private TestPOJO_Linked linkedAttribute;

	public TestPOJO() {
		this(null);
	}
	
	public TestPOJO(String strAttribute) {
		this(strAttribute, 0, null);
	}
	
	public TestPOJO(String strAttribute, int intAttribute, TestPOJO_Linked linkedAttribute) {
		super();
		this.setStrAttribute(strAttribute);
		this.setIntAttribute(intAttribute);
		this.setLinkedAttribute(linkedAttribute);
	}
	
	public int getIntAttribute() {
		return intAttribute;
	}
	public void setIntAttribute(int intAttribute) {
		this.intAttribute = intAttribute;
	}
	public String getStrAttribute() {
		return strAttribute;
	}
	public void setStrAttribute(String strAttribute) {
		this.strAttribute = strAttribute;
	}
	public TestPOJO_Linked getLinkedAttribute() {
		return linkedAttribute;
	}
	public void setLinkedAttribute(TestPOJO_Linked linkedAttribute) {
		this.linkedAttribute = linkedAttribute;
	}
	
	@Override
	public String toString() {
		return "TestPOJO: strAttribute: " + getStrAttribute() + "    intAttribute: " + getIntAttribute() + "   linkedAttribute: " + getLinkedAttribute();
	}
}


