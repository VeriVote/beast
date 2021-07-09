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

    public Map<Class<? extends Exception>, ErrorMessage> loadMessages() {
        return Map.of(NotImplementedException.class,
                new ErrorMessage("Not implemented",
                        "tried to run code that hasn't been implemented yet"));
    }
}
