package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps a two dimensional array that cbmc has put out
 * 
 * @author Lukas
 *
 */
public class CBMCResultWrapperMultiArray {
    private final int mainIndex;
    private final String name;
    private final List<ArrayList<Long>> list = new ArrayList<ArrayList<Long>>();

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
    public CBMCResultWrapperMultiArray(int mainIndex, String name) {
        this.mainIndex = mainIndex;
        this.name = name;
    }

    /**
     * adds a variable to this 2 dim array-wrapper
     * 
     * @param firstIndex
     *            the first index (the first index of the array)
     * @param secondIndex
     *            the second index (the second index of the array)
     * @param toAdd
     *            the value to add at this position
     */
    public void addTo(int firstIndex, int secondIndex, long toAdd) {

        if (list.size() > firstIndex) {
            addToLongList(list.get(firstIndex), secondIndex, toAdd);
        } else {
            for (int i = list.size(); i <= firstIndex; i++) {
                list.add(new ArrayList<Long>());
            }
            addToLongList(list.get(firstIndex), secondIndex, toAdd);
        }

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
     * this array consists of an array of long arrays. This methode lets you add
     * to an underlying sub "array" / list
     * 
     * @param list
     *            the list to
     * @param indexToAddAt
     *            the index that determines where to add the value
     * @param toAdd
     *            the value to add
     */
    private void addToLongList(List<Long> list, int indexToAddAt, long toAdd) {
        if (list.size() > indexToAddAt) {
            list.set(indexToAddAt, toAdd);
        } else {
            for (int i = list.size(); i <= indexToAddAt; i++) {
                list.add(0l);
            }
            list.set(indexToAddAt, toAdd);
        }
    }

    /**
     * 
     * @return the array that this wrapper represents. It has the same values
     *         and size as the array that the c-program that cbmc analyzed had
     *         inside
     */
    public Long[][] getArray() {
        Long[][] toReturn;
        if ((list != null) && (list.size() > 0) && (list.get(0) != null) && (list.get(0).size() > 0)) {
            toReturn = new Long[list.size()][list.get(0).size()];
        } else {
            toReturn = new Long[0][0];
        }

        for (int i = 0; i < toReturn.length; i++) {
            boolean indexWorks = list.size() >= i && list.get(i) != null;
            for (int j = 0; j < toReturn[0].length; j++) {
                if (indexWorks && list.get(i).size() >= j && list.get(i).get(j) != null) {
                    toReturn[i][j] = list.get(i).get(j);
                } else {
                    toReturn[i][j] = 0L;
                }
            }
        }
        return toReturn;
    }
}
