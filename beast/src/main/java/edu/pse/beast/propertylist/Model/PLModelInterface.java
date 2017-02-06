package edu.pse.beast.propertylist.Model;

import java.util.ArrayList;
import java.util.Observer;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ResultInterface;

public interface PLModelInterface {

    void initialize();

    boolean changeName(PropertyItem prop, String newName);

    boolean addDescription(PropertyItem prop);

    boolean addNewProperty(BooleanExpEditor editor);

    void editProperty(PropertyItem prop, BooleanExpEditor editor);

    boolean deleteProperty(PropertyItem prop);

    void setTestStatus(PropertyItem prop, boolean newStatus);

    void userActionNewList();

    ArrayList<PropertyItem> getList();

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    boolean setNextToBePresented(ResultInterface res);

    void resetResults();
}
