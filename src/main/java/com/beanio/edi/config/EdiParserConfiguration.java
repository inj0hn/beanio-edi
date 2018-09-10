package com.beanio.edi.config;

import org.beanio.stream.delimited.DelimitedParserConfiguration;

/**
 * Stores configuration settings for parsing EDI formatted streams.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiParserConfiguration extends DelimitedParserConfiguration {
    
    private char componentDelimiter = ':';
    
    public EdiParserConfiguration() {
        super();
    }
    
    /**
     * Creates a new instance
     * 
     * @param delimiter the EDI field delimiter character
     */
    public EdiParserConfiguration(char delimiter) {
        super(delimiter);
    }
    
    /**
     * Creates a new instance
     * 
     * @param delimiter the EDI field delimiter character
     * @param componentDelimiter the EDI component delimiter character
     */
    public EdiParserConfiguration(char delimiter, char componentDelimiter) {
        super(delimiter);
        setComponentDelimiter(componentDelimiter);
    }
    
    /**
     * Sets the EDI component delimiter character configuration
     * 
     * @param componentDelimiter EDI component delimiter character
     */
    public void setComponentDelimiter(char componentDelimiter) {
        this.componentDelimiter = componentDelimiter;
    }
    
    /**
     * Returns the EDI component delimiter character configuration.
     * 
     * @return component delimiter character
     */
    public char getComponentDelimiter() {
        return componentDelimiter;
    }

}
