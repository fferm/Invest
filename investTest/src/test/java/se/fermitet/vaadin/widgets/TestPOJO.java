package se.fermitet.vaadin.widgets;

public class TestPOJO {
	private String strAttribute;
	private int intAttribute;
	private TestPOJO_Linked linkedAttribute;

	
	public int getIntAttribute() {
		return intAttribute;
	}
	public TestPOJO setIntAttribute(int intAttribute) {
		this.intAttribute = intAttribute;
		return this;
	}
	public String getStrAttribute() {
		return strAttribute;
	}
	public TestPOJO setStrAttribute(String strAttribute) {
		this.strAttribute = strAttribute;
		return this;
	}
	public TestPOJO_Linked getLinkedAttribute() {
		return linkedAttribute;
	}
	public TestPOJO setLinkedAttribute(TestPOJO_Linked linkedAttribute) {
		this.linkedAttribute = linkedAttribute;
		return this;
	}
}


