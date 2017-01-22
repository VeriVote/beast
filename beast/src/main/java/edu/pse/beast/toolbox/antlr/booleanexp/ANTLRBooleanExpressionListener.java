package edu.pse.beast.toolbox.antlr.booleanexp;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author NikolaiLMS
 */
public class ANTLRBooleanExpressionListener extends BooleanExpressionBaseListener {
    @Override
    public void enterProperty(BooleanExpressionParser.PropertyContext ctx) {
        System.out.println(ctx.getText());
    }
    public void printProperty(String drinkSentence) {
        // Get our lexer
        BooleanExpressionLexer lexer = new BooleanExpressionLexer(new ANTLRInputStream(drinkSentence));

        // Get a list of matched tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Pass the tokens to the parser
        BooleanExpressionParser parser = new BooleanExpressionParser(tokens);

        // Specify our entry point
        BooleanExpressionParser.PropertyContext drinkSentenceContext = parser.property();

        // Walk it and attach our listener
        ParseTreeWalker walker = new ParseTreeWalker();
        ANTLRBooleanExpressionListener listener = new ANTLRBooleanExpressionListener();
        walker.walk(listener, drinkSentenceContext);
    }
}
