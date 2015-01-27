package se.fermitet.vaadin.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.ui.Table;

public class POJOTableAdapter<T> implements Serializable {

	private static final long serialVersionUID = 3689523044777603458L;

	private List<T> data;
	private Table table;
	private BeanContainer<Integer, T> container;

	public POJOTableAdapter(Class<T> pojoClass) {
		this(pojoClass, null);
	}

	public POJOTableAdapter(Class<T> pojoClass, String myCaption) {
		super();
		
		this.table = new Table(myCaption);
		this.container = new BeanContainer<Integer, T>(pojoClass);
		this.table.setContainerDataSource(this.container);
	}
	
	public Table getTable() {
		return table;
	}


	public void setdData(List<T> allData) {
		this.data = allData;
		
		syncData(allData);
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
	
	public void setColumns(List<ColumnDefinition> columnDefinitionsToShow) {
		List<String> existingPropIds = new ArrayList<String>(this.container.getContainerPropertyIds());

		List<String> propIdsToShow = new ArrayList<String>();
		for (ColumnDefinition def : columnDefinitionsToShow) {
			String propId = def.propertyName;
			propIdsToShow.add(propId);
			
			if (!existingPropIds.contains(propId)) {
				this.container.addNestedContainerBean(propId);
			}
			
			this.table.setColumnHeader(propId, def.headerText);
		}
		
		this.table.setVisibleColumns(propIdsToShow.toArray());
	}
}
