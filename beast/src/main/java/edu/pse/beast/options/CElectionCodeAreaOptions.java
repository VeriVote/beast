package edu.pse.beast.options;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;

public class CElectionCodeAreaOptions {
    private final CElectionCodeArea cElecCodeArea;
    private final CodeAreaOptions codeAreaOptiosn;
    
    /**
     * 
     * @param cElecCodeArea the cCodeArea
     * @param codeAreaOptions the options
     */
    public CElectionCodeAreaOptions(CElectionCodeArea cElecCodeArea,
            CodeAreaOptions codeAreaOptions) {
        this.cElecCodeArea = cElecCodeArea;
        this.codeAreaOptiosn = codeAreaOptions;
    }
    
    /**
     * 
     * @return the code area
     */
    public CElectionCodeArea getCElecCodeArea() {
        return cElecCodeArea;
    }
    
    /**
     * 
     * @return the options
     */
    public CodeAreaOptions getCodeAreaOptions() {
        return codeAreaOptiosn;
    }
    
}
