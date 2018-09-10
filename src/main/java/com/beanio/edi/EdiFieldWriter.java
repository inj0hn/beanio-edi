package com.beanio.edi;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Writes {@link EdiField} array to a {@link Writer}
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiFieldWriter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EdiFieldWriter.class);
    
    private Character fieldDelimiter;
    private Character componentDelimiter;
    private Character escape;
    
    /**
     * Creates a new instance. Throws an {@link IllegalArgumentException} if the
     * escape parameter is the same as the fieldDelimiter or componentDelimiter.
     * 
     * @param fieldDelimiter the EDI field delimiter
     * @param componentDelimiter the EDI component delimiter
     * @param escape the field escape character
     */
    public EdiFieldWriter(Character fieldDelimiter, Character componentDelimiter, Character escape) {
        this.fieldDelimiter = fieldDelimiter;
        this.componentDelimiter = componentDelimiter;
        this.escape = escape;
        
        if (escape != null && fieldDelimiter == escape) {
            throw new IllegalArgumentException("The field delimiter cannot match the escape character");
        }
        
        if (escape != null && componentDelimiter == escape) {
            throw new IllegalArgumentException("The component delimiter cannot match the escape character");
        }
    }
    
    /**
     * Writes {@link EdiField} array to a {@link Writer}.
     * It removes all {@link EdiField} with empty <tt>EdiField.getText()</tt> 
     * from the tail of the {@link EdiField array} before passing it to 
     * the {@link Writer}.
     * 
     * @param fields the EDI fields to write
     * @param out the writer to output the fields
     * @throws IOException I/O error occurs
     */
    public void write(final EdiField[] fields, final Writer out) throws IOException {
        boolean escapeEnabled = escape!=null;
        int fieldPosition = 0;
        Character delimiter = null;
        EdiField[] reductedFields = truncateEdiFieldArray(fields);
        
        for(int i=0 ; i< reductedFields.length ; i++) {
            EdiField field = reductedFields[i];
            LOGGER.debug("position:{}, EDI field:{}", fieldPosition, field);
            
            if (fieldPosition++ > 0) {
                if (delimiter!=null) {
                    out.write(delimiter);
                } else {
                    out.write("");
                }
            }
            
            // preserve literal, static text
            if (!field.isLiteral() && escapeEnabled) {
                for (char currentCharacter : field.getText().toCharArray()) {
                    if ((fieldDelimiter != null && fieldDelimiter == currentCharacter)
                            || (componentDelimiter != null && componentDelimiter == currentCharacter)
                            || currentCharacter == escape) {
                        out.write(escape);
                    }
                    out.write(currentCharacter);
                }
            } else {
                out.write(field.getText());
            }
            
            delimiter = getDelimiter(reductedFields, i);
        }
    }
    
    /**
     * Truncates the tail of {@link EdiField} array to remove empty fields.
     * 
     * @param fields the source edi field array
     * @return reducted {@link EdiField array}
     */
    private EdiField[] truncateEdiFieldArray(final EdiField[] fields) {
        boolean discard = true;
        List<EdiField> truncatedFields = new ArrayList<>();
        for(int i=(fields.length - 1) ; i>=0 ; i--) {
            if(discard && StringUtils.isEmpty(fields[i].getText())) {
                continue;
            } else {
                truncatedFields.add(fields[i]);
                discard = false;
            }
        }
        Collections.reverse(truncatedFields);
        return truncatedFields.toArray(new EdiField[0]);
    }
    
    private Character getDelimiter(EdiField[] fields, int currentIndex) {
        if (currentIndex + 1 < fields.length) {
            EdiField currentField = fields[currentIndex];
            EdiField nextField = fields[currentIndex + 1];
            // use componentDelimiter between components within the same segment.
            // if the component's next node is a field, use the regular delimiter
            return (currentField.isComponent() && currentField.isComponent() == nextField.isComponent()
                    // nextField is a component within the same segment of the current component
                    && currentField.getComponentPosition() < nextField.getComponentPosition())
                    ? componentDelimiter : fieldDelimiter;
        }
        return null;
    }

}
