package edu.pse.beast.booleanexpeditor.booleanexpcodearea.errorfinder;

import org.antlr.v4.runtime.ParserRuleContext;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.TypeExpression;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ComparisonExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.ElectExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.PassTypeContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.SymbolicVarExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumExpContext;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.VoteSumUniqueExpContext;
import edu.pse.beast.types.InternalTypeContainer;

/**
 * A factory for creating BooleanExpError objects.
 *
 * @author Holger Klein
 */
public final class BooleanExpErrorFactory {
    /** The Constant ANTLR. */
    static final String ANTLR = "antlr";
    /** The Constant VAR_NOT_DECL. */
    static final String VAR_NOT_DECL = "var_not_decl";
    /** The Constant TOO_MANY_VARS_PASSED. */
    static final String TOO_MANY_VARS_PASSED =
            "too_many_vars_passed";
    /** The Constant WRONG_VAR_TYPE_PASSED. */
    static final String WRONG_VAR_TYPE_PASSED =
            "wrong_var_type_passed";
    /** The Constant INCOMPARABLE_TYPES. */
    static final String INCOMPARABLE_TYPES =
            "incomparable_types";
    /** The Constant INCOMPARABLE_LIST_SIZES. */
    static final String INCOMPARABLE_LIST_SIZES =
            "incomparable_list_sizes";
    /** The Constant WRONG_VAR_PASSED_TO_VOTESUM. */
    static final String WRONG_VAR_PASSED_TO_VOTESUM =
            "wrong_var_passed_to_votesum";
    /** The Constant WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE. */
    static final String WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE =
            "wrong_var_passed_to_votesum_unique";
    /** The Constant NUMBER_MUST_BE_GREATER_0. */
    static final String NUMBER_MUST_BE_GREATER_ZERO =
            "number_must_be_greater_0";

    /** The Constant VAR_NAME. */
    static final String VAR_NAME = "var_name";
    /** The Constant VAR_TYPE. */
    static final String VAR_TYPE = "var_type";

    /** The Constant MSG. */
    static final String MSG = "msg";

    /** The Constant PASSED_TYPE. */
    static final String PASSED_TYPE = "passed_type";
    /** The Constant EXPECTED_TYPE. */
    static final String EXPECTED_TYPE = "expected_type";

    /** The Constant LHS_LIST_SIZE. */
    static final String LHS_LIST_SIZE = "lhs_list_size";
    /** The Constant RHS_LIST_SIZE. */
    static final String RHS_LIST_SIZE = "rhs_list_size";

    /** The Constant LHS_TYPE. */
    static final String LHS_TYPE = "lhs_type";
    /** The Constant RHS_TYPE. */
    static final String RHS_TYPE = "rhs_type";

    /** The Constant ERROR_IDS. */
    private static final String[] ERROR_IDS =
        {
        ANTLR, VAR_NOT_DECL,
        TOO_MANY_VARS_PASSED, WRONG_VAR_TYPE_PASSED,
        INCOMPARABLE_TYPES, INCOMPARABLE_LIST_SIZES,
        WRONG_VAR_PASSED_TO_VOTESUM,
        WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE,
        NUMBER_MUST_BE_GREATER_ZERO
    };

    /**
     * Instantiates a new boolean exp error factory.
     */
    private BooleanExpErrorFactory() { }

