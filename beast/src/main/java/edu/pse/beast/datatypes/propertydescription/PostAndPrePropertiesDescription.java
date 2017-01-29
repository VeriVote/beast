/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslator;
import java.util.LinkedList;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author Niels
 */
public class PostAndPrePropertiesDescription {

    private final String name;
    private SymbolicVariableList symbolicVariableList;
    private FormalPropertiesDescription prePropertiesDescription;
    private FormalPropertiesDescription postPropertiesDescription;

    /**
     * 
     * @param name HAS to be UNIQUE in the context
     */
    public PostAndPrePropertiesDescription(String name) {
        this.name = name;
    }    
    /**
     * 
     * @param name HAS to be UNIQUE in the context
     * @param preDescr the prePropterie Description of the Property
     * @param postDescr the postPropterie Description of the Property
     */
    public PostAndPrePropertiesDescription(String name, FormalPropertiesDescription preDescr,
            FormalPropertiesDescription postDescr) {
        this.name = name;
        this.prePropertiesDescription = preDescr;
        this.postPropertiesDescription = postDescr;
    }

    public PostAndPrePropertiesDescription(String name, FormalPropertiesDescription preDescr,
                                           FormalPropertiesDescription postDescr, SymbolicVariableList symbolicVariableList) {
        this.name = name;
        this.prePropertiesDescription = preDescr;
        this.postPropertiesDescription = postDescr;
        this.symbolicVariableList = symbolicVariableList;
    }

    
    public String getName() {
    	return this.name;
    }
    

    public LinkedList<SymbolicVariable> getSymbolicVariableList() {
        return symbolicVariableList.getSymbolicVariables();
    }
    public SymbolicVariableList getSymVarList() {
    	return symbolicVariableList;
    }

    public void setSymbolicVariableList(SymbolicVariableList symbolicVariableList) {
        this.symbolicVariableList = symbolicVariableList;
    }

    public FormalPropertiesDescription getPostPropertiesDescription() {
        return postPropertiesDescription;
    }

    public FormalPropertiesDescription getPrePropertiesDescription() {
        return prePropertiesDescription;
    }
}
