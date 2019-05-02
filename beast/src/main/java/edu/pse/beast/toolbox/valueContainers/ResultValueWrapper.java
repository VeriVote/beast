package edu.pse.beast.toolbox.valueContainers;

import java.util.ArrayList;
import java.util.List;

public class ResultValueWrapper {

    private final int mainIndex;
    private final String name;
    private final int dimension;

    private final ResultValue valueContainer;

    /**
     * creates a new wrapper
     *
     * @param mainIndex the index of this variable (for example votes1 has the main
     *                  index of 1)
     * @param name      the name of this variable (for example votes1 has the name
     *                  votes)
     */
    public ResultValueWrapper(int mainIndex, String name, int dimension) {
        this.mainIndex = mainIndex;
        this.name = name;
        this.dimension = dimension;

        if (dimension >= 1) {
            valueContainer = new ResultValueMulti();
        } else {
            valueContainer = new ResultValueSingle();
        }
    }

    /**
     * saves an element at the specified positions
     * @param indices the indices which describe the position of the element the user wants to set
     * @param toAdd the value to add
     */
    public void addTo(List<Integer> indices, String toAdd) {
        checkIndex(indices);

        valueContainer.addValueAtPos(indices, toAdd);
    }
    
    /**
     * Assumes that only a single element should be saved here
     * @param toAdd the value to add
     */
    public void addLong(String toAdd) {
        addTo(new ArrayList<Integer>(), toAdd);
    }

    /**
     * returns the value stored at the specified position
     * @param indices
     * @return
     */
    public String getValueAtPos(List<Integer> indices) {
        checkIndex(indices);
        
        return valueContainer.getValueAtPos(indices);
    }
    
    /**
     *
     * @return returns the main index (for example votes1 has the mainIndex of 1)
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
     * checks if the given indices have the right dimension to be called upon
     * @param indices
     */
    private void checkIndex(List<Integer> indices) {
        if (indices.size() != dimension) {
            throw new IndexOutOfBoundsException(
                    "The dimension of the requested object does not match the dimension of this obejct");
        }
    }

    @Deprecated
    public List<List<String>> getList() {
        System.err.println("DO NOT USE THIS ANYMORE. TODO, delete when all dependencies are removed");
        return null;
    }
}
