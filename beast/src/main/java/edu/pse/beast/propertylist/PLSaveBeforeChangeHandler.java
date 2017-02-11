package edu.pse.beast.propertylist;

import java.util.Observable;
import java.util.Observer;
import edu.pse.beast.propertylist.Model.PLModelInterface;

/**
 * Handles saves before change in the property list.
 * @author Justin
 *
 */
public class PLSaveBeforeChangeHandler implements Observer {

    private boolean changedSinceSave = false;

    private String saveLocation;

    /**
     * Constructor
     * @param model The property list data model to observe
     * @param saveLocation The current location for saving files
     */
    public PLSaveBeforeChangeHandler(PLModelInterface model, String saveLocation) {
        model.addObserver(this);
        this.saveLocation = saveLocation;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setChangedSinceSave(true);
    }

    // getter and setter
    public boolean isChangedSinceSave() {
        return changedSinceSave;
    }

    public void setChangedSinceSave(boolean changedSinceSave) {
        this.changedSinceSave = changedSinceSave;
    }

    public String getSaveLocation() {
        return saveLocation;
    }

    public void setSaveLocation(String saveLocation) {
        this.saveLocation = saveLocation;
    }

}
