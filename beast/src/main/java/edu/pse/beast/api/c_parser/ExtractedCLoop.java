package edu.pse.beast.api.c_parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.ExpressionContext;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.IterationStatementContext;
import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr.CParser.RelationalExpressionContext;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ExtractedCLoop {
    private static final String RESOURCES = "/edu/pse/beast/api/c_parser/";
    private static final String FILE_ENDING = ".template";
    private static final String FILE_KEY = "LOOP";

    private static final String BLANK = " ";
    private static final String PAREN_OPEN = "(";
    private static final String PAREN_CLOSE = ")";

    private static final String LOOP_TYPE = "LOOP_TYPE";
    private static final String LOOP_NUMBER = "LOOP_NUMBER";
    private static final String LOOP_BOUND = "LOOP_BOUND";
    private static final String MISSING_MANUAL_BOUND = "MISSING MANUAL BOUND";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

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

    public static final String getTemplate(final Class<?> c) {
        final String key = FILE_KEY;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public final List<ExtractedCLoop> getChildrenLoops() {
        return childrenLoops;
    }

    public final CLoopTypes getLoopType() {
        return loopType;
    }

    public final String getFunctionName() {
        return functionName;
    }

    public final int getLoopNumberInFunction() {
        return loopNumberInFunction;
    }

    public final int getLine() {
        return line;
    }

    public final int getPosInLine() {
        return posInLine;
    }

    public final CLoopParseResultType getLoopParseResult() {
        return loopParseResult;
    }

    public final LoopBoundType getParsedLoopBoundType() {
        return parsedLoopBoundType;
    }

    public final void setParsedLoopBoundType(final LoopBoundType loopBoundType) {
        this.parsedLoopBoundType = loopBoundType;
    }

    public final void setParentLoop(final ExtractedCLoop parentLoopHandler) {
        this.parentLoop = parentLoopHandler;
    }

    public final ExtractedCLoop getParentLoop() {
        return parentLoop;
    }

    public final Integer getManualInteger() {
        return manualInteger;
    }

    public final void setManualInteger(final int manualIntegerValue) {
        this.manualInteger = manualIntegerValue;
    }

    @Override
    public final String toString() {
        final Class<?> c = this.getClass();
        String template =
                getTemplate(c)
                .replaceAll(LOOP_TYPE, loopType.toString())
                .replaceAll(LOOP_NUMBER, String.valueOf(loopNumberInFunction))
                .replaceAll(LOOP_BOUND, parsedLoopBoundType.toString());
        if (parsedLoopBoundType == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
            if (manualInteger == null) {
                template = MISSING_MANUAL_BOUND + BLANK + "::" + BLANK + template;
            } else {
                template += PAREN_OPEN + manualInteger + PAREN_CLOSE;
            }
        }
        return template;
    }

    private void handleParseFail(final CLoopParseResultType res) {
        loopParseResult = res;
        parsedLoopBoundType = LoopBoundType.MANUALLY_ENTERED_INTEGER;
    }

    private static ExpressionContext initForLoopExpression(final IterationStatementContext ctx) {
        int firstSemiPos = 0;
        final String semi = ctx.Semi(0).getText();
        while (firstSemiPos < ctx.getChildCount()
                && !ctx.getChild(firstSemiPos).getText().equals(semi)) {
            ++firstSemiPos;
        }
        final ExpressionContext expCtx = (ExpressionContext) ctx.getChild(firstSemiPos + 1);
        return expCtx.getText().equals(semi) ? null : expCtx;
    }

    private static ParserRuleContext initParseRule(final ExpressionContext condExp) {
        ParserRuleContext parseRule = (ParserRuleContext) condExp;
        while (parseRule.getChildCount() > 0
                && parseRule.getClass() != RelationalExpressionContext.class) {
            parseRule = (ParserRuleContext) parseRule.getChild(0);
        }
        return parseRule;
    }

    private void initLoopContext(final CodeGenOptions codeGenOptions,
                                 final RelationalExpressionContext relCond) {
        final String votersVarName = codeGenOptions.getCurrentAmountVotersVarName();
        final String candsVarName = codeGenOptions.getCurrentAmountCandsVarName();
        final String seatsVarName = codeGenOptions.getCurrentAmountSeatsVarName();

        final String lessThanText = relCond.shiftExpression().getText();

        if (lessThanText.equals(votersVarName)) {
            loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
            parsedLoopBoundType = LoopBoundType.NECESSARY_LOOP_BOUND_AMT_VOTERS;
        } else if (lessThanText.equals(candsVarName)) {
            loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
            parsedLoopBoundType = LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS;
        } else if (lessThanText.equals(seatsVarName)) {
            loopParseResult = CLoopParseResultType.PROBABLE_SUCCESS_CONSTANT_LOOP;
            parsedLoopBoundType = LoopBoundType.NECESSARY_LOOP_BOUND_AMT_SEATS;
        }
    }

    private void init(final CodeGenOptions codeGenOptions) {
        line = context.start.getLine();
        posInLine = context.start.getCharPositionInLine();

        if (context.For() != null) {
            loopType = CLoopTypes.FOR;
            final ExpressionContext condExp = initForLoopExpression(context);

            final CLoopParseResultType parseFail;
            if (condExp == null) {
                parseFail = CLoopParseResultType.NO_CONDITIONAL_STATEMENT;
            } else {
                final ParserRuleContext parseRule = initParseRule(condExp);
                if (parseRule.getClass() != RelationalExpressionContext.class) {
                    parseFail = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_RIGHT_FORM;
                } else {
                    final RelationalExpressionContext relCond =
                            (RelationalExpressionContext) parseRule;
                    if (relCond.Less() == null) {
                        parseFail = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_LESS;
                    } else if (relCond.shiftExpression() == null) {
                        parseFail = CLoopParseResultType.CONDITIONAL_STATEMENT_NOT_RIGHT_FORM;
                    } else {
                        parseFail = null;
                        initLoopContext(codeGenOptions, relCond);
                    }
                }
                if (parseFail != null) {
                    handleParseFail(parseFail);
                }
            }
        } else if (context.While() != null) {
            loopType = CLoopTypes.WHILE;
        }
    }

    public final LoopBound generateLoopBound() {
        final List<LoopBound> childrenLoopBounds = new ArrayList<>();
        for (final ExtractedCLoop cl : childrenLoops) {
            childrenLoopBounds.add(cl.generateLoopBound());
        }
        return new LoopBound(childrenLoopBounds, functionName,
                             parsedLoopBoundType, loopNumberInFunction);
    }

    public final void addChild(final ExtractedCLoop l) {
        childrenLoops.add(l);
    }

    public final String getUuid() {
        return uuid;
    }

    public static final ExtractedCLoop fromStoredValues(final String uuid,
                                                        final Types types,
                                                        final int line,
                                                        final int posInLine,
                                                        final int numberInFunc,
                                                        final String functionName) {
        final ExtractedCLoop cLoop = new ExtractedCLoop();
        cLoop.uuid = uuid;
        cLoop.loopType = types.loop;
        cLoop.line = line;
        cLoop.posInLine = posInLine;
        cLoop.loopNumberInFunction = numberInFunc;
        cLoop.loopParseResult = types.parseResult;
        cLoop.parsedLoopBoundType = types.loopBound;
        cLoop.functionName = functionName;
        return cLoop;
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class Types {
        final CLoopTypes loop;
        final CLoopParseResultType parseResult;
        final LoopBoundType loopBound;

        public Types(final CLoopTypes loopType,
                     final CLoopParseResultType parseResultType,
                     final LoopBoundType loopBoundType) {
            this.loop = loopType;
            this.parseResult = parseResultType;
            this.loopBound = loopBoundType;
        }
    }
}
