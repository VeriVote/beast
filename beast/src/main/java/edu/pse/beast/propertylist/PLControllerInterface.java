package edu.pse.beast.propertylist;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ResultInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;

public interface PLControllerInterface {

    void changeName(PropertyItem prop, String newName);

    void setTestStatus(PropertyItem prop, boolean newStatus);

    void editProperty(PropertyItem prop);

    void deleteProperty(PropertyItem prop);

    void addDescription(PropertyItem prop);

    void addNewProperty();

    void presentResult(ResultInterface res);
}
