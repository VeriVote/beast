package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;

public class BooleanExpCodeAreaOptions extends Options {
    private final BooleanExpCodeArea booleanExpCodeArea;
    private final CodeAreaOptions codeAreaOptions;
    
    /**
     * 
     * @param id the id of these options
     * @param booleanExpCodeArea the codearea
     * @param codeAreaOptions the options
     */
    public BooleanExpCodeAreaOptions(String id, BooleanExpCodeArea booleanExpCodeArea,
            CodeAreaOptions codeAreaOptions) {
        super(id);
        this.booleanExpCodeArea = booleanExpCodeArea;
        this.codeAreaOptions = codeAreaOptions;
    }
    
    /**
     * 
     * @return the options
     */
    public CodeAreaOptions getCodeAreaOptions() {
        return codeAreaOptions;
    }
    
    /**
     * 
     * @return the codearea
     */
    public BooleanExpCodeArea getBooleanExpCodeArea() {
        return booleanExpCodeArea;
    }

    @Override
    public void reapply() {
        // TODO Auto-generated method stub
        
    }
}
