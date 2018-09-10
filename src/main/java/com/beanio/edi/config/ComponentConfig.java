package com.beanio.edi.config;

import org.beanio.internal.config.FieldConfig;
import org.beanio.internal.parser.Field;

/**
 * A component is a subfield within an EDI segment. The only difference of a
 * component and field are its delimiter in the output EDI file.
 * 
 * Workaround to distinguish an EDI field {@link Field} vs an EDI component
 * without making changes to beanio's core source code.
 * 
 * @author Arthur Tolentino
 *
 */
public class ComponentConfig extends FieldConfig {
    
    private Integer componentPosition;
    
    /**
     * Creates a new instance copying the {@link FieldConfig} configuration 
     * 
     * @param config the field configuration source
     * @param componentPosition the position of this component within a segment
     */
    public ComponentConfig(final FieldConfig config, Integer componentPosition) {
        setBound(config.isBound());
        setCollection(config.getCollection());
        setDefault(config.getDefault());
        setFormat(config.getFormat());
        setGetter(config.getGetter());
        setIdentifier(config.isIdentifier());
        setJsonArray(config.isJsonArray());
        setJsonArrayIndex(config.getJsonArrayIndex());
        setJsonName(config.getJsonName());
        setJsonType(config.getJsonType());
        setJustify(config.getJustify());
        setKeepPadding(config.isKeepPadding());
        setLabel(config.getLabel());
        setLazy(config.isLazy());
        setLength(config.getLength());
        setLenientPadding(config.isLenientPadding());
        setLiteral(config.getLiteral());
        setMaxLength(config.getMaxLength());
        setMaxOccurs(config.getMaxOccurs());
        setMaxOccursRef(config.getMaxOccurs());
        setMaxSize(config.getMaxSize());
        setMinLength(config.getMinLength());
        setMinOccurs(config.getMinOccurs());
        setMinOccursRef(config.getMinOccursRef());
        setMinSize(config.getMinSize());
        setName(config.getName());
        setNillable(config.isNillable());
        setOccursRef(config.getOccursRef());
        setOrdinal(config.getOrdinal());
        setPadding(config.getPadding());
        setPosition(config.getPosition());
        setRef(config.isRef());
        setRegex(config.getRegex());
        setRequired(config.isRequired());
        setSetter(config.getSetter());
        setTrim(config.isTrim());
        setType(config.getType());
        setTypeHandler(config.getTypeHandler());
        setTypeHandlerInstance(config.getTypeHandlerInstance());
        setUntil(config.getUntil());
        setXmlName(config.getXmlName());
        setXmlNamespace(config.getXmlNamespace());
        setXmlNamespaceAware(config.isXmlNamespaceAware());
        setXmlPrefix(config.getXmlPrefix());
        setXmlType(config.getXmlType());
        
        this.componentPosition = componentPosition;
    }
    
    /**
     * Returns the position of this component within a segment
     * 
     * @return component position
     */
    public Integer getComponentPosition() {
        return componentPosition;
    }

}
