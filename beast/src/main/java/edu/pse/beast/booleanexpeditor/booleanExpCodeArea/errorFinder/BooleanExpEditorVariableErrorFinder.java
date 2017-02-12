package edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpANTLRHandler;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * Class for finding type-errors in symbolic variable usage in the
 * BooleanExpression(s) of the CodeArea this class is an attribute of.
 *
 * @author Nikolai
 */
public class BooleanExpEditorVariableErrorFinder implements ErrorFinder, ElectionDescriptionChangeListener {

    private final BooleanExpANTLRHandler antlrHandler;
    private PostAndPrePropertiesDescription description;
    private final FormalExpErrorFinderTreeListener lis;

    /**
     * Constructor
     *
     * @param antlrHandler BooleanExpEditorANTLRHandler object this class uses
     * to find errors
     */
    public BooleanExpEditorVariableErrorFinder(
            BooleanExpANTLRHandler antlrHandler,
            SymbolicVariableList list, CElectionDescriptionEditor ceditor) {
        this.antlrHandler = antlrHandler;
        lis = new FormalExpErrorFinderTreeListener(list, ceditor);
    }

    @Override
    public ArrayList<CodeError> getErrors() {
        ParseTree tree = antlrHandler.getParseTree();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(lis, tree);
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

    public FormalExpErrorFinderTreeListener getLis() {
        return lis;
    }

}
