package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.vaadin.ui.Table;

public class POJOTableAdapter<T> implements Serializable {

	private static final long serialVersionUID = 3689523044777603458L;

	private List<ColumnDefinition> columnDefinitions;
	private Class<T> pojoClass;
	private List<T> data;

	private Table table;

	public POJOTableAdapter(Class<T> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<T> pojoClass, String myCaption) {
		super();
		
		this.table = new Table(myCaption);
		this.pojoClass = pojoClass;
		columnDefinitions = new ArrayList<ColumnDefinition>();
	}
	
	public Table getTable() {
		return table;
	}


	public void setdData(List<T> allData) {
		checkThatColumnDefinitionsExist();
		
		this.data = allData;
		
		syncData(allData);
	}

	protected void syncData(List<T> allData) {
		table.removeAllItems();
		for (T data : allData) {
			List<Object> dataList = new ArrayList<Object>();
			for (ColumnDefinition def : columnDefinitions) {
				try {
					dataList.add(def.getter.invoke(data));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException("Problem with calling method " + def.getterName + " on object " + data.toString());
				}
			}
			
			table.addItem(dataList.toArray(), UUID.randomUUID());
		}
	}
	
	public List<T> getData() {
		return Collections.unmodifiableList(this.data);
	}
	
	private void checkThatColumnDefinitionsExist() {
		if (columnDefinitions == null || columnDefinitions.size() == 0) {
			throw new RuntimeException("No column definitions are found");
		}
	}

	private class ColumnDefinition {
		String getterName;
		String headerText;

		Method getter;
		Class<?> clazz;
	}

	public void addColumn(String getterName, String headerText) {
		ColumnDefinition def = createColumnDefinition(getterName, headerText);

		addColumnToTableProperties(def);
	}

	private ColumnDefinition createColumnDefinition(String getterName, String headerText) {
		ColumnDefinition def = new ColumnDefinition();
		def.getterName = getterName;
		def.headerText = headerText;

		try {
			def.getter = pojoClass.getMethod(getterName);
			def.clazz = def.getter.getReturnType();
			
			handlePrimitiveTypes(def);
			
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Problem in finding no-argument method " + getterName + " on class " + pojoClass.getName());
		}

		columnDefinitions.add(def);
		return def;
	}

	private void handlePrimitiveTypes(POJOTableAdapter<T>.ColumnDefinition def) {
		if (def.clazz.equals(byte.class)) 	def.clazz = Byte.class;
		if (def.clazz.equals(short.class)) 	def.clazz = Short.class;
		if (def.clazz.equals(int.class)) 	def.clazz = Integer.class;
		if (def.clazz.equals(long.class)) 	def.clazz = Long.class;
		if (def.clazz.equals(float.class)) 	def.clazz = Float.class;
		if (def.clazz.equals(double.class)) def.clazz = Double.class;
		if (def.clazz.equals(boolean.class)) def.clazz = Boolean.class;
		if (def.clazz.equals(char.class)) def.clazz = Character.class;
	}

	private void addColumnToTableProperties(POJOTableAdapter<T>.ColumnDefinition def) {
		// Assumption that name of property is same as name of getter
		Object propId = def.getterName;
		table.addContainerProperty(propId, def.clazz, null);
		table.setColumnHeader(propId, def.headerText);
	}

}
