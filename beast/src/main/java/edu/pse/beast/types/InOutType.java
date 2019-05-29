package edu.pse.beast.types;

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
    int drawResult(Result result, double startY);
}
