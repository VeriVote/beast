package edu.pse.beast.types;

import java.util.List;

import edu.pse.beast.toolbox.CodeArrayListBeautifier;

/**
 * The class InternalTypeContainer.
 *
 * @author Niels Hanselmann
 */
public final class InternalTypeContainer extends InOutType {
    // TODO Extract to another interface

    /** The Constant PRIME_ONE. */
    private static final int PRIME = 31;

    /** The Constant PRIME_TWO. */
    private static final int PRIME_TWO = 1231;

    /** The Constant PRIME_THREE. */
    private static final int PRIME_THREE = 1237;

    /** The is list. */
    private final boolean isList;

    /** The internal type. */
    private final InternalTypeRep internalType;

    /** The listed type. */
    private final InternalTypeContainer listedType;

    /**
     * If the container is a list, say of votes, then the access type is the
     * type of the symbolic variable which can be used to access elements of the
     * list. Example: Every voter elects one candidate: VOTES1(v) <-- Access
     * type would be VOTER. Every voter lists candidates by preference:
     * VOTES1(v)(c) <--- Access type c would be CANDIDATE. This variable is also
     * used to determine the size of the list: Type size VOTER V CANDIDATE C
     * SEAT S.
     */
    private final InternalTypeRep accessTypeIfList;

    /**
     * Constructor for a listed TypeContainer.
     *
     * @param listType
     *            sets the type of the list elements
     * @param accTypeIfList
     *            sets the access type for the list elements
     */
    public InternalTypeContainer(final InternalTypeContainer listType,
                                 final InternalTypeRep accTypeIfList) {
        super(false, null, -1, null);
        this.isList = true;
        this.listedType = listType;
        this.accessTypeIfList = accTypeIfList;
        this.internalType = null;
    }

    /**
     * Constructor for a TypeContainer that is NOT a List.
     *
     * @param internType
     *            the type of this election
     */
    public InternalTypeContainer(final InternalTypeRep internType) {
        super(false, null, 1, null);
        this.isList = false;
        this.internalType = internType;
        this.listedType = null;
        this.accessTypeIfList = null;
    }

    /**
     * Returns if the TypeContainer is a list.
     *
     * @return isList
     */
    public boolean isList() {
        return isList;
    }

    /**
     * Getter for internalType.
     *
     * @return the internalType
     */
    public InternalTypeRep getInternalType() {
        return internalType;
    }

    /**
     * Getter for listedType. If isList is false it returns null.
     *
     * @return returns listedType
     */
    public InternalTypeContainer getListedType() {
        return listedType;
    }

    /**
     * Returns NULL if it is not a list.
     *
     * @return the access type of the list
     */
    public InternalTypeRep getAccessTypeIfList() {
        return accessTypeIfList;
    }

    /**
     * Gets the list lvl.
     *
     * @return the amount levels that this Container is made up of lists
     */
    public int getListLvl() {
        InternalTypeContainer cont = this;
        int lvl = 0;
        while (cont.isList) {
            cont = cont.getListedType();
            lvl++;
        }
        return lvl;
    }

    @Override
    public int hashCode() {
        int result = PRIME
                + (accessTypeIfList != null ? accessTypeIfList.hashCode() : 0);
        result = PRIME * result
                + ((internalType != null) ? internalType.hashCode() : 0);
        result = PRIME * result + (isList ? PRIME_TWO : PRIME_THREE);
        result = PRIME * result
                + (listedType != null ? listedType.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        InternalTypeContainer other = (InternalTypeContainer) obj;

        if (accessTypeIfList != other.accessTypeIfList) {
            return false;
        }
        if (internalType != other.internalType) {
            return false;
        }
        if (isList != other.isList) {
            return false;
        }
        return listedType != null ? listedType.equals(other.listedType)
                : other.listedType == null;
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return this;
    }

    @Override
    public String otherToString() {
        return "";
    }

    @Override
    public String getInfo() {
        return "";
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(final CodeArrayListBeautifier code,
                                            final String valueName,
                                            final List<String> loopVariables) {
        // TODO Auto-generated method stub
    }
}
