package edu.pse.beast.propertychecker;

public class CBMC_Result_Wrapper_long {
    private final int index;
    private final String name;
    private long value = -1l;
    
    public CBMC_Result_Wrapper_long(int index, String name) {
        this.index = index;
        this.name = name;
    }
    
    public void setValue(long value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
    
    public long getValue() {
        return value;
    }
}
