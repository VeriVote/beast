package edu.pse.beast.toolbox.valueContainer;

/**
 * The Interface ResultValue.
 */
public interface ResultValue {

    /**
     * The Enum ResultType.
     */
    enum ResultType {

        /** The array. */
        ARRAY,
        /** The pointer. */
        POINTER,
        /** The single. */
        SINGLE,
        /** The struct. */
        STRUCT
    }

    /**
     * Gets the result type.
     *
     * @return the result type
     */
    ResultType getResultType();
}
