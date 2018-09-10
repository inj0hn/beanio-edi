package com.beanio.edi.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

import org.beanio.stream.RecordWriter;

import com.beanio.edi.EdiField;
import com.beanio.edi.EdiFieldWriter;
import com.beanio.edi.config.EdiParserConfiguration;

/**
 * Marshal and write {@link EdiField} array of a record to a {@link Writer}.
 * Note that no validation is performed when a record is written, so if an escape character
 * is not configured and a field contains a delimiting character, the generated
 * output may be invalid.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiWriter implements RecordWriter {
    
    private static final char DEFAULT_ESCAPE_CHAR = '\\';
    
    private EdiFieldWriter ediFieldWriter;
    private Writer out;
    private String recordTerminator;

    /**
     * Creates a new instance
     * 
     * @param out the output writer
     * @param config the EDI parser configuration
     */
    public EdiWriter(Writer out, EdiParserConfiguration config) {
        this.out = out;
        
        recordTerminator = config.getRecordTerminator();
        if (recordTerminator == null) {
            recordTerminator = System.getProperty("line.separator");
        }
        
        Character fieldDelimiter = config.getDelimiter();
        Character componentDelimiter = config.getComponentDelimiter();
        Character escape = Optional.ofNullable(config.getEscape()).orElse(DEFAULT_ESCAPE_CHAR);
        
        ediFieldWriter = new EdiFieldWriter(fieldDelimiter, componentDelimiter, escape);
    }
    
    @Override
    public void write(Object value) throws IOException {
        EdiField[] fields = (EdiField[]) value;
        ediFieldWriter.write(fields, out);
        out.write(recordTerminator);
    }

    @Override
    public void close() throws IOException {
        out.flush();
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

}
