package edu.pse.beast.highlevel;

import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * This interface is implemented by every View which displays Strings. It is
 * used to switch between different languages easily.
 *
 * @author Holger Klein
 */
public interface DisplaysStringsToUser {
    /**
     * Updates all displayed String to match the chosen StringResource.
     *
     * @param stringResIF StringLoaderInterface with the new Strings for display
     */
    void updateStringRes(StringLoaderInterface stringResIF);
}
