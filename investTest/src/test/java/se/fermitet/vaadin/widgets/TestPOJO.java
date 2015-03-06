package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

public class TestPOJO {
	private String strAttribute;
	private int intAttribute;
	private TestPOJO_LinkedTo linkedAttribute;
	private Money moneyAttribute;
	private LocalDate dateAttribute;

	public TestPOJO() {
		this((String) null);
	}

	public TestPOJO(String strAttribute) {
		this(strAttribute, 0, null);
	}

	public TestPOJO(String strAttribute, int intAttribute, TestPOJO_LinkedTo linkedAttribute) {
		super();
		this.setStrAttribute(strAttribute);
		this.setIntAttribute(intAttribute);
		this.setLinkedAttribute(linkedAttribute);
	}

	public TestPOJO(Money moneyAttribute) {
		super();
		this.moneyAttribute = moneyAttribute;
	}

	public TestPOJO(LocalDate dateAttribute) {
		super();
		this.dateAttribute = dateAttribute;
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
	public TestPOJO_LinkedTo getLinkedAttribute() {
		return linkedAttribute;
	}
	public void setLinkedAttribute(TestPOJO_LinkedTo linkedAttribute) {
		this.linkedAttribute = linkedAttribute;
	}
	public Money getMoneyAttribute() {
		return moneyAttribute;
	}
	public void setMoneyAttribute(Money moneyAttribute) {
		this.moneyAttribute = moneyAttribute;
	}
	public LocalDate getDateAttribute() {
		return dateAttribute;
	}
	public void setDateAttribute(LocalDate dateAttribute) {
		this.dateAttribute = dateAttribute;
	}

	@Override
	public String toString() {
		return "TestPOJO: strAttribute: " + getStrAttribute() + "    intAttribute: " + getIntAttribute() + "   linkedAttribute: " + getLinkedAttribute();
	}

	public static List<TestPOJO> getTestData() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO("Str 1", 1, new TestPOJO_LinkedTo("Linked 1")));
		ret.add(new TestPOJO("Str 2", 2, new TestPOJO_LinkedTo("Linked 2")));
		ret.add(new TestPOJO(null,    3, new TestPOJO_LinkedTo("Linked 3")));
		ret.add(new TestPOJO("Str 4", 4, null));
		ret.add(new TestPOJO("Str 5", 6, new TestPOJO_LinkedTo(null)));

		return ret;
	}

	public static List<TestPOJO> getTestDataSecond() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO("Second 1", 100, new TestPOJO_LinkedTo("Second linked 1")));

		return ret;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateAttribute == null) ? 0 : dateAttribute.hashCode());
		result = prime * result + intAttribute;
		result = prime * result
				+ ((linkedAttribute == null) ? 0 : linkedAttribute.hashCode());
		result = prime * result
				+ ((moneyAttribute == null) ? 0 : moneyAttribute.hashCode());
		result = prime * result
				+ ((strAttribute == null) ? 0 : strAttribute.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestPOJO other = (TestPOJO) obj;
		if (dateAttribute == null) {
			if (other.dateAttribute != null)
				return false;
		} else if (!dateAttribute.equals(other.dateAttribute))
			return false;
		if (intAttribute != other.intAttribute)
			return false;
		if (linkedAttribute == null) {
			if (other.linkedAttribute != null)
				return false;
		} else if (!linkedAttribute.equals(other.linkedAttribute))
			return false;
		if (moneyAttribute == null) {
			if (other.moneyAttribute != null)
				return false;
		} else if (!moneyAttribute.equals(other.moneyAttribute))
			return false;
		if (strAttribute == null) {
			if (other.strAttribute != null)
				return false;
		} else if (!strAttribute.equals(other.strAttribute))
			return false;
		return true;
	}

	
}


