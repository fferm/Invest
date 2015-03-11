package se.fermitet.invest.domain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import se.fermitet.general.IdAble;
import se.fermitet.invest.exception.InvestException;

public abstract class InvestPOJO implements IdAble<UUID> {

	@NotNull
	@Id
	UUID id = UUID.randomUUID();

	@Transient
	private String toStringClassName;
	@Transient
	private List<PropertyDef> toStringPropDefs;

	public InvestPOJO() {
		super();

		toStringPropDefs = new ArrayList<InvestPOJO.PropertyDef>();
		try {
			initToStringProperties();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new InvestException("No such method", e);
		} 

	}

	protected abstract void initToStringProperties() throws NoSuchMethodException;

	public UUID getId() {
		return this.id;
	}

	@Override
	public String toString() {
		try {
			StringBuilder retBuilder = new StringBuilder("[ ");

			retBuilder.append(toStringClassName);
			retBuilder.append(" { ");

			for (Iterator<PropertyDef> iter = toStringPropDefs.iterator(); iter.hasNext(); ) {
				PropertyDef def = iter.next();

				retBuilder.append(def.name);
				retBuilder.append(" : ");

				Object value = def.getter.invoke(this);

				if (value == null) retBuilder.append("null");
				else retBuilder.append(value.toString());

				if (iter.hasNext()) retBuilder.append(" |");
				retBuilder.append(" ");
			}
			retBuilder.append("} ]");

			return retBuilder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvestException("Exception in toString getter call", e);
		}
	}


	protected void setToStringClassName(String toStringClassName) {
		this.toStringClassName = toStringClassName;
	}

	protected void addToStringPropDef(String name, Method getter) {
		toStringPropDefs.add(new PropertyDef(name, getter));
	}

	private class PropertyDef {
		private String name;
		private Method getter;

		protected PropertyDef(String name, Method getter) {
			super();
			this.name = name;
			this.getter = getter;
		}
	}
}
