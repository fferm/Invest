package se.fermitet.invest.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ModelException extends Exception {

	private static final long serialVersionUID = -7394909328890202728L;
	
	public enum ModelExceptionType {
		CANNOT_DELETE_STOCK_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS,
		CANNOT_DELETE_PORTFOLIO_SINCE_IT_HAS_ASSOCIATED_TRANSACTIONS,
		DUMMY  //TODO Delete when there is more
	}

	private ModelExceptionType type;
	private List<Object> context;
	
	public ModelException(ModelExceptionType type, Object... context) {
		super();
		
		this.type = type;
		this.context = Arrays.asList(context);
	}

	public ModelExceptionType getType() {
		return type;
	}
	
	public List<Object> getContext() {
		return Collections.unmodifiableList(this.context);
	}
	
	@Override
	public String toString() {
		Enum<ModelExceptionType> type = this.getType();
		
		StringBuffer buf = new StringBuffer("ModelException: ");
		buf.append(type.name());
		
		if (this.getContext().size() >= 0) {
			buf.append("  Context: ");
			
			for (Iterator<Object> iter = context.iterator(); iter.hasNext(); ) {
				Object contextObj = iter.next();
				buf.append(contextObj.toString());
				
				if (iter.hasNext())	buf.append(" , ");
			}
		}
		
		return buf.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((context == null) ? 0 : context.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ModelException other = (ModelException) obj;
		if (context == null) {
			if (other.context != null)
				return false;
		} else if (!context.equals(other.context))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	
	
	
	

}
