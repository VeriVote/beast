package edu.pse.beast.types;

public interface InOutType {

    InternalTypeContainer getInternalTypeContainer();

    /**
     * A human readable representation of this type.
     * @return
     */
    String otherToString();
}
