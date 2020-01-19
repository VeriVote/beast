package edu.pse.beast.celectiondescriptioneditor.celectioncodearea;

//import java.util.List;
//
//import javax.swing.text.BadLocationException;
//
//import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CAntlrHandler;
//import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling
//        .CGrammarErrorFinder;
//import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling
//        .CVariableErrorFinder;
//import edu.pse.beast.codearea.CodeArea;
//
///**
// * This is the class which inherits from codearea to create a codearea more
// * suited towards writing c code.
// *
// * @author Holger Klein
// */
//public class CElectionCodeAreaJAVAFX extends CodeArea {
//
//    private final CAntlrHandler antlrHandler;
//    private final CGrammarErrorFinder grammerErrorFinder;
//   // private final CVariableErrorFinder varErrFinder;
//    private final CSyntaxHl cSyntaxHl;
//
//    /**
//     * constructor
//     * @param codeArea the code area to be associated with
//     */
//    public CElectionCodeAreaJAVAFX(CodeArea codeArea) {
//        super(codeArea);
//        antlrHandler = new CAntlrHandler(pane);
//        grammerErrorFinder = new CGrammarErrorFinder(antlrHandler);
//       // varErrFinder = new CVariableErrorFinder(pane);
//        //errorCtrl.addErrorFinder(grammerErrorFinder);
//       // errorCtrl.addErrorFinder(varErrFinder);
//        cSyntaxHl = new CSyntaxHl(antlrHandler, syntaxHL);
//    }
//
//    /**
//     * displays the given code to the user in the JTextPane
//     *
//     * @param code the code to be displayed
//     * @throws BadLocationException in case of a bad location
//     */
//    public void letUserEditCode(List<String> code) throws BadLocationException {
//        String s = "";
//        for (int i = 0; i < code.size(); ++i) {
//            s += code.get(i);
//            if (i != code.size() - 1) {
//                s += "\n";
//            }
//        }
//        pane.setText(s);
//        actionList.clear();
//    }
//}
