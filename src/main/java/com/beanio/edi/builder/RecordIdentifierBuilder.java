package com.beanio.edi.builder;

import org.beanio.builder.FieldBuilder;

/**
 * Record identifier {@link FieldBuilder field builder}
 * 
 * @author Arthur Tolentino
 *
 */
public class RecordIdentifierBuilder extends FieldBuilder {

    /**
     * Creates a new instance
     * @param id the record identifier
     */
    public RecordIdentifierBuilder(String id) {
        super("recordIdentifier");
        rid();
        literal(id);
        ignore();
    }

}
