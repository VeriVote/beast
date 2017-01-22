package edu.pse.beast.BooleanExpEditor;

import edu.pse.beast.toolbox.antlr.booleanexp.ANTLRBooleanExpressionListener;

/**
 * @author NikolaiLMS
 */
public class ANTLRTest {
    public static void main(String[] args) {
        ANTLRBooleanExpressionListener booleanExpressionListener = new ANTLRBooleanExpressionListener();
        booleanExpressionListener.printProperty("FOR_ALL_VOTERS(v) : VOTES1(v) == VOTES2(v)");
    }

}
