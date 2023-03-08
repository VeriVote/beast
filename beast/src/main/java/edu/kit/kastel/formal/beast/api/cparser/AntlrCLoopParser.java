package edu.kit.kastel.formal.beast.api.cparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.method.antlr.CBaseListener;
import edu.kit.kastel.formal.beast.api.method.antlr.CLexer;
import edu.kit.kastel.formal.beast.api.method.antlr.CParser;

/**
 * Uses antlr C grammar to extract information
 * about c loops in election descriptions.
 *
 * @author Holger Klein
 *
 */
public class AntlrCLoopParser extends CBaseListener {

    private static List<ExtractedCLoop> extractedCLoops = new ArrayList<ExtractedCLoop>();
    private static Stack<ExtractedCLoop> loopStack = new Stack<ExtractedCLoop>();
    private static int amtLoops;

    public static List<ExtractedCLoop> findLoops(final String functionName,
                                                 final String cCode,
                                                 final CodeGenOptions codeGenOptions) {
        final CLexer l = new CLexer(CharStreams.fromString(cCode));
        final CommonTokenStream ts = new CommonTokenStream(l);
        final CParser p = new CParser(ts);
        final ParseTreeWalker walker = new ParseTreeWalker();
        extractedCLoops.clear();
        loopStack.clear();

        walker.walk(new CBaseListener() {
            public void enterIterationStatement(final CParser.IterationStatementContext ctx) {
                final ExtractedCLoop extractedCLoop =
                        new ExtractedCLoop(ctx, extractedCLoops.size(),
                                           codeGenOptions, functionName);
                if (!loopStack.isEmpty()) {
                    extractedCLoop.setParentLoop(loopStack.peek());
                    loopStack.peek().addChild(extractedCLoop);
                }
                extractedCLoops.add(extractedCLoop);
                loopStack.push(extractedCLoop);
            };

            @Override
            public void exitIterationStatement(final CParser.IterationStatementContext ctx) {
                loopStack.pop();
            }
        }, p.blockItemList());
        return extractedCLoops;
    }

    public static int getAmtLoops(final String cCode) {
        final CLexer l = new CLexer(CharStreams.fromString(cCode));
        final CommonTokenStream ts = new CommonTokenStream(l);
        final CParser p = new CParser(ts);
        final ParseTreeWalker walker = new ParseTreeWalker();
        amtLoops = 0;
        walker.walk(new CBaseListener() {
            @Override
            public void enterIterationStatement(final CParser.IterationStatementContext ctx) {
                amtLoops++;
            }
        }, p.blockItemList());
        return amtLoops;
    }
}
