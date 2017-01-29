/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.toolbox.BooleanExpCodeToASTConverter;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslator;

/**
 *
 * @author Holger
 */
public class FormalPropertiesDescription {

    private String code;
   
    public FormalPropertiesDescription(String code) {
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
//    public BooleanExpListNode getAST(){
//        return astGenerator.generateFromSyntaxTree(code);
//    }
}
