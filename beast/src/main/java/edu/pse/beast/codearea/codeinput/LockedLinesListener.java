package edu.pse.beast.codearea.codeinput;

/**
 * This interface must be implemented by all classes which want to be notified
 * of lines being locked/unlocked by the lockedlineshandler.
 *
 * @author Holger Klein
 */
public interface LockedLinesListener {

    /**
     * Locked line.
     *
     * @param lineNumber the line number
     */
    void lockedLine(int lineNumber);

    /**
     * Unlocked line.
     *
     * @param lineNumber the line number
     */
    void unlockedLine(int lineNumber);
}
