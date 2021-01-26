package edu.pse.beast.api.codegen;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.helperfunctions.HelperFunction;
import edu.pse.beast.api.codegen.helperfunctions.IntersectHelperFunction;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectVotesContext;

public class FindNeededHelperFunctions extends FormalPropertyDescriptionBaseListener {
	private AllInputAndOutputTypes allInputAndOutputTypes;
	private Map<String, HelperFunction> generated = new HashMap<>();

	public FindNeededHelperFunctions(AllInputAndOutputTypes allInputAndOutputTypes) {
		this.allInputAndOutputTypes = allInputAndOutputTypes;
	}

	public static Map<String, HelperFunction> findNeededFunctions(String boolExpCode,
			AllInputAndOutputTypes allInputAndOutputTypes) {
		final FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(
				CharStreams.fromString(boolExpCode));
		final CommonTokenStream ts = new CommonTokenStream(l);
		final FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);

		ParseTreeWalker walker = new ParseTreeWalker();
		FindNeededHelperFunctions listener = new FindNeededHelperFunctions(allInputAndOutputTypes);
		walker.walk(listener, p.booleanExpList());

		return listener.generated;
	}

	@Override
	public void exitIntersectVotes(IntersectVotesContext ctx) {
		IntersectHelperFunction func = new IntersectHelperFunction(allInputAndOutputTypes.getInputStruct());
		generated.put(func.getUUID(), func);
	}

}
