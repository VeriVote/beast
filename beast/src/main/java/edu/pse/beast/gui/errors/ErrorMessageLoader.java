package edu.pse.beast.gui.errors;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

/**
 * This class can be extended to load error messages from a file
 * 
 * @author holge
 *
 */
public class ErrorMessageLoader {

    public Map<BeastErrorTypes, ErrorMessage> loadMessages() {
        return Map.of(BeastErrorTypes.NOT_IMPLEMENTED_CODE_PATH,
                new ErrorMessage("Not implemented",
                        "tried to run code that hasn't been implemented yet"));
    }
}
