package edu.pse.beast.types;

/**
 *
 * @author Niels Hanselmann
 */
public class InternalTypeContainer implements InOutType {

    private final boolean isList;
    private final InternalTypeRep internalType;
    private final InternalTypeContainer listedType;

    /**
     * If the container is a list, say of votes, then the access type is the type of
     * the symbolic variable which can be used to access elements of the list. Example:
     * Every voter elects one candidate: VOTES1(v) <-- access type would be VOTER. Every
     * voter lists candidates by preference: VOTES1(v)(c) <--- access type c would be
     * CANDIDATE. This variable is also used to determine the size of the list: type size
     * VOTER V CANDIDATE C SEAT S
     */
    private final InternalTypeRep accessTypeIfList;

    /**
     * Constructor for a listed TypeContainer
     *
     * @param listedType      sets the type of the list elements
     * @param accessTypeIfList sets the access type for the list elements
     */
    public InternalTypeContainer(InternalTypeContainer listedType,
                                 InternalTypeRep accessTypeIfList) {
        this.isList = true;
        this.listedType = listedType;
        this.accessTypeIfList = accessTypeIfList;
        this.internalType = null;
    }

    /**
     * Constructor for a TypeContainer that is NOT a List
     *
     * @param internalType the type of this election
     */
    public InternalTypeContainer(InternalTypeRep internalType) {
        this.isList = false;
        this.internalType = internalType;
        this.listedType = null;
        this.accessTypeIfList = null;
    }

    /**
     * Returns if the TypeContainer is a list
     *
     * @return isList
     */
    public boolean isList() {
        return isList;
    }

    /**
     * getter for internalType
     *
     * @return returns internalType
     */
    public InternalTypeRep getInternalType() {
        return internalType;
    }

    /**
     * getter for listedType If isList is false it returns null
     *
     * @return returns listedType
     */
    public InternalTypeContainer getListedType() {
        return listedType;
    }

    /**
     * returns NULL if it is not a list
     *
     * @return the access type of the list
     */
    public InternalTypeRep getAccessTypeIfList() {
        return accessTypeIfList;
    }

    /**
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
        int result = 31 + (accessTypeIfList != null ? accessTypeIfList.hashCode() : 0);
        result = 31 * result + ((internalType != null) ? internalType.hashCode() : 0);
        result = 31 * result + (isList ? 1231 : 1237);
        result = 31 * result + (listedType != null ? listedType.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
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
        return listedType != null
                ? listedType.equals(other.listedType)
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
}