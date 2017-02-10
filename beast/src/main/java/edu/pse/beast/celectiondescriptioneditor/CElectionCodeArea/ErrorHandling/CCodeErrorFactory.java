/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.codearea.ErrorHandling.CodeError;

/**
 *
 * @author holger
 */
public class CCodeErrorFactory {
    
    private static String ids[] = {"compilererror"};
    public static CodeError generateCompilterError(int line, int posInLine, String varName, String message) {
        CodeError toReturn = new CodeError(line - 3*3, posInLine, ids[0], 0, -1, -1);
        toReturn.setExtraInfo("varname", varName);
        toReturn.setExtraInfo("msg", message);
        
        return toReturn;
    }
}
