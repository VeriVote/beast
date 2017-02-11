package edu.pse.beast.saverloader;

/**
 * Interface used by the different GUIs to assess whether there have been changes made since an object was loaded/saved.
 * @author NikolaiLMS
 */
public class ChangeHandler {
    private boolean hasChanged;

    /**
     * @return true if the currently loaded object differs from what is currently displayed
     * in BooleanExpEditorWindow, false otherwise
     */
    public boolean hasChanged(){
        return hasChanged();
    }
}
