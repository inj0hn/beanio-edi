package com.beanio.edi.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;

import org.beanio.stream.RecordMarshaller;

import com.beanio.edi.EdiField;
import com.beanio.edi.EdiFieldWriter;
import com.beanio.edi.config.EdiParserConfiguration;

/**
 * {@link RecordMarshaller} implementation for EDI formatted records.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiRecordMarshaller implements RecordMarshaller{
    
    private EdiFieldWriter writer;
    /**
     * Creates an instance with default {@link EdiParserConfiguration} configuration
     */
    public EdiRecordMarshaller() {
        this(new EdiParserConfiguration());
    }
    
    /**
     * Creates an instance with custom {@link EdiParserConfiguration} configuration
     * 
     * @param config custom EDI parser configuration
     */
    public EdiRecordMarshaller(EdiParserConfiguration config) {
        Character fieldDelimiter = config.getDelimiter();
        Character componentDelimiter = config.getComponentDelimiter();
        Character escape = config.getEscape();
        
        writer = new EdiFieldWriter(fieldDelimiter, componentDelimiter, escape);
    }
    
    /**
     * Marshals an {@link EdiField} array into an EDI record text.
     * {@inheritDoc}
     */
    @Override
    public String marshal(Object record) {
        EdiField[] fields = (EdiField[]) record;
        StringWriter text = new StringWriter();
        
        try {
            writer.write(fields, text);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        
        return text.toString();
    }

}
