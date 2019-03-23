package edu.pse.beast.codearea.codeinput;

/**
 * This interface must be implemented by all classes which want to be notified
 * of lines being locked/unlocked by the lockedlineshandler
 *
 * @author Holger-Desktop
 */
public interface LockedLinesListener {
    void lockedLine(int lineNumber);

    void unlockedLine(int lineNumber);
}