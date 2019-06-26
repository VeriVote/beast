package edu.pse.beast.types;

import java.util.List;

import edu.pse.beast.propertychecker.Result;

public interface InOutType {

    InternalTypeContainer getInternalTypeContainer();

    /**
     * A human readable representation of this type.
     * @return
     */
    String otherToString();
    
    /**
     * 
     * @param result the result to be presented
     * @param startY the y position to start the drawing at
     * @return the bottom most y-position the presentation has
     */
    List<String> drawResult(Result result);
    

    /**
     * 
     * @return a text describing everything the user needs to know about this type (e.g description of structs...)
     */
	String getInfo();
}
