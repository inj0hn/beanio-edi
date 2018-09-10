package com.beanio.edi.format;

import org.beanio.internal.parser.RecordFormat;
import org.beanio.internal.parser.UnmarshallingContext;

/**
 * A {@link RecordFormat} for EDI records.
 * 
 * Currently not used but is required by the stream factory
 * during initialization. Only used to unmarshal JAXB objects
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiRecordFormat implements RecordFormat{

    @Override
    public boolean matches(UnmarshallingContext context) {
        throw new UnsupportedOperationException("Method not implemented");
    }

    @Override
    public void validate(UnmarshallingContext context) {
        throw new UnsupportedOperationException("Method not implemented");
    }

}
