package com.beanio.edi.compiler;

import java.io.Reader;
import java.io.Writer;

import org.beanio.stream.RecordMarshaller;
import org.beanio.stream.RecordParserFactory;
import org.beanio.stream.RecordReader;
import org.beanio.stream.RecordUnmarshaller;
import org.beanio.stream.RecordWriter;

import com.beanio.edi.config.EdiParserConfiguration;
import com.beanio.edi.writer.EdiRecordMarshaller;
import com.beanio.edi.writer.EdiWriter;

/**
 * Default {@link RecordParserFactory} for the EDI stream format.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiRecordParserFactory extends EdiParserConfiguration implements RecordParserFactory {

    @Override
    public void init() throws IllegalArgumentException {
        if (getEscape() != null) {
            if (getEscape() == getDelimiter()) {
                throw new IllegalArgumentException("The field delimiter cannot match the escape character");
            }
            if (getEscape() == getComponentDelimiter()) {
                throw new IllegalArgumentException("The component delimiter cannot match the escape character");
            }
        }

        if (getLineContinuationCharacter() != null && getLineContinuationCharacter() == getDelimiter()) {
            throw new IllegalArgumentException("The field delimiter cannot match the line continuation character");
        }
        
        if (getLineContinuationCharacter() != null && getLineContinuationCharacter() == getComponentDelimiter()) {
            throw new IllegalArgumentException("The component delimiter cannot match the line continuation character");
        }
    }

    @Override
    public RecordReader createReader(Reader in) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public RecordWriter createWriter(Writer out) throws IllegalArgumentException {
        return new EdiWriter(out, this);
    }

    @Override
    public RecordMarshaller createMarshaller() throws IllegalArgumentException {
        return new EdiRecordMarshaller(this);
    }

    @Override
    public RecordUnmarshaller createUnmarshaller() throws IllegalArgumentException {
        throw new UnsupportedOperationException("Method not implemented");
    }
}
