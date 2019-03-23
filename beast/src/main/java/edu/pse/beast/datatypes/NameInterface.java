package edu.pse.beast.datatypes;

/**
 * Interface used by saverloader.FileChooser that allows it to change and
 * receive the Names of given objects.
 *
 * @author NikolaiLMS
 */
public interface NameInterface {

    /**
     * Setter
     *
     * @param newName sets the Name
     */
    void setNewName(String newName);

    /**
     * Getter
     *
     * @return the Name
     */
    String getName();
}