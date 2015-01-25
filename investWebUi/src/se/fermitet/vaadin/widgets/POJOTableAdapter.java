package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.vaadin.ui.Table;

public class POJOTableAdapter<T> implements Serializable {

	private static final long serialVersionUID = 3689523044777603458L;

	private List<ColumnDefinition> columnDefinitions;
	private Class<T> pojoClass;
	private List<T> data;
	private Map<T, UUID> itemIdPerPOJO;
	private Map<UUID, T> POJOPerItemId;

	private Table table;

	public POJOTableAdapter(Class<T> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<T> pojoClass, String myCaption) {
		super();
		
		this.table = new Table(myCaption);
		this.pojoClass = pojoClass;
		this.columnDefinitions = new ArrayList<ColumnDefinition>();
		this.itemIdPerPOJO = new HashMap<T, UUID>();
		this.POJOPerItemId = new HashMap<UUID, T>();
	}
	
	public Table getTable() {
		return table;
	}


	public void setdData(List<T> allData) {
		checkPrerequisites(allData);
		
		this.data = allData;
		
		syncData(allData);
	}

	private void checkPrerequisites(List<T> allData) {
		checkThatColumnDefinitionsExist();
		checkThatListContainsValidData(allData);
	}

	protected void syncData(List<T> allData) {
		table.removeAllItems();
		itemIdPerPOJO.clear();
		POJOPerItemId.clear();
		
		for (T data : allData) {
			List<Object> dataList = new ArrayList<Object>();
			for (ColumnDefinition def : columnDefinitions) {
				try {
					dataList.add(def.getter.invoke(data));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException("Problem with calling method " + def.getterName + " on object " + data.toString());
				}
			}
			
			UUID itemId = UUID.randomUUID();
			this.itemIdPerPOJO.put(data, itemId);
			this.POJOPerItemId.put(itemId, data);
			
			table.addItem(dataList.toArray(), itemId);
		}
	}
	
	public List<T> getData() {
		return Collections.unmodifiableList(this.data);
	}
	
	public void select(T toSelect) {
		table.select(this.itemIdPerPOJO.get(toSelect));
	}

	public void unselect() {
		select(null);
	}

	public T getSelectedData() {
		UUID selectedItemId = (UUID) table.getValue();
		
		if (selectedItemId != null) {
			return POJOPerItemId.get(selectedItemId);
		} else {
			return null;
		}
	}
	

	
	private void checkThatColumnDefinitionsExist() {
		if (columnDefinitions == null || columnDefinitions.size() == 0) {
			throw new POJOTableAdapterException("No column definitions are found");
		}
	}
	
	private void checkThatListContainsValidData(List<T> allData) {
		for (int i = 0; i < allData.size(); i++) {
			T o1 = allData.get(i);
			if (o1 == null) throw new POJOTableAdapterException("Null object found on index: " + i);
			
			for (int j = i + 1; j < allData.size(); j++) {
				T o2 = allData.get(j);
				
				if (o1.equals(o2)) throw new POJOTableAdapterException("Found two equal objects in list.  Indexes " + i + " and " + j + ".  Object was: " + o1.toString());
			}
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
			throw new POJOTableAdapterException("Problem in finding no-argument method " + getterName + " on class " + pojoClass.getName());
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
