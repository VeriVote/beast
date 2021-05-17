package edu.pse.beast.api.c_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CBaseListener;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.IterationStatementContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;

public class AntlrCLoopParser extends CBaseListener {

	private List<ExtractedCLoop> extractedCLoops = new ArrayList<>();
	private CodeGenOptions codeGenOptions;
	private Stack<ExtractedCLoop> loopStack = new Stack<>();

	public static List<ExtractedCLoop> findLoops(String cCode,
			CodeGenOptions codeGenOptions) {

		CLexer l = new CLexer(CharStreams.fromString(cCode));
		final CommonTokenStream ts = new CommonTokenStream(l);
		CParser p = new CParser(ts);
		ParseTreeWalker walker = new ParseTreeWalker();
		AntlrCLoopParser listener = new AntlrCLoopParser();
		listener.codeGenOptions = codeGenOptions;
		walker.walk(listener, p.blockItemList());
		return listener.extractedCLoops;
	}

	public void enterIterationStatement(
			edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.IterationStatementContext ctx) {
		ExtractedCLoop extractedCLoop = new ExtractedCLoop(ctx, extractedCLoops.size(),
				codeGenOptions);
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
}
