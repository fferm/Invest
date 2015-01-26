package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Table;

public class POJOTableAdapter<T> implements Serializable {

	private static final long serialVersionUID = 3689523044777603458L;

	private List<ColumnDefinition> columnDefinitions;
	private Class<T> pojoClass;
	private List<T> data;
	private Table table;
	private BeanContainer<Integer, T> container;

	public POJOTableAdapter(Class<T> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<T> pojoClass, String myCaption) {
		super();
		
		this.pojoClass = pojoClass;
		this.columnDefinitions = new ArrayList<ColumnDefinition>();

		this.table = new Table(myCaption);
		this.container = new BeanContainer<Integer, T>(pojoClass);
		this.table.setContainerDataSource(this.container);
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
//		checkThatColumnDefinitionsExist();
//		checkThatListContainsValidData(allData);
	}

	protected void syncData(List<T> allData) {
		table.removeAllItems();
		
		int i = 0;
		for (T data : allData) {
			container.addItem(i, data);
			i++;
		}
	}
	
	public List<T> getData() {
		return Collections.unmodifiableList(this.data);
	}
	
	public void select(T toSelect) {
//		table.select(this.itemIdPerPOJO.get(toSelect));
	}

	public void unselect() {
//		select(null);
	}

	public T getSelectedData() {
//		UUID selectedItemId = (UUID) table.getValue();
//		
//		if (selectedItemId != null) {
//			return POJOPerItemId.get(selectedItemId);
//		} else {
//			return null;
//		}
		return null;
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

	public void setColumns(List<String> propIdsToShow) {
//		this.columnDefinitions = columns;

		List<String> existingPropIds = new ArrayList<String>();
		existingPropIds.addAll(this.container.getContainerPropertyIds());
		
		Set<String> union = new HashSet<String>(propIdsToShow);
		union.addAll(existingPropIds);
		
		for (String propId : union) {
			boolean inExisting = existingPropIds.contains(propId);
			boolean toShow = propIdsToShow.contains(propId);
			
			if (inExisting && toShow) {
				// Do nothing
				// Possibly set header
			} else if (inExisting && !toShow) {
				this.container.removeContainerProperty(propId);
			} else if (!inExisting && toShow) {
				this.container.addNestedContainerProperty(propId);
				// Possibly set header
			}
		}
	}
//	public void addColumn(String propertyName, String headerText) {
//		ColumnDefinition def = createColumnDefinition(getterName, headerText);
//
//		addColumnToTableProperties(def);
//	}
//
//	private ColumnDefinition createColumnDefinition(String getterName, String headerText) {
//		ColumnDefinition def = new ColumnDefinition();
//		def.getterName = getterName;
//		def.headerText = headerText;
//
//		try {
//			def.getter = pojoClass.getMethod(getterName);
//			def.clazz = def.getter.getReturnType();
//			
//			handlePrimitiveTypes(def);
//			
//		} catch (NoSuchMethodException | SecurityException e) {
//			throw new POJOTableAdapterException("Problem in finding no-argument method " + getterName + " on class " + pojoClass.getName());
//		}
//
//		columnDefinitions.add(def);
//		return def;
//	}
//
//	private void handlePrimitiveTypes(POJOTableAdapter<T>.ColumnDefinition def) {
//		if (def.clazz.equals(byte.class)) 	def.clazz = Byte.class;
//		if (def.clazz.equals(short.class)) 	def.clazz = Short.class;
//		if (def.clazz.equals(int.class)) 	def.clazz = Integer.class;
//		if (def.clazz.equals(long.class)) 	def.clazz = Long.class;
//		if (def.clazz.equals(float.class)) 	def.clazz = Float.class;
//		if (def.clazz.equals(double.class)) def.clazz = Double.class;
//		if (def.clazz.equals(boolean.class)) def.clazz = Boolean.class;
//		if (def.clazz.equals(char.class)) def.clazz = Character.class;
//	}
//
//	private void addColumnToTableProperties(POJOTableAdapter<T>.ColumnDefinition def) {
//		String propId = def.getterName;
//		container.addNestedContainerProperty(propId);
////		container.addContainerProperty(propId, def.clazz, null);
////		table.addContainerProperty(propId, def.clazz, null);
////		table.setColumnHeader(propId, def.headerText);
//	}
//
//	public void addProperty(String string) {
//		container.getc
//		container.addNestedContainerProperty(string);
//	}
}
