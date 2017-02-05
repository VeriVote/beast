package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;

public class CBMC_Result_Wrapper_singleArray {
    private final int index;
    private final String name;
    private final List<Long> list = new ArrayList<Long>();
    
    public CBMC_Result_Wrapper_singleArray(int index, String name) {
        this.index = index;
        this.name = name;
    }
    
    public void addTo(int index, long toAdd) {
        if (list.size() > index) {
            list.set(index, toAdd);
        } else {
            for (int i = list.size(); i < index; i++) {
                list.add(-1l);
            }
            list.add(toAdd);
        }
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
    
    public Long[] getList() {
        Long[] toReturn;
        if((list != null) && (list.size() > 0)) {
            toReturn = new Long[list.size()];
        } else {
            toReturn = new Long[0];
        }

        for (int i = 0; i < toReturn.length; i++) {
            if (list.size() >= i && list.get(i) != null) {
                toReturn[i] = list.get(i);
            } else {
                toReturn[i] = -1l;
            }
        }   
        return toReturn;
    }
}
