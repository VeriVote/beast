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

    private static final String ids[] = {"compilererror", "antlr"};

    public static CodeError generateCompilerError(int line, int posInLine, String varName, String message) {
        CodeError toReturn = new CodeError(line - 10, posInLine, ids[0], 0, -1, -1);
        toReturn.setExtraInfo("var", varName);
        toReturn.setExtraInfo("msg", message);
        return toReturn;
    }

}
