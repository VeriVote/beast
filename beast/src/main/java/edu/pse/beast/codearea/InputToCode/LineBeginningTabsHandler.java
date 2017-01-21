/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public interface LineBeginningTabsHandler {
    public int getTabsForLine(int absPos);
}
