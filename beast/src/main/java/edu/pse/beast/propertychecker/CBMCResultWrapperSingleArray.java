package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lukas
 *
 */
public class CBMCResultWrapperSingleArray {
    private final int mainIndex;
    private final String name;
    private final List<Long> list = new ArrayList<Long>();

    /**
     * creates a new wrapper
     * 
     * @param mainIndex
     *            the index of this variable (for example votes1 has the main
     *            index of 1)
     * @param name
     *            the name of this variable (for example votes1 has the name
     *            votes)
     */
    public CBMCResultWrapperSingleArray(int mainIndex, String name) {
        this.mainIndex = mainIndex;
        this.name = name;
    }

    /**
     * adds a variable to this 2 dim array-wrapper
     * 
     * @param index
     *            the index (the index of the array)
     * @param toAdd
     *            the value to add at this position
     */
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
    
    /**
     * returns the two dimensional list that is saved here. Only use this if you
     * really need it, because there is also and methode that creates an array
     * from these, which makes sure that there are no empty parts in the lists
     * 
     * @return the list that describes this variable
     */
    public List<Long> getList() {
        return list;
    }

    /**
     * 
     * @return returns the main index (for example votes1 has the mainIndex of
     *         1)
     */
    public int getMainIndex() {
        return mainIndex;
    }

    /**
     * 
     * @return the name of the var (for example votes1 has the name votes)
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return the array that this wrapper represents. It has the same values
     *         and size as the array that the c-program that cbmc analyzed had
     *         inside
     */
    public Long[] getArray() {
        Long[] toReturn;
        if ((list != null) && (list.size() > 0)) {
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
