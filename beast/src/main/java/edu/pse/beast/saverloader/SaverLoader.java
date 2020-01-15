package edu.pse.beast.saverloader;

/**
 * Interface that allows the FileChooser to polymorphically save and load files.
 * @param <T>
 *
 * @author Nikolai Schnell
 */
public interface SaverLoader<T> {

    /**
     * Creates an object from a given, by createSaveString() generated, saveString.
     *
     * @param saveString the saveString
     * @return the object
     * @throws Exception if the saveString does not contain a valid format
     */
    T createFromSaveString(String saveString) throws Exception;

    /**
     * Creates a String from a given object, that can then be saved to a file and
     * later given to createFromSaveString() to retrieve the saved object.
     *
     * @param toSave the Object to save
     * @return the saveString
     */
    String createSaveString(T toSave);
}
