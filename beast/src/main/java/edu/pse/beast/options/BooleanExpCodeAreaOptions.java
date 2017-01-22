package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;

public class BooleanExpCodeAreaOptions {
    private final BooleanExpCodeArea booleanExpCodeArea;
    private final CodeAreaOptions codeAreaOptions;
    
    /**
     * 
     * @param booleanExpCodeArea the codearea
     * @param codeAreaOptions the options
     */
    public BooleanExpCodeAreaOptions(BooleanExpCodeArea booleanExpCodeArea,
            CodeAreaOptions codeAreaOptions) {
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
}
