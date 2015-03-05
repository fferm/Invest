package se.fermitet.vaadin.widgets;

import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;
import org.joda.time.LocalDate;

public class TestPOJO {
	private String strAttribute;
	private int intAttribute;
	private TestPOJO_Linked linkedAttribute;
	private Money moneyAttribute;
	private LocalDate dateAttribute;

	public TestPOJO() {
		this((String) null);
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
	public TestPOJO_Linked getLinkedAttribute() {
		return linkedAttribute;
	}
	public void setLinkedAttribute(TestPOJO_Linked linkedAttribute) {
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

		ret.add(new TestPOJO("Str 1", 1, new TestPOJO_Linked("Linked 1")));
		ret.add(new TestPOJO("Str 2", 2, new TestPOJO_Linked("Linked 2")));
		ret.add(new TestPOJO(null,    3, new TestPOJO_Linked("Linked 3")));
		ret.add(new TestPOJO("Str 4", 4, null));
		ret.add(new TestPOJO("Str 5", 6, new TestPOJO_Linked(null)));

		return ret;
	}

	public static List<TestPOJO> getTestDataSecond() {
		List<TestPOJO> ret = new ArrayList<TestPOJO>();

		ret.add(new TestPOJO("Second 1", 100, new TestPOJO_Linked("Second linked 1")));

		return ret;

	}


}


