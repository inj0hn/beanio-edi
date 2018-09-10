package com.beanio.edi.format;

import org.beanio.BeanWriterException;
import org.beanio.internal.config.FieldConfig;
import org.beanio.internal.parser.FieldFormat;
import org.beanio.internal.parser.MarshallingContext;
import org.beanio.internal.parser.UnmarshallingContext;
import org.beanio.internal.parser.format.flat.FlatFieldFormatSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanio.edi.writer.EdiMarshallingContext;

/**
 * A {@link FieldFormat} implementation for a field in an EDI stream.
 * 
 * @author Arthur tolentino
 *
 */
public class EdiFieldFormat extends FlatFieldFormatSupport {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EdiFieldFormat.class);
    
    private Integer componentPosition;
    private boolean isRequired;
    private boolean isLiteral;
    
    /**
     * Sets the component position of the field
     * 
     * @param componentPosition the position of the component within a segment
     */
    public void setComponentPosition(Integer componentPosition) {
        this.componentPosition = componentPosition;
    }
    
    /**
     * Sets the required field indicator
     * 
     * @param true if field is required. false otherwise
     */
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Sets the literal field indicator
     * 
     * @param isLiteral true the field's text value is 
     *        through {@link FieldConfig#getLiteral()}
     */
    public void setLiteral(boolean isLiteral) {
        this.isLiteral = isLiteral;
    }
    
    @Override
    protected String extractFieldText(UnmarshallingContext context, boolean reporting) {
        throw new UnsupportedOperationException("Method not implemented");
    }
    
    @Override
    public boolean insertValue(MarshallingContext context, Object value) {
        if (isRequired && value == null) {
            throw new BeanWriterException(String.format("Required field is empty. Field name=%s", getName()));
        }
        return false;
    }

    @Override
    protected void insertFieldText(MarshallingContext context, String fieldText, boolean commit) {
        LOGGER.debug("Insert field text={}, pos={}, component pos={}, isRequired={}, isLiteral={}", 
                fieldText, getPosition(), componentPosition, isRequired, isLiteral);
        EdiMarshallingContext ediMarshallingContext = (EdiMarshallingContext) context;
        ediMarshallingContext.setField(getPosition(), componentPosition, fieldText, isLiteral, commit);
    }
    
}
