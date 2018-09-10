package com.beanio.edi.compiler;

import org.beanio.internal.compiler.Preprocessor;
import org.beanio.internal.compiler.flat.FlatPreprocessor;
import org.beanio.internal.config.FieldConfig;
import org.beanio.internal.config.StreamConfig;

/**
 * Configuration {@link Preprocessor} for an EDI stream format.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiPreprocessor extends FlatPreprocessor {

    public EdiPreprocessor(StreamConfig stream) {
        super(stream);
    }
    
    @Override
    protected void handleField(FieldConfig field) {
        super.handleField(field);
        // setup default padding character to be the same as fixed-length fields
        // for variable-length fields
        if (field.getMinLength() != null && field.getPadding() == null) {
            field.setPadding(' ');
        }
    }

}
