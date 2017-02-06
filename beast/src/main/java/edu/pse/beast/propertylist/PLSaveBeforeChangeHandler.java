package edu.pse.beast.propertylist;

import java.util.Observable;
import java.util.Observer;
import edu.pse.beast.propertylist.Model.PLModelInterface;

/**
 * @author Justin
 *
 */
public class PLSaveBeforeChangeHandler implements Observer {

    private boolean changedSinceSave = false;

    private String saveLocation;

    public PLSaveBeforeChangeHandler(PLModelInterface model, String saveLocation) {
        model.addObserver(this);
        this.saveLocation = saveLocation;
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setChangedSinceSave(true);
    }

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
