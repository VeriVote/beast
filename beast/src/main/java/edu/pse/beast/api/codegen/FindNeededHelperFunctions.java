package edu.pse.beast.api.codegen;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.pse.beast.api.codegen.helperfunctions.HelperFunction;
import edu.pse.beast.api.codegen.helperfunctions.HelperFunctionMap;
import edu.pse.beast.api.codegen.helperfunctions.IntersectionHelperFunction;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionBaseListener;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.IntersectVotesContext;

public class FindNeededHelperFunctions extends FormalPropertyDescriptionBaseListener {
	private InputAndOutputElectionStructs allInputAndOutputTypes;
	private HelperFunctionMap map = new HelperFunctionMap();

	public FindNeededHelperFunctions(InputAndOutputElectionStructs allInputAndOutputTypes) {
		this.allInputAndOutputTypes = allInputAndOutputTypes;
	}

	public static HelperFunctionMap findNeededFunctions(String boolExpCode,
			InputAndOutputElectionStructs inAndOutStructs) {
		final FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(
				CharStreams.fromString(boolExpCode));
		final CommonTokenStream ts = new CommonTokenStream(l);
		final FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);

		ParseTreeWalker walker = new ParseTreeWalker();
		FindNeededHelperFunctions listener = new FindNeededHelperFunctions(inAndOutStructs);
		walker.walk(listener, p.booleanExpList());

		return listener.map;
	}

	private void addFuncAndDeps(HelperFunction func) {
		for (HelperFunction dep : func.dependencies()) {
			addFuncAndDeps(dep);
		}
		map.add(func);
	}

	@Override
	public void exitIntersectVotes(IntersectVotesContext ctx) {
		IntersectionHelperFunction func = new IntersectionHelperFunction(allInputAndOutputTypes.getInput());
		addFuncAndDeps(func);
	}

}
