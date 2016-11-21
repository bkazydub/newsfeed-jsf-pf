package com.test.utils;

import com.test.beans.SourcesCache;
import com.test.model.Source;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named @RequestScoped
public class SourceConverter implements Converter {

    @Inject
    private SourcesCache cache;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null || value.isEmpty())
            return null;

        for (Source source : cache.getSources()) {
            if (source.getId().equals(value))
                return source;
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value == null)
            return "";

        if (value instanceof Source) {
            return ((Source) value).getId();
        } else {
            throw new ConverterException("The value is not of type " + Source.class.getName());
        }
    }
}
