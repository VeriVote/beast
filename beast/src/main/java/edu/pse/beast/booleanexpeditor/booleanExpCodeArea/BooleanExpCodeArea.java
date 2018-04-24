//package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;
//
//import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorGrammarErrorFinder;
//import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpEditorVariableErrorFinder;
//import edu.pse.beast.codearea.CodeArea;
//
///**
// * Class extending CodeArea Class of package codearea and giving it extra functionality specific to BooleanExpEditor.
// * @author Nikolai
// */
//public class BooleanExpCodeArea extends CodeArea {
//    private final BooleanExpANTLRHandler antlrHandler;
//    private final BooleanExpEditorVariableErrorFinder variableErrorFinder;
//    private final BooleanExpSyntaxHL booleanExpSyntaxHL;
//
//    /**
//     * Constructor
//     * @param codeArea the codeArea object the super constructor is called with
//     * @param antlrHandler BooleanExpAntlrHandler object
//     * @param variableErrorFinder the BooleanExpEditorVariableErrorFinder object
//     */
//    BooleanExpCodeArea(CodeArea codeArea,
//                              BooleanExpANTLRHandler aHandler,
//                              BooleanExpEditorVariableErrorFinder variableErrorFinder) {
//        super(codeArea);
//        this.antlrHandler = aHandler;;
//        this.variableErrorFinder = variableErrorFinder;
//        booleanExpSyntaxHL = new BooleanExpSyntaxHL(antlrHandler, syntaxHL);
//    }
//
//
//
//    /**
//     * Getter for the BooleanExpEditorVariableErrorFinder object
//     * @return variableErrorFinder
//     */
//    public BooleanExpEditorVariableErrorFinder getVariableErrorFinder() {
//        return variableErrorFinder;
//    }    
//     
//}