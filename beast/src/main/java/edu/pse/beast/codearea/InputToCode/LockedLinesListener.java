/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

/**
 * This interface must be implemented by all classes which want to be
 * notified of lines being locked/unlocked by the lockedlineshandler
 * @author Holger-Desktop
 */
public interface LockedLinesListener {
    public void lockedLine(int lineNumber);
    public void unlockedLine(int lineNumber);
}
