/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.codearea.ErrorHandling.CodeError;

/**
 * This class is used by the Cerrorfinder. It generates coderrors from the given
 * information
 *
 * @author holger
 */
public class CCodeErrorFactory {

    private static final String IDS[] = {"compilererror", "antlr"};

    /**
     * wraps the error from the compiler into a CodeError Object. If information for these parts are missing,
     * just pass "" for the strings, and 0 for the positions
     * @param line the line the error is in
     * @param posInLine the position in that line
     * @param varName the name of the variable that causes the error
     * @param message the message the compiler gave in full text
     * @return the created code error, containing all information given to this function
     */
    public static CodeError generateCompilerError(int line, int posInLine, String varName, String message) {
        CodeError toReturn = new CodeError(line, posInLine, IDS[0], 0, -1, -1);
        toReturn.setExtraInfo("var", varName);
        toReturn.setExtraInfo("msg", message);
        return toReturn;
    }

}
