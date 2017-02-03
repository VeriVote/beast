package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import edu.pse.beast.datatypes.descofvoting.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScopehandler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.SyntaxTree;

/**
 * Class for finding type-errors in symbolic variable usage in the BooleanExpression(s) of the CodeArea
 * this class is an attribute of.
 * @author Nikolai
 */
public class BooleanExpEditorVariableErrorFinder implements ErrorFinder, ElectionDescriptionChangeListener {
    private BooleanExpANTLRHandler antlrHandler;
    private PostAndPrePropertiesDescription description;
    private FormalExpErrorFinderTreeListener lis;
    /**
     * Constructor
     * @param antlrHandler BooleanExpEditorANTLRHandler object this class uses to find errors
     */
    public BooleanExpEditorVariableErrorFinder(
            BooleanExpANTLRHandler antlrHandler,
            SymbolicVariableList list) {
        this.antlrHandler = antlrHandler;
        lis = new FormalExpErrorFinderTreeListener(list);
    }

     public void setUp(
            BooleanExpScopehandler scopeHandler,
            ElectionTypeContainer input,
            ElectionTypeContainer output) {
         lis.setUp(scopeHandler, input, output);        
    }
     
     public void setInput(ElectionTypeContainer input) {
        lis.setInput(input);
    }

    public void setOutput(ElectionTypeContainer output) {
        lis.setOutput(output);
    }     
    
    @Override
    public ArrayList<CodeError> getErrors() {        
        System.out.println("formal prop searching variable errors");
        
        try {
            ParseTree tree = antlrHandler.getParseTree();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(lis, tree);
        } catch (BadLocationException ex) {
            Logger.getLogger(BooleanExpEditorVariableErrorFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lis.getErrors();
    }

    @Override
    public void inputChanged(ElectionTypeContainer input) {
        lis.setInput(input);
    }

    @Override
    public void outputChanged(ElectionTypeContainer output) {
       lis.setOutput(output);
    }
}
