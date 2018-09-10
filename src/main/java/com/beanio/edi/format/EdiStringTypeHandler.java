package com.beanio.edi.format;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.beanio.types.StringTypeHandler;

/**
 * Strips and replaces illegal EDI characters <b>(~, *, ^, :,|)</b> with a
 * space, and converts all characters to upper case by default.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiStringTypeHandler extends StringTypeHandler {

    public static final String NAME = EdiStringTypeHandler.class.getName();
    private final static Set<Character> DEFAULT_ILLEGAL_CHARACTERS = new HashSet<>(
            Arrays.asList('~', '*', '^', ':', '|'));
    private String illegalCharacterRegex;
    private boolean toUpperCase = true;
    
    /**
     * Creates a new instance using the default set of illegal characters.
     * See {@link EdiParserBuilder} for default delimiters/illegal characters.
     */
    public EdiStringTypeHandler() {
        initIllegalCharacterRegex(DEFAULT_ILLEGAL_CHARACTERS);
    }
    
    /**
     * Creates a new instance overriding the default set of illegal characters
     * Override the default illegal characters here when overriding the 
     * delimiters in {@link EdiParserBuilder}
     * 
     * @param overrideIllegalCharacters the set of illegal characters
     */
    public EdiStringTypeHandler(Set<Character> overrideIllegalCharacters) {
        initIllegalCharacterRegex(overrideIllegalCharacters);
    }
    
    /**
     * Sets the toUpperCase flag
     * 
     * @param toUpperCase format text to all caps
     */
    public void setUpperCase(boolean toUpperCase) {
        this.toUpperCase = toUpperCase;
    }
    
    /**
     * Returns the toUpperCase flag
     * 
     * @return true if the text will be converted to all caps
     */
    public boolean isUpperCase() {
        return toUpperCase;
    }
    
    private void initIllegalCharacterRegex(Set<Character> illegalCharacters) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        builder.append(StringUtils.join(illegalCharacters
                .stream()
                .map(illegalCharacter -> new String("\\" + illegalCharacter))
                .collect(Collectors.toList()), 
                '|'));
        builder.append(")");
        illegalCharacterRegex = builder.toString();
    }
    
    @Override
    public String format(Object text) {
        if (text!=null) {
            String textValue = (String) text;
            String formattedText = textValue.replaceAll(illegalCharacterRegex, " ");
            if (toUpperCase) {
                formattedText = formattedText.toUpperCase();
            }
            return formattedText;
        }
        return null;
    }
}
