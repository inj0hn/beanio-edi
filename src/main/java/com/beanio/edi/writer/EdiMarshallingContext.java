package com.beanio.edi.writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.beanio.internal.parser.MarshallingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanio.edi.EdiField;

/**
 * A {@link MarshallingContext} for EDI records.
 * 
 * @author Arthur Tolentino
 *
 */
public class EdiMarshallingContext extends MarshallingContext {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EdiMarshallingContext.class);
    
    // the index of the last committed field in the record
    private int committed;
    // the list used to build the final record
    private List<EdiField> record;
    // the list of entries for creating the record (may be unordered)
    private List<Entry> entries;
    
    public EdiMarshallingContext() {
        committed = 0;
        record = new ArrayList<EdiField>();
        entries = new ArrayList<Entry>();
    }

    @Override
    public void clear() {
        LOGGER.debug("Clearing {} entries", entries.size());
        super.clear();
        
        entries.clear();
        committed = 0;
    }
    
    /**
     * Puts the field text in the record.
     * 
     * @param position the position of the field in the record.
     * @param componentPosition the position of this field within a component segment if it's an EDI component
     * @param fieldText the field text
     * @param isLiteral indicates if the text is static
     * @param commit true to commit the current record, or false
     *   if the field is optional and should not extend the record
     *   unless a subsequent field is later appended to the record 
     */
    public void setField(int position, Integer componentPosition, String fieldText, boolean isLiteral,
            boolean commit) {
        int index = getAdjustedFieldPosition(position);
        
        Entry entry = new Entry(index, componentPosition, fieldText, isLiteral);
        entries.add(entry);
        
        if (commit) {
            committed = entries.size();
        }
    }
    
    /**
     * Returns all {@link EdiField} array for the current record
     * 
     * {@inheritDoc}
     */
    @Override
    protected Object getRecordObject() {
        record.clear();
        
        List<Entry> committedEntries;
        if (committed < entries.size()) {
            committedEntries = entries.subList(0, committed);
        }
        else {
            committedEntries = entries;
        }
        
        LOGGER.debug("Committed entries {}", committedEntries.size());
        
        Collections.sort(committedEntries);
        
        // the current index to write out
        int size = 0;
        // the offset for positions relative to the end of the record
        int offset = -1;
        
        for (Entry entry : committedEntries) {
            
            int index = entry.getPosition();
            if (index < 0) {
                
                // the offset is calculated the first time we encounter
                // a position relative to the end of the record
                if (offset == -1) {
                    offset = size + Math.abs(index);
                    index = size;
                }
                else {
                    index += offset;
                }
            }
            
            EdiField ediField = new EdiField(entry.getText(), entry.getComponentPosition());
            ediField.setLiteral(entry.isLiteral());
            if (index < size) {
                record.set(index, ediField);
            }
            else {
                while (index > size) {
                    record.add(ediField);
                    ++size;
                }
                
                record.add(ediField);
                ++size;
            }
        }
        
        return record.toArray(new EdiField[0]);
    }
    
    private static class Entry implements Comparable<Entry> {
        private int position;
        private Integer componentPosition;
        private int order;
        private String text;
        private boolean isLiteral;
        
        public Entry(int position, Integer componentPosition, String text, boolean isLiteral) {
            this.position = position;
            this.componentPosition = componentPosition;
            this.order = position < 0 ? position + Integer.MAX_VALUE : position;
            this.text = text;
            this.isLiteral = isLiteral;
        }
        
        public int getPosition() {
            return position;
        }

        public Integer getComponentPosition() {
            return componentPosition;
        }
        
        public String getText() {
            return text;
        }

        public boolean isLiteral() {
            return isLiteral;
        }
        
        @Override
        public int compareTo(Entry o) {
            return Integer.valueOf(this.order).compareTo(o.order);
        }
    }

}
