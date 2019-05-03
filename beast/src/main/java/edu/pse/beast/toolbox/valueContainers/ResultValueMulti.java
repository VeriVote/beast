package edu.pse.beast.toolbox.valueContainers;

import java.util.ArrayList;
import java.util.List;

public class ResultValueMulti implements ResultValue {
    private List<ResultValue> values = new ArrayList<ResultValue>();

    @Override
    public void addValueAtPos(List<Integer> indices, String value) {
        checkIndex(indices);

        int index = indices.get(0);

        if (values.size() < index) {
            // extend the array if it is not big enough already
            if (indices.size() > 1) {
                // we have to insert another "Multi" value,
                                      // as we have to save more than one
                                      // data point
                for (int i = values.size(); i <= index; i++) {
                    values.add(new ResultValueMulti());
                }
            } else { // we have to save single objects
                for (int i = values.size(); i <= index; i++) {
                    values.add(new ResultValueSingle());
                }
            }
        }

        // the sublist contains all indices not used so far
        values.get(index).addValueAtPos(indices.subList(1, indices.size()),
                            value); // insert the object
    }

    @Override
    public String getValueAtPos(List<Integer> indices) {
        checkIndex(indices);
        int index = indices.get(0);
        // the sublist contains all indices not used so far
        return values.get(index).getValueAtPos(indices.subList(1, indices.size()));
    }

    private void checkIndex(List<Integer> indices) {
        if (indices == null) {
            throw new NullPointerException();
        }
        if (indices.size() == 0) {
            throw new IndexOutOfBoundsException(
                    "ResultValueSingle objects only contain one element, accessible at index 0");
        }
    }
}
