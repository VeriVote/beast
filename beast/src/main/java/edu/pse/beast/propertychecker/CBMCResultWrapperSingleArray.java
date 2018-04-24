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
    private final List<String> list = new ArrayList<String>();

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
    public void addTo(int index, String toAdd) {
        if (list.size() > index) {
            list.set(index, toAdd);
        } else {
            for (int i = list.size(); i <= index; i++) {
                list.add("0");
            }
            list.set(index, toAdd);
        }
    }
    
    /**
     * returns the two dimensional list that is saved here. Only use this if you
     * really need it, because there is also and method that creates an array
     * from these, which makes sure that there are no empty parts in the lists
     * 
     * @return the list that describes this variable
     */
    public List<String> getList() {
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
    public String[] getArray() {
        String[] toReturn;
        if ((list != null) && (list.size() > 0)) {
            toReturn = new String[list.size()];
        } else {
            toReturn = new String[0];
        }

        for (int i = 0; i < toReturn.length; i++) {
            if (list.size() >= i && list.get(i) != null) {
                toReturn[i] = list.get(i);
            } else {
                toReturn[i] = "0";
            }
        }
        return toReturn;
    }
    
    /**
     * 
     * @return wraps the single array into an array in the two dimensional format.
     * This is specifically targeted for the single_choice option, so the result will
     * be a sparse array in which only one entry in each row will be one, every other entry will be
     * zero
     */
    public CBMCResultWrapperMultiArray wrapInTwoDim(int index, String name, int amountCandidates) {
    	
    	CBMCResultWrapperMultiArray twoDimArr = new CBMCResultWrapperMultiArray(index, name);
    	
    	String[] asArray = getArray();
    	
    	
    	for(int i = 0; i < asArray.length; i++) {
    		long currentCandidate = Long.parseLong(asArray[i]);
    		
    		if(currentCandidate == 0L) {
    			//add 0 to the last candidate position, so the whole row will be zeros
    			twoDimArr.addTo(i, amountCandidates - 1,  "0");
    		} else {
    			twoDimArr.addTo(i, (int) (currentCandidate - 1), "1");
    		}
    	}
    	
    	return twoDimArr;
    }
}
