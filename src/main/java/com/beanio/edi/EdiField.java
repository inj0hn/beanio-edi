package com.beanio.edi;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.beanio.builder.FieldBuilder;

/**
 * Represents an EDI field that is written out to a {@link Writer} in
 * {@link EdiFieldWriter}
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiField {
    private boolean literal;
    private Integer componentPosition;
    private String text;

    /**
     * Constructs a new instance
     * 
     * @param text the field text value
     */
    public EdiField(String text) {
        this(text, null);
    }
    
    /**
     * Constructs a new instance
     * 
     * @param text the field text value
     * @param componentPosition the position of this component within a segment. 
     * Null if field is non-component.
     */
    public EdiField(String text, Integer componentPosition) {
        this.text = text;
        this.componentPosition = componentPosition;
    }
    
    /**
     * Returns the position of this component within a segment. Null if this a
     * non-component field
     * 
     * @return component position within a segment
     */
    public Integer getComponentPosition() {
        return componentPosition;
    }
    
    /**
     * Returns true if this field is a component.
     * 
     * @return true if this field is a component. False otherwise
     */
    public boolean isComponent() {
        return componentPosition != null;
    }
    
    /**
     * Sets the literal field indicator
     * 
     * @param literal field is a static text
     */
    public void setLiteral(boolean literal) {
        this.literal = literal;
    }
    
    /**
     * Returns true if the text is set via {@link FieldBuilder#literal(String)}
     * 
     * @return true if this field is a static text. false otherwise.
     */
    public boolean isLiteral() {
        return literal;
    }
    
    /**
     * Returns the EDI field text value
     * 
     * @return field text
     */
    public String getText() {
        return text;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