    /**
     * Gets the error num.
     *
     * @param id
     *            the id
     * @return the error num
     */
    private static int getErrorNum(final String id) {
        for (int i = 0; i < ERROR_IDS.length; ++i) {
            if (ERROR_IDS[i].equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Creates an error when a variable was not created.
     *
     * @param ctx
     *            the context* of the symbolic variable expression
     * @return the error object that describes the problem
     */
    public static CodeError createVarNotDeclaredErr(final SymbolicVarExpContext ctx) {
        CodeError err = generateStandardError(ctx, VAR_NOT_DECL);
        err.setExtraInfo(VAR_NAME, ctx.Identifier().getText());
        return err;
    }

    /**
     * Creates an antlr error.
     *
     * @param line
     *            the line the error is in
     * @param charInline
     *            the amount of characters in the line
     * @param msg
     *            the message to be displayed
     * @return the error that describes contains the information given to this
     *         function
     */
    public static CodeError createAntlrError(final int line,
                                             final int charInline,
                                             final String msg) {
        CodeError err = new CodeError(line, charInline, ANTLR,
                                      getErrorNum(ANTLR), 0, 0);
        err.setExtraInfo(MSG, msg);
        return err;
    }

    /**
     * Creates an error when too many variables are passed.
     *
     * @param ctx
     *            the context of the symbolic variable expression
     * @return the error object that describes the problem
     */
    public static CodeError createTooManyVarsPassedError(final PassTypeContext ctx) {
        CodeError err = generateStandardError(ctx, TOO_MANY_VARS_PASSED);
        err.setExtraInfo(VAR_NAME, ctx.getText());
        return err;
    }

    /**
     * Creates an error when a wrong variable was passed.
     *
     * @param cont
     *            the internal type
     * @param ctx
     *            the context of the symbolic variable expression
     * @param currentVarExp
     *            the current variable expression
     * @return the code error that describes the problem
     */
    static CodeError createWrongVarTypePassed(final InternalTypeContainer cont,
                                              final FormalPropertyDescriptionParser
                                                          .PassTypeContext ctx,
                                              final TypeExpression currentVarExp) {
        CodeError err = generateStandardError(ctx, WRONG_VAR_TYPE_PASSED);
        err.setExtraInfo(VAR_NAME, ctx.getText());
        err.setExtraInfo(PASSED_TYPE,
                         currentVarExp.getInternalTypeContainer().getInternalType().toString());
        err.setExtraInfo(EXPECTED_TYPE, cont.getAccessTypeIfList().toString());
        return err;
    }

    /**
     * Creates an error when two not comparable things are being compared.
     *
     * @param ctx
     *            the context of the symbolic variable expression
     * @param lhsCont
     *            the left hand side content
     * @param rhsCont
     *            the right hand side content
     * @return the error that describes the problem
     */
    static CodeError createCantCompareDifferentListLevels(final ComparisonExpContext ctx,
                                                          final InternalTypeContainer lhsCont,
                                                          final InternalTypeContainer rhsCont) {
        CodeError err = generateStandardError(ctx, INCOMPARABLE_LIST_SIZES);
        err.setExtraInfo(LHS_LIST_SIZE, String.valueOf(lhsCont.getListLvl()));
        err.setExtraInfo(RHS_LIST_SIZE, String.valueOf(rhsCont.getListLvl()));
        return err;
    }

    /**
     * Creates an error when two incomparable things get compared.
     *
     * @param ctx
     *            the comparison context
     * @param lhsCont
     *            the left hand side content
     * @param rhsCont
     *            the right hand side content
     * @return the code error
     */
    static CodeError createCantCompareTypes(final ComparisonExpContext ctx,
                                            final InternalTypeContainer lhsCont,
                                            final InternalTypeContainer rhsCont) {
        CodeError err = generateStandardError(ctx, INCOMPARABLE_TYPES);
        err.setExtraInfo(LHS_TYPE, lhsCont.getInternalType().toString());
        err.setExtraInfo(RHS_TYPE, rhsCont.getInternalType().toString());
        return err;
    }

    /**
     * Create code error for a wrong variable to votesum.
     *
     * @param ctx
     *            the sum expression context
     * @param passedType
     *            the passed type
     * @return the code error
     */
    static CodeError createWrongVarToVotesumError(final VoteSumExpContext ctx,
                                                  final InternalTypeContainer passedType) {
        CodeError err = generateStandardError(ctx, WRONG_VAR_PASSED_TO_VOTESUM);
        err.setExtraInfo(VAR_TYPE, passedType.getInternalType().toString());
        return err;
    }

    /**
     * Create code error for a wrong variable to votesum.
     *
     * @param ctx
     *            the sum expression context
     * @param passedType
     *            the passed type
     * @return the code error
     */
    static CodeError createWrongVarToVotesumError(final VoteSumUniqueExpContext ctx,
                                                  final InternalTypeContainer passedType) {
        CodeError err = generateStandardError(ctx, WRONG_VAR_PASSED_TO_VOTESUM_UNIQUE);
        err.setExtraInfo(VAR_TYPE, passedType.getInternalType().toString());
        return err;
    }

    /**
     * Create the error that indicates that a number must be greater than zero
     * for a votesum.
     *
     * @param ctx
     *            the vote sum expression context
     * @return the code error
     */
    static CodeError createNumberMustBeGreaterZeroVotesum(final ParserRuleContext ctx) {
        CodeError err = generateStandardError(ctx, NUMBER_MUST_BE_GREATER_ZERO);
        return err;
    }

    /**
     * Creates an error if an "elect" value is smaller than zero.
     *
     * @param ctx
     *            the election expression context
     * @return the code error
     */
    static CodeError createNumberMustBeGreaterZeroElect(final ElectExpContext ctx) {
        CodeError err = generateStandardError(ctx, NUMBER_MUST_BE_GREATER_ZERO);
        return err;
    }

    /**
     * Creates an error if an "votes" value is smaller than zero.
     *
     * @param ctx
     *            the vote expression context
     * @return the code error
     */
    static CodeError createNumberMustBeGreaterZeroVotes(final VoteExpContext ctx) {
        CodeError err = generateStandardError(ctx, NUMBER_MUST_BE_GREATER_ZERO);
        return err;
    }

    /**
     * Generate standard error.
     *
     * @param ctx
     *            the ctx
     * @param id
     *            the id
     * @return the code error
     */
    private static CodeError generateStandardError(final ParserRuleContext ctx,
                                                   final String id) {
        int pos = ctx.getStart().getStartIndex();
        int endPos = ctx.getStop().getStopIndex();
        int line = ctx.getStart().getLine();
        int charInLine = ctx.getStart().getCharPositionInLine();
        CodeError err = new CodeError(line, charInLine, id,
                                      getErrorNum(id), pos, endPos);
        return err;
    }
}
