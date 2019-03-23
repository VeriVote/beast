package edu.pse.beast.codearea.codeinput;

/**
 * This interface must be implemented by all classes which want to be notified
 * of lines being locked/unlocked by the lockedlineshandler
 *
 * @author Holger-Desktop
 */
public interface LockedLinesListener {
    public void lockedLine(int lineNumber);

    public void unlockedLine(int lineNumber);
}