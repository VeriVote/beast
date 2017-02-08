package edu.pse.beast.saverloader;

/**
 * @author NikolaiLMS
 */
public interface SaverLoader {
    Object createFromSaveString(String saveString) throws Exception;
    String createSaveString(Object object) throws Exception;
}
