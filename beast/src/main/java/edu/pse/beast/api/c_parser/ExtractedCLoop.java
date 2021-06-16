package edu.pse.beast.api.c_parser;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.ExpressionContext;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.IterationStatementContext;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.RelationalExpressionContext;

public class ExtractedCLoop {
	private IterationStatementContext ctx;
	private CLoopTypes loopType;
	private int line;
	private int loopNumberInFunction;
	private CLoopParseResultType loopParseResult;
	private LoopBoundType parsedLoopBoundType;
	private int posInLine;
	private String functionName;

	private ExtractedCLoop parentLoop;
	private List<ExtractedCLoop> childrenLoops = new ArrayList<>();

	public ExtractedCLoop(IterationStatementContext ctx,
			int loopNumberInFunction, CodeGenOptions codeGenOptions,
			String functionName) {
		this.ctx = ctx;
		this.functionName = functionName;
		this.loopNumberInFunction = loopNumberInFunction;
		init(codeGenOptions);
	}

	public int getLoopNumberInFunction() {
		return loopNumberInFunction;
	}

	public int getLine() {
		return line;
	}

	public int getPosInLine() {
		return posInLine;
	}

	public CLoopParseResultType getLoopParseResult() {
		return loopParseResult;
	}

	public LoopBoundType getParsedLoopBoundType() {
		return parsedLoopBoundType;
	}

	public void setParentLoop(ExtractedCLoop parentLoop) {
		this.parentLoop = parentLoop;
	}

	public ExtractedCLoop getParentLoop() {
		return parentLoop;
	}

	@Override
	public String toString() {
		return "ExtractedCLoop [loopType=" + loopType + ", line=" + line
				+ ", loopNumberInFunction=" + loopNumberInFunction
				+ ", loopParseResult=" + loopParseResult + ", parentLoop="
				+ parentLoop + ", parsedLoopBoundType=" + parsedLoopBoundType
				+ "]";
	}

	private void init(CodeGenOptions codeGenOptions) {
		line = ctx.start.getLine();
		posInLine = ctx.start.getCharPositionInLine();

		if (ctx.For() != null) {
			loopType = CLoopTypes.FOR;
			List<ExpressionContext> expressions = ctx.expression();

			ExpressionContext condExp = null;
			int firstSemiPos = 0;
			String semi = ctx.Semi(0).getText();
			for (; firstSemiPos < ctx.getChildCount()
					&& !ctx.getChild(firstSemiPos).getText()
							.equals(semi); ++firstSemiPos);

			if (!ctx.getChild(firstSemiPos + 1).getText().equals(semi)) {
				condExp = (ExpressionContext) ctx.getChild(firstSemiPos + 1);
			}

			if (condExp == null) {
				loopParseResult = CLoopParseResultType.NO_CONDITIONAL_STATEMENT;
				return;
			}

			ParserRuleContext parseRule = (ParserRuleContext) condExp;
			while (parseRule.getChildCount() > 0 && parseRule
					.getClass() != RelationalExpressionContext.class) {
				parseRule = (ParserRuleContext) parseRule.getChild(0);
			}

			if (parseRule.getClass() != RelationalExpressionContext.class) {
				loopParseResult = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_RIGHT_FORM;
				return;
			}

			RelationalExpressionContext relCond = (RelationalExpressionContext) parseRule;

			if (relCond.Less() == null) {
				loopParseResult = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_LESS;
				return;
			}

			if (relCond.shiftExpression() == null) {
				loopParseResult = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_RIGHT_FORM;
				return;
			}

			String lessThanText = relCond.shiftExpression().getText();

			if (lessThanText
					.equals(codeGenOptions.getCurrentAmountVotersVarName())) {
				loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
				parsedLoopBoundType = LoopBoundType.LOOP_BOUND_AMT_VOTERS;
			} else if (lessThanText
					.equals(codeGenOptions.getCurrentAmountCandsVarName())) {
				loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
				parsedLoopBoundType = LoopBoundType.LOOP_BOUND_AMT_CANDS;
			} else if (lessThanText
					.equals(codeGenOptions.getCurrentAmountSeatsVarName())) {
				loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
				parsedLoopBoundType = LoopBoundType.LOOP_BOUND_AMT_SEATS;
			}
		} else if (ctx.While() != null) {
			loopType = CLoopTypes.WHILE;
		}
	}

	public LoopBound generateLoopBound() {
		List<LoopBound> childrenLoopBounds = new ArrayList<>();
		for (ExtractedCLoop cl : childrenLoops) {
			childrenLoopBounds.add(cl.generateLoopBound());
		}

		LoopBound bound = new LoopBound(childrenLoopBounds,
				functionName, parsedLoopBoundType, loopNumberInFunction);
		
		return bound;
	}

	public void addChild(ExtractedCLoop l) {
		childrenLoops.add(l);
	}

}
