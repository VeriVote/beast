package edu.pse.beast.api.codegen;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CBaseListener;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CLexer;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser;

public class CodeGenCParser  {
	public static void parseC(String code) {
		CLexer l = new CLexer(CharStreams.fromString(code));
		final CommonTokenStream ts = new CommonTokenStream(l);
		CParser p = new CParser(ts);
		
		ParseTreeWalker walker = new ParseTreeWalker();
		CLoopFinder cLoopFinder = new CLoopFinder();
		walker.walk(cLoopFinder, p.blockItem());
	}
}
