package edu.pse.beast.toolbox.valueContainers;

import java.util.List;

public class ResultValueSingle implements ResultValue {

    private boolean set = false;
    private String value = "";

    @Override
    public void addValueAtPos(List<Integer> indices, String value) {
        checkIndex(indices);
        this.value = value;
        this.set = true;
    }

    @Override
    public String getValueAtPos(List<Integer> indices) {
        checkIndex(indices);

        if (!set) {
            System.err.println("The specified element was not set. This should not happen!");
        }
        return value;
    }

    private void checkIndex(List<Integer> indices) {
        if (indices.size() != 0) {
            throw new IndexOutOfBoundsException(
                    "ResultValueSingle objects only contain one element, "
                            + "therefore the index list has to be empty");
        }
    }

}
