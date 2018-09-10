package com.beanio.edi.compiler;

import org.beanio.internal.compiler.Preprocessor;
import org.beanio.internal.compiler.flat.FlatParserFactory;
import org.beanio.internal.config.FieldConfig;
import org.beanio.internal.config.RecordConfig;
import org.beanio.internal.config.StreamConfig;
import org.beanio.internal.parser.FieldFormat;
import org.beanio.internal.parser.RecordFormat;
import org.beanio.internal.parser.StreamFormat;
import org.beanio.internal.parser.format.FieldPadding;
import org.beanio.stream.RecordParserFactory;

import com.beanio.edi.config.ComponentConfig;
import com.beanio.edi.format.EdiFieldFormat;
import com.beanio.edi.format.EdiRecordFormat;
import com.beanio.edi.format.EdiStreamFormat;
import com.beanio.edi.format.VariableLengthFieldPadding;

/**
 * A {@link ParserFactory} for the EDI stream format.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiParserFactory extends FlatParserFactory {

    @Override
    public StreamFormat createStreamFormat(StreamConfig config) {
        EdiStreamFormat format = new EdiStreamFormat();
        format.setName(config.getName());
        format.setRecordParserFactory(createRecordParserFactory(config));
        return format;
    }
    
    @Override
    protected Preprocessor createPreprocessor(StreamConfig config) {
        return new EdiPreprocessor(config);
    }
    
    @Override
    public RecordFormat createRecordFormat(RecordConfig config) {
        return new EdiRecordFormat();
    }
    
    @Override
    public FieldFormat createFieldFormat(FieldConfig config, Class<?> type) {
        EdiFieldFormat format = new EdiFieldFormat();
        format.setName(config.getName());
        format.setPosition(config.getPosition());
        format.setUntil(config.getUntil() == null ? 0 : config.getUntil());
        format.setPadding(getFieldPadding(config, type));
        format.setLazy(config.getMinOccurs().equals(0));
        if (config instanceof ComponentConfig) {
            ComponentConfig componentConfig = (ComponentConfig) config;
            format.setComponentPosition(componentConfig.getComponentPosition());
        }
        format.setRequired(config.isRequired());
        format.setLiteral(config.getLiteral()!=null);
        return format;
    }
    
    @Override
    protected RecordParserFactory getDefaultRecordParserFactory() {
        return new EdiRecordParserFactory();
    }
    
    private FieldPadding getFieldPadding(FieldConfig config, Class<?> type) {
        FieldPadding padding = null;
        if (config.getLength() != null){
            padding = new FieldPadding();
            padding.setLength(config.getLength());
        } else if (config.getMinLength() != null || config.getMaxLength() != null){
            padding = new VariableLengthFieldPadding();
            VariableLengthFieldPadding variableLengthFieldPadding = (VariableLengthFieldPadding) padding;
            variableLengthFieldPadding.setMinLength(config.getMinLength());
            variableLengthFieldPadding.setMaxLength(config.getMaxLength());
        }
        if (padding != null) {
            padding.setFiller(config.getPadding());
            padding.setJustify(FieldConfig.RIGHT.equals(config.getJustify()) ? FieldPadding.RIGHT : FieldPadding.LEFT);
            padding.setOptional(!config.isRequired());
            padding.setPropertyType(type);
            padding.init();
        }
        
        return padding;
    }
}
