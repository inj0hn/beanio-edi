package com.beanio.edi.builder;

import org.beanio.builder.FieldBuilder;
import org.beanio.builder.SegmentBuilder;

import com.beanio.edi.config.ComponentConfig;

/**
 * Builds a new EDI segment configuration. The method
 * {@link EdiSegmentBuilder#addComponent(FieldBuilder)} allows users to add EDI
 * components to this segment.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiSegmentBuilder extends SegmentBuilder {
    
    private int componentCount;

    /**
     * Creates a new instance
     * @param name the record name
     */
    public EdiSegmentBuilder(String name) {
        super(name);
    }
    
    /**
     * Adds a component to this record
     * @param field the component to add
     * @return this
     */
    public EdiSegmentBuilder addComponent(FieldBuilder field) {
        getConfig().add(new ComponentConfig(field.build(), componentCount++));
        return this;
    }
    
}
