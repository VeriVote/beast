package edu.pse.beast.api.c_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CBaseListener;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.IterationStatementContext;

public class AntlrCLoopParser extends CBaseListener {

	private static List<ExtractedCLoop> extractedCLoops = new ArrayList<>();
	private static Stack<ExtractedCLoop> loopStack = new Stack<>();
	private static int amtLoops = 0;

	public static List<ExtractedCLoop> findLoops(String cCode,
			CodeGenOptions codeGenOptions) {

		CLexer l = new CLexer(CharStreams.fromString(cCode));
		final CommonTokenStream ts = new CommonTokenStream(l);
		CParser p = new CParser(ts);
		ParseTreeWalker walker = new ParseTreeWalker();
		extractedCLoops.clear();
		loopStack.clear();

		walker.walk(new CBaseListener() {
			public void enterIterationStatement(
					IterationStatementContext ctx) {
				ExtractedCLoop extractedCLoop = new ExtractedCLoop(ctx,
						extractedCLoops.size(), codeGenOptions);
				if (!loopStack.isEmpty()) {
					extractedCLoop.setParentLoop(loopStack.peek());
				}
				extractedCLoops.add(extractedCLoop);
				loopStack.push(extractedCLoop);
			};

			@Override
			public void exitIterationStatement(IterationStatementContext ctx) {
				loopStack.pop();
			}
		}, p.blockItemList());
		return extractedCLoops;
	}

	public static int getAmtLoops(String cCode) {
		CLexer l = new CLexer(CharStreams.fromString(cCode));
		final CommonTokenStream ts = new CommonTokenStream(l);
		CParser p = new CParser(ts);
		ParseTreeWalker walker = new ParseTreeWalker();
		amtLoops = 0;
		walker.walk(new CBaseListener() {
			@Override
			public void enterIterationStatement(IterationStatementContext ctx) {
				amtLoops++;
			}
		}, p.blockItemList());
		return amtLoops;
	}

}
