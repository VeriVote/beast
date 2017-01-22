package toBeImplemented;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;

public class ElectionTypeContainerSaverLoader {

    public static List<String> createSaveFormat(ElectionTypeContainer container) {
        //TODO
        List<String> toReturn = new ArrayList<String>();
        
        //testdata
        toReturn.add("eTypeContainerDescr 1");
        toReturn.add("eTypeContainerDescr 2");
        toReturn.add("eTypeContainerDescr 3");
        toReturn.add("eTypeContainerDescr ...");
        
        return toReturn;
    }
    
}
