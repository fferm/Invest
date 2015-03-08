package se.fermitet.vaadin.converters;

import com.vaadin.data.util.converter.Converter;

abstract class AbstractConverter<PRESENTATION, MODEL> implements Converter<PRESENTATION, MODEL>{
	private static final long serialVersionUID = 2527687838020839324L;

	private Class<PRESENTATION> presentationClass;
	private Class<MODEL> modelClass;

	public AbstractConverter(Class<PRESENTATION> presentationClass, Class<MODEL> modelClass) {
		super();
		this.presentationClass = presentationClass;
		this.modelClass = modelClass;
	}
	
	@Override
	public Class<MODEL> getModelType() {
		return modelClass;
	}
	
	@Override
	public Class<PRESENTATION> getPresentationType() {
		return presentationClass;
	}
}