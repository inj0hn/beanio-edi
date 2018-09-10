package com.beanio.edi.format;

import org.beanio.internal.parser.MarshallingContext;
import org.beanio.internal.parser.StreamFormatSupport;
import org.beanio.internal.parser.UnmarshallingContext;

import com.beanio.edi.writer.EdiMarshallingContext;

/**
 * A {@link StreamFormatSupport} implementation for the EDI stream format.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiStreamFormat extends StreamFormatSupport {

    @Override
    public MarshallingContext createMarshallingContext(boolean stream) {
        return new EdiMarshallingContext();
    }

    @Override
    public UnmarshallingContext createUnmarshallingContext() {
        throw new UnsupportedOperationException("Method not implemented");
    }


}
