package edu.pse.beast.api.c_parser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.runtime.ParserRuleContext;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.ExpressionContext;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.IterationStatementContext;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.RelationalExpressionContext;

public class ExtractedCLoop {
    private IterationStatementContext context;

    private String uuid = UUID.randomUUID().toString();
    private CLoopTypes loopType;

    private int line;
    private int loopNumberInFunction;
    private int posInLine;

    private String functionName;
    private CLoopParseResultType loopParseResult;
    private LoopBoundType parsedLoopBoundType;
    private Integer manualInteger;

    private ExtractedCLoop parentLoop;
    private List<ExtractedCLoop> childrenLoops = new ArrayList<>();

    public ExtractedCLoop(final IterationStatementContext ctx,
                          final int loopNumberInFunctionNumber,
                          final CodeGenOptions codeGenOptions,
                          final String functionNameString) {
        this.context = ctx;
        this.functionName = functionNameString;
        this.loopNumberInFunction = loopNumberInFunctionNumber;
        init(codeGenOptions);
    }

    private ExtractedCLoop() {
    }

    public List<ExtractedCLoop> getChildrenLoops() {
        return childrenLoops;
    }

    public CLoopTypes getLoopType() {
        return loopType;
    }

    public String getFunctionName() {
        return functionName;
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

    public void setParsedLoopBoundType(final LoopBoundType loopBoundType) {
        this.parsedLoopBoundType = loopBoundType;
    }

    public void setParentLoop(final ExtractedCLoop parentLoopHandler) {
        this.parentLoop = parentLoopHandler;
    }

    public ExtractedCLoop getParentLoop() {
        return parentLoop;
    }

    public Integer getManualInteger() {
        return manualInteger;
    }

    public void setManualInteger(final int manualIntegerValue) {
        this.manualInteger = manualIntegerValue;
    }

    @Override
    public String toString() {
        String template = "LOOP_TYPE LOOP_NUMBER: LOOP_BOUND";
        template =
                template.replaceAll("LOOP_TYPE", loopType.toString())
                .replaceAll("LOOP_NUMBER", String.valueOf(loopNumberInFunction))
                .replaceAll("LOOP_BOUND", parsedLoopBoundType.toString());
        if (parsedLoopBoundType == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
            if (manualInteger == null) {
                template = "MISSING MANUAL BOUND :: " + template;
            } else {
                template += "(" + manualInteger + ")";
            }
        }
        return template;
    }

    private void handleParseFail(final CLoopParseResultType res) {
        loopParseResult = res;
        parsedLoopBoundType = LoopBoundType.MANUALLY_ENTERED_INTEGER;
    }

    private void init(final CodeGenOptions codeGenOptions) {
        line = context.start.getLine();
        posInLine = context.start.getCharPositionInLine();

        if (context.For() != null) {
            loopType = CLoopTypes.FOR;
            context.expression();

            ExpressionContext condExp = null;
            int firstSemiPos = 0;
            final String semi = context.Semi(0).getText();
            while (firstSemiPos < context.getChildCount()
                    && !context.getChild(firstSemiPos).getText().equals(semi)) {
                ++firstSemiPos;
            }

            if (!context.getChild(firstSemiPos + 1).getText().equals(semi)) {
                condExp = (ExpressionContext) context.getChild(firstSemiPos + 1);
            }

            final CLoopParseResultType parseFail;
            if (condExp == null) {
                parseFail = CLoopParseResultType.NO_CONDITIONAL_STATEMENT;
            } else {
                ParserRuleContext parseRule = (ParserRuleContext) condExp;
                while (parseRule.getChildCount() > 0
                        && parseRule.getClass() != RelationalExpressionContext.class) {
                    parseRule = (ParserRuleContext) parseRule.getChild(0);
                }
                if (parseRule.getClass() != RelationalExpressionContext.class) {
                    parseFail = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_RIGHT_FORM;
                } else if (((RelationalExpressionContext) parseRule).Less() == null) {
                    parseFail = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_LESS;
                } else if (((RelationalExpressionContext) parseRule).shiftExpression() == null) {
                    parseFail = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_RIGHT_FORM;
                } else {
                    parseFail = null;
                }
            }
            if (parseFail != null) {
                handleParseFail(parseFail);
                return;
            }

            final String lessThanText =
                    ((RelationalExpressionContext) ((ParserRuleContext) condExp))
                    .shiftExpression().getText();

            if (lessThanText
                    .equals(codeGenOptions.getCurrentAmountVotersVarName())) {
                loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
                parsedLoopBoundType = LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS;
            } else if (lessThanText
                    .equals(codeGenOptions.getCurrentAmountCandsVarName())) {
                loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
                parsedLoopBoundType = LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS;
            } else if (lessThanText
                    .equals(codeGenOptions.getCurrentAmountSeatsVarName())) {
                loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
                parsedLoopBoundType = LoopBoundType.NECESSARY_LOOP_BOUND_AMT_SEATS;
            }
        } else if (context.While() != null) {
            loopType = CLoopTypes.WHILE;
        }
    }

    public LoopBound generateLoopBound() {
        final List<LoopBound> childrenLoopBounds = new ArrayList<>();
        for (final ExtractedCLoop cl : childrenLoops) {
            childrenLoopBounds.add(cl.generateLoopBound());
        }
        return new LoopBound(childrenLoopBounds, functionName,
                             parsedLoopBoundType, loopNumberInFunction);
    }

    public void addChild(final ExtractedCLoop l) {
        childrenLoops.add(l);
    }

    public String getUuid() {
        return uuid;
    }

    public static ExtractedCLoop fromStoredValues(final String uuid,
                                                  final CLoopTypes loopType,
                                                  final int line,
                                                  final int posInLine,
                                                  final int numberInFunc,
                                                  final CLoopParseResultType parseResultType,
                                                  final LoopBoundType loopBoundType,
                                                  final String functionName) {
        final ExtractedCLoop cLoop = new ExtractedCLoop();
        cLoop.uuid = uuid;
        cLoop.loopType = loopType;
        cLoop.line = line;
        cLoop.posInLine = posInLine;
        cLoop.loopNumberInFunction = numberInFunc;
        cLoop.loopParseResult = parseResultType;
        cLoop.parsedLoopBoundType = loopBoundType;
        cLoop.functionName = functionName;
        return cLoop;
    }
}
