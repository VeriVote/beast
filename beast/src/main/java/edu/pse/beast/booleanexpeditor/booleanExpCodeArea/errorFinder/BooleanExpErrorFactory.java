/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpErrorFactory {

    private static String[] errorIds = {"antlr", "var_not_decl"};  
    
    private static int getErrorNum(String id) {
        for(int i = 0; i < errorIds.length; ++i) {
            if(errorIds[i].equals(id))
                return i;
        }
        return -1;
    }
    
    
    public static CodeError createVarNotDeclaredErr(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {
        int pos = ctx.getStart().getStartIndex();
        int line = ctx.getStart().getLine();
        int charInLine = ctx.getStart().getCharPositionInLine();
        CodeError err = new CodeError(line, charInLine, "var_not_decl", getErrorNum("var_not_decl"));
        err.setExtraInfo("var_name", ctx.Identifier().getText());
        return err;
    }

    static CodeError createAntlrError(int line, int charInline, String msg) {
        CodeError err = new CodeError(line, charInline, "antlr",  getErrorNum("antlr"));
        err.setExtraInfo("msg", msg);
        return err;
    }
}
