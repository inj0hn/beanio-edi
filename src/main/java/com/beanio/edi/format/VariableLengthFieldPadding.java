package com.beanio.edi.format;

import org.apache.commons.lang3.StringUtils;
import org.beanio.internal.parser.format.FieldPadding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides padding and trunction functionality for variable length fields.
 * 
 * @author Arthur Tolentino
 *
 */
public class VariableLengthFieldPadding extends FieldPadding {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VariableLengthFieldPadding.class);
    
    private String paddedNull = "";
    private Integer minLength;
    private Integer maxLength;
    
    /**
     * Pads text with <tt>filler</tt> if <tt>text.length()</tt> is less than
     * minLength. Truncates text if it is greater than maxLength.
     */
    @Override
    public String pad(final String text) {
        String formattedText;
        if (text == null) {
            if (isOptional()) {
                // optional fields are padded with spaces
                formattedText = paddedNull;
            } else {
                formattedText = format("");
            }
        } else {
            formattedText = format(text);
        }
        
        return formattedText;
    }
    
    private String format(final String text) {
        String formattedText = text;
        int currentLength = text.length();
        
        if (maxLength != null && maxLength >= 0 && currentLength > maxLength) {
            LOGGER.debug("Truncating '{}' down to {} chars", text, maxLength);
            formattedText = text.substring(0, maxLength);
        } else if (minLength != null && minLength >= 0 && currentLength < minLength) {

            // right justify shouldn't pad the text to its right and vice-versa. one of beanio's weird verbage.
            if (FieldPadding.RIGHT == getJustify()) {
                formattedText = StringUtils.leftPad(text, minLength, getFiller());
            }
            else {
                formattedText = StringUtils.rightPad(text, minLength, getFiller());
            }
        }
        
        return formattedText;
    }
    
    @Override
    public String unpad(String fieldText) {  
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    /**
     * Returns the minimum field length
     * @return minimum field length
     */
    public Integer getMinLength() {
        return minLength;
    }
    
    /**
     * Sets the minimum field length
     * @param minLength the minimum field length
     */
    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }
    
    /**
     * Returns the maximum field length
     * @return maximum field length
     */
    public Integer getMaxLength() {
        return maxLength;
    }
    
    /**
     * Set the maximum field length
     * @param maxLength the maximum field length
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void setPaddedNull(String paddedNull) {
        this.paddedNull = paddedNull;
    }

}
