package com.beanio.edi.builder;

import org.beanio.builder.DelimitedParserBuilder;
import org.beanio.internal.config.BeanConfig;
import org.beanio.stream.RecordParserFactory;

import com.beanio.edi.compiler.EdiRecordParserFactory;

/**
 * Builder for EDI parsers.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiParserBuilder extends DelimitedParserBuilder {

    private final EdiRecordParserFactory parser;

    /**
     * Creates a new instance
     * 
     * @param fieldDelimiter the EDI field delimiter
     * @param componentDelimiter the EDI component delimiter
     * @param recordTerminator the EDI segment delimiter
     */
    public EdiParserBuilder(char fieldDelimiter, char componentDelimiter, String recordTerminator) {
        parser = new EdiRecordParserFactory();
        parser.setDelimiter(fieldDelimiter);
        parser.setComponentDelimiter(componentDelimiter);
        parser.setRecordTerminator(recordTerminator);
    }

    @Override
    public BeanConfig<RecordParserFactory> build() {
        BeanConfig<RecordParserFactory> config = super.build();
        config.setInstance(parser);
        return config;
    }

}
