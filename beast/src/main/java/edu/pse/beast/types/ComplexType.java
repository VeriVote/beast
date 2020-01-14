package edu.pse.beast.types;

public abstract class ComplexType {

    public abstract boolean equals(Object toCompare);

    /**
     *
     * @return the string which defines this struct
     */
    public abstract String getStructDefinition();

    public abstract String getStructAccess();
}
