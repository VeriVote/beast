package edu.pse.beast.propertychecker;

/**
 * 
 * @author Lukas
 *
 */
public class CBMC_Result_Wrapper_long {
    private final int mainIndex;
    private final String name;
    private long value = -1l;
    
    public CBMC_Result_Wrapper_long(int mainIndex, String name) {
        this.mainIndex = mainIndex;
        this.name = name;
    }
    
    public void setValue(long value) {
        this.value = value;
    }

    public int getMainIndex() {
        return mainIndex;
    }

    public String getName() {
        return name;
    }
    
    public long getValue() {
        return value;
    }
}
