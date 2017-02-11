/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.booleanExpAST.SymbolicVarExp;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author Holger-Desktop
 */
public class BooleanExpErrorFactory {

    private static final  String[] errorIds = 
    {"antlr", "var_not_decl", "too_many_vars_passed", "wrong_var_type_passed",
        "incomparable_types", "incomparable_list_sizes", "wrong_var_passed_to_votesum"};  
    
    private static int getErrorNum(String id) {
        for(int i = 0; i < errorIds.length; ++i) {
            if(errorIds[i].equals(id))
                return i;
        }
        return -1;
    }
    
    
    public static CodeError createVarNotDeclaredErr(FormalPropertyDescriptionParser.SymbolicVarExpContext ctx) {
        CodeError err = generateStandardError(ctx, "var_not_decl");
        err.setExtraInfo("var_name", ctx.Identifier().getText());
        return err;
    }

    public static CodeError createAntlrError(int line, int charInline, String msg) {
        CodeError err = new CodeError(line, charInline, "antlr",  getErrorNum("antlr"), 0, 0);
        err.setExtraInfo("msg", msg);
        return err;
    }

    public static CodeError createTooManyVarsPassedError(FormalPropertyDescriptionParser.PassSymbVarContext ctx) {
           
        CodeError err = generateStandardError(ctx, "too_many_vars_passed");
        err.setExtraInfo("var_name", ctx.symbolicVarExp().Identifier().getText());
        return err;
    }

    static CodeError createWrongVarTypePassed(InternalTypeContainer cont,
            FormalPropertyDescriptionParser.PassSymbVarContext ctx,
            SymbolicVarExp currentVarExp) {
        
        CodeError err = generateStandardError(ctx, "wrong_var_type_passed");
        
        err.setExtraInfo("var_name", ctx.symbolicVarExp().Identifier().getText());
        err.setExtraInfo("passed_type", currentVarExp.getSymbolicVar().getInternalTypeContainer().getInternalType().toString());
        err.setExtraInfo("expected_type", cont.getAccesTypeIfList().toString());
        return err;
    }
    
    static CodeError createCantCompareDifferentListLevels(FormalPropertyDescriptionParser.ComparisonExpContext ctx, InternalTypeContainer lhsCont, InternalTypeContainer rhsCont) {
        CodeError err = generateStandardError(ctx, "incomparable_list_sizes");
        err.setExtraInfo("lhs_list_size", String.valueOf(lhsCont.getListLvl()));
        err.setExtraInfo("rhs_list_size", String.valueOf(rhsCont.getListLvl()));
        return err;
    }

    static CodeError createCantCompareTypes(FormalPropertyDescriptionParser.ComparisonExpContext ctx, InternalTypeContainer lhsCont, InternalTypeContainer rhsCont) {
        CodeError err = generateStandardError(ctx, "incomparable_types");
        err.setExtraInfo("lhs_type", lhsCont.getInternalType().toString());
        err.setExtraInfo("rhs_type", rhsCont.getInternalType().toString());
        return err;
    }
    
    static CodeError createWrongVarToVotesumError(FormalPropertyDescriptionParser.VoteSumExpContext ctx, SymbolicVariable symbolicVar) {
        CodeError err = generateStandardError(ctx, "wrong_var_passed_to_votesum");
        err.setExtraInfo("var_name", symbolicVar.getId());
        err.setExtraInfo("var_type", symbolicVar.getInternalTypeContainer().getInternalType().toString());
        return err;
    }
    
    private static CodeError generateStandardError(ParserRuleContext ctx, String id) {
        int pos = ctx.getStart().getStartIndex();
        int endPos = ctx.getStop().getStopIndex();
        int line = ctx.getStart().getLine();
        int charInLine = ctx.getStart().getCharPositionInLine(); 
        CodeError err = new CodeError(line, charInLine, id, getErrorNum(id), pos, endPos);
        return err;
    }

    

    
}
