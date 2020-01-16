package edu.pse.beast.types;

/**
 * The Class ComplexType.
 */
public abstract class ComplexType {

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object toCompare);

    /**
     * Gets the struct definition.
     *
     * @return the string which defines this struct
     */
    public abstract String getStructDefinition();

    /**
     * Gets the struct access.
     *
     * @return the struct access
     */
    public abstract String getStructAccess();
}
