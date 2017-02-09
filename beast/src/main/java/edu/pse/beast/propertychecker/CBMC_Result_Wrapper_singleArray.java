package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lukas
 *
 */
public class CBMC_Result_Wrapper_singleArray {
    private final int mainIndex;
    private final String name;
    private final List<Long> list = new ArrayList<Long>();
    
    public CBMC_Result_Wrapper_singleArray(int mainIndex, String name) {
        this.mainIndex = mainIndex;
        this.name = name;
    }
    
    public void addTo(int index, long toAdd) {
        if (list.size() > index) {
            list.set(index, toAdd);
        } else {
            for (int i = list.size(); i <= index; i++) {
                list.add(0l);
            }
            list.set(index, toAdd);
        }
    }

    public int getMainIndex() {
        return mainIndex;
    }

    public String getName() {
        return name;
    }
    
    public List<Long> getList() {
    	return list;
    }
    
    public Long[] getArray() {
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
                toReturn[i] = 0l;
            }
        }   
        return toReturn;
    }
    
}
