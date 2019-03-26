//package edu.pse.beast.propertylist;
//
//
//import java.util.Observable;
//import java.util.Observer;
//
//import edu.pse.beast.propertylist.model.PLModel;
//
///**
// * Handles saves before change in the property list.
// * @author Justin Hecht
// *
// */
//public class PLChangeHandler implements Observer {
//    private boolean hasChanged = false;
//
//    /**
//     * Constructor
//     * @param model The property list data model to observe
//     */
//    public PLChangeHandler(PLModel model) {
//        model.addObserver(this);
//    }
//
//
//    @Override
//    public void update(Observable o, Object arg) {
//        this.setChangedSinceSave(true);
//    }
//
//    // getter and setter
//    public boolean hasChanged() {
//        return hasChanged;
//    }
//
//    public void setChangedSinceSave(boolean changedSinceSave) {
//        this.hasChanged = changedSinceSave;
//    }
//}