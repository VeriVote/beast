package edu.pse.beast.toolbox.valueContainer;

public interface ResultValue {
    enum ResultType {
        ARRAY, POINTER, SINGLE, STRUCT
    }

    public ResultType getResultType();
}