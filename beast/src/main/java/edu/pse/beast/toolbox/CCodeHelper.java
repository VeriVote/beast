package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.celectiondescriptioneditor.electiontemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * This class contains functionality to generate C Code from internal data
 * structures.
 *
 * @author Holger Klein
 */
public final class CCodeHelper {
    /** The Constant AUTO. */
    public static final String AUTO = "auto";
    /** The Constant BREAK. */
    public static final String BREAK = "break";
    /** The Constant CASE. */
    public static final String CASE = "case";
    /** The Constant CHAR. */
    public static final String CHAR = "char";
    /** The Constant CONST. */
    public static final String CONST = "const";
    /** The Constant CONTINUE. */
    public static final String CONTINUE = "continue";
    /** The Constant DEFAULT. */
    public static final String DEFAULT = "default";
    /** The Constant DEFINE. */
    public static final String DEFINE = "#define";
    /** The Constant DO. */
    public static final String DO = "do";
    /** The Constant DOUBLE. */
    public static final String DOUBLE = "double";
    /** The Constant ELSE. */
    public static final String ELSE = "else";
    /** The Constant ENUM. */
    public static final String ENUM = "enum";
    /** The Constant EXTERN. */
    public static final String EXTERN = "extern";
    /** The Constant FLOAT. */
    public static final String FLOAT = "float";
    /** The Constant FOR. */
    public static final String FOR = "for";
    /** The Constant GOTO. */
    public static final String GOTO = "goto";
    /** The Constant IF. */
    public static final String IF = "if";
    /** The Constant INCLUDE. */
    public static final String INCLUDE = "#include";
    /** The Constant INLINE. */
    public static final String INLINE = "inline";
    /** The Constant INT. */
    public static final String INT = "int";
    /** The Constant LONG. */
    public static final String LONG = "long";
    /** The Constant REGISTER. */
    public static final String REGISTER = "register";
    /** The Constant RESTRICT. */
    public static final String RESTRICT = "restrict";
    /** The Constant RETURN. */
    public static final String RETURN = "return";
    /** The Constant SHORT. */
    public static final String SHORT = "short";
    /** The Constant SIGNED. */
    public static final String SIGNED = "signed";
    /** The Constant SIZE_OF. */
    public static final String SIZE_OF = "sizeof";
    /** The Constant STATIC. */
    public static final String STATIC = "static";
    /** The Constant STRUCT. */
    public static final String STRUCT = "struct";
    /** The Constant SWITCH. */
    public static final String SWITCH = "switch";
    /** The Constant TYPE_OF. */
    public static final String TYPE_DEF = "typedef";
    /** The Constant UNION. */
    public static final String UNION = "union";
    /** The Constant UNSIGNED. */
    public static final String UNSIGNED = "unsigned";
    /** The Constant VOID. */
    public static final String VOID = "void";
    /** The Constant VOLATILE. */
    public static final String VOLATILE = "volatile";
    /** The Constant WHILE. */
    public static final String WHILE = "while";
    /** The Constant ALIGN_AS. */
    public static final String ALIGN_AS = "_Alignas";
    /** The Constant ALIGN_OF. */
    public static final String ALIGN_OF = "_Alignof";
    /** The Constant ATOMIC. */
    public static final String ATOMIC = "_Atomic";
    /** The Constant BOOL. */
    public static final String BOOL = "_Bool";
    /** The Constant COMPLEX. */
    public static final String COMPLEX = "_Complex";
    /** The Constant GENERIC. */
    public static final String GENERIC = "_Generic";
    /** The Constant IMAGINARY. */
    public static final String IMAGINARY = "_Imaginary";
    /** The Constant NO_RETURN. */
    public static final String NO_RETURN = "_Noreturn";
    /** The Constant STATIC_ASSERT. */
    public static final String STATIC_ASSERT = "_Static_assert";
    /** The Constant THREAD_LOCAL. */
    public static final String THREAD_LOCAL = "_Thread_local";

    /** The Constant OPENING_PARENTHESES. */
    public static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    public static final String CLOSING_PARENTHESES = ")";
    /** The Constant OPENING_BRACES. */
    public static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    public static final String CLOSING_BRACES = "}";
    /** The Constant SEMICOLON. */
    public static final String SEMICOLON = ";";

    /** The Constant LINE_BREAK. */
    private static final String LINE_BREAK = "\n";
    /** The Constant EQUALS_SIGN. */
    private static final String EQUALS_SIGN = "=";
    /** The Constant LT_SIGN. */
    private static final String LT_SIGN = "<";
    /** The Constant LEQ_SIGN. */
    private static final String LEQ_SIGN = LT_SIGN + EQUALS_SIGN;
    /** The Constant GT_SIGN. */
    private static final String GT_SIGN = ">";
    /** The Constant PLUS. */
    private static final String PLUS = "+";

    /** The BLANK symbol. */
    private static final String BLANK = " ";
    /** The comma symbol. */
    private static final String COMMA = ",";
    /** The colon symbol. */
    private static final String COLON = ":";
    /** The Constant DOT. */
    private static final String DOT = ".";
    /** The Constant DOT. */
    private static final String STAR = "*";

    /** The Constant AMPERSAND. */
    private static final String AMPERSAND = "&";
    /** The Constant PIPE. */
    private static final String PIPE = "|";
    /** The Constant MINUS. */
    private static final String MINUS = "-";
    /** The Constant NOT. */
    private static final String NOT = "!";
    /** The Constant DIV. */
    private static final String DIV = "/";
    /** The Constant PLUS_PLUS. */
    private static final String PLUS_PLUS = PLUS + PLUS;

    /** The Constant OPENING_BRACKETS. */
    private static final String OPENING_BRACKETS = "[";
    /** The Constant CLOSING_BRACKETS. */
    private static final String CLOSING_BRACKETS = "]";

    /** The Constant UNDERSCORE. */
    private static final String UNDERSCORE = "_";
    /** The Constant DOT_ARR. */
    private static final String ARR = "arr";
    /** The Constant DOT_ARR. */
    private static final String DOT_ARR = DOT + ARR;

    /** The Constant ifndef. */
    private static final String IFNDEF = "#ifndef";
    /** The Constant endif. */
    private static final String ENDIF = "#endif";

    /** The Constant I. */
    private static final String I = "i";
    /** The Constant J. */
    private static final String J = "j";
    /** The Constant X. */
    private static final String X = "x";
    /** The Constant Y. */
    private static final String Y = "y";

    /** The Constant ZERO. */
    private static final String ZERO = "0";
    /** The Constant ONE. */
    private static final String ONE = "1";
    /** The Constant TWO. */
    private static final String TWO = "2";

    /** The line comment symbol(s). */
    private static final String LINE_COMMENT = "//";
    /** The right parenthesis and left brace symbol. */
    private static final String PAREN_R_BRACE_L =
            CLOSING_PARENTHESES + BLANK + OPENING_BRACES;
    /** The uint loop start string. */
    private static final String UINT_LOOP_START =
            OPENING_PARENTHESES + UNSIGNED + BLANK + INT + BLANK;

    /** The RESULT constant. */
    private static final String RESULT = "RESULT";
    /** The VOTES constant. */
    private static final String VOTES = "VOTES";
    /** The "amountVotes" constant. */
    private static final String AMOUNT_VOTES = "amountVotes";
    /** The "_exp" string. */
    private static final String EXP = "_exp";
    private static final String TOTAL_DIFF = "total_diff";
    private static final String POS_DIFF = "pos_diff";
    private static final String ASSUME = "assume";
    private static final String MARGIN = "MARGIN";
    private static final String NEWLINE = "\n";

    // String that only allows string in valid C format (they can still contain
    // identifiers)

    /** The character regex. */
    private static String characterRegex = "[_a-zA-Z][_a-zA-Z0-9]{0,30}";

    /** The reserved words. */
    private static String[] reservedWords =
        {
        AUTO, BREAK, CASE, CHAR,
        CONST, CONTINUE, DEFAULT,
        DO, DOUBLE, ELSE, ENUM,
        EXTERN, FLOAT, FOR, GOTO,
        IF, INLINE, INT, LONG,
        REGISTER, RESTRICT, RETURN,
        SHORT, SIGNED, SIZE_OF,
        STATIC, STRUCT, SWITCH,
        TYPE_DEF, UNION, UNSIGNED,
        VOID, VOLATILE, WHILE,
        ALIGN_AS, ALIGN_OF, ATOMIC,
        BOOL, COMPLEX, GENERIC,
        IMAGINARY, NO_RETURN,
        STATIC_ASSERT, THREAD_LOCAL
        };

    /** The c reserved words. */
    private static List<String> cReservedWords =
            new ArrayList<String>(Arrays.asList(reservedWords));

    /**
     * Instantiates a new c code helper.
     */
    private CCodeHelper() { }

    /**
     * Determines whether the string is neither null nor empty.
     *
     * @param string
     *            the string
     * @return true, if the string is not null and not empty
     */
    public static boolean notNullOrEmpty(final String string) {
        return string != null && !"".equals(string);
    }

    /**
     * Determines whether there is at least one string in the list
     * and this string is neither null nor empty.
     *
     * @param strings
     *            the strings in a list
     * @return true, if the string is not null and not empty
     */
    private static boolean notNullOrEmpty(final List<String> strings) {
        return strings != null && !strings.isEmpty()
                && strings.get(0) != null && !"".equals(strings.get(0));
    }

    /**
     * Generate negated expression.
     *
     * @param expr
     *            the expr
     * @return the string
     */
    public static String not(final String expr) {
        return NOT + expr;
    }

    /**
     * Generate underscored expression.
     *
     * @param expr
     *            the expr
     * @return the string
     */
    public static String underscore(final String expr) {
        return UNDERSCORE + expr;
    }

    /**
     * Generate colon expression.
     *
     * @param lhs
     *            the lhs expr
     * @param rhs
     *            the rhs expr
     * @return the string
     */
    public static String colon(final String lhs,
                               final String rhs) {
        return lhs + COLON + BLANK + rhs;
    }

    //COLON

    /**
     * Produces the given number of spaces.
     *
     * @param numberOfSpaces
     *            the number of spaces
     *
     * @return the spaces
     */
    public static String spaces(final int numberOfSpaces) {
        String spaces = "";
        if (0 <= numberOfSpaces) {
            for (int remSpaces = numberOfSpaces; 0 < remSpaces; remSpaces--) {
                spaces += BLANK;
            }
        }
        return spaces;
    }

    /**
     * Produces a space.
     *
     * @return a space
     */
    public static String space() {
        return BLANK;
    }

    /**
     * Generate line comment.
     *
     * @param text
     *            the text
     * @return the string
     */
    public static String lineComment(final String text) {
        return LINE_COMMENT + BLANK + text;
    }

    /**
     * Generate code for pointer to reference.
     *
     * @param reference the reference
     * @return the string
     */
    public static String pointer(final String reference) {
        return STAR + reference;
    }

    /**
     * Generate increment expression.
     *
     * @param expr
     *            the expr
     * @return the string
     */
    public static String plusPlus(final String expr) {
        return expr + PLUS_PLUS;
    }

    /**
     * Generate lhs expression plus rhs expression.
     *
     * @param lhsExp
     *            the lhsExp
     * @param rhsExp
     *            the rhsExp
     * @return the string
     */
    public static String plus(final String lhsExp,
                              final String rhsExp) {
        return lhsExp + BLANK + PLUS + BLANK + rhsExp;
    }

    /**
     * Generate lhs expression increment by rhs expression.
     *
     * @param lhsExp
     *            the lhsExp
     * @param rhsExp
     *            the rhsExp
     * @return the string
     */
    public static String plusEquals(final String lhsExp,
                                    final String rhsExp) {
        return lhsExp + BLANK + PLUS + EQUALS_SIGN + BLANK + rhsExp;
    }

    /**
     * Add comma and space.
     *
     * @param exp
     *            the exp
     * @return the string
     */
    public static String comma(final String exp) {
        return exp + COMMA + BLANK;
    }

    /**
     * Add comma and space.
     *
     * @param exp
     *            the exp
     * @return the string
     */
    public static String comma(final int exp) {
        return comma(Integer.toString(exp));
    }

    /**
     * Add linebreak.
     *
     * @param exp
     *            the exp
     * @return the string
     */
    public static String lineBreak(final String exp) {
        return exp + LINE_BREAK;
    }

    /**
     * Generate linebreak.
     *
     * @return the string
     */
    public static String lineBreak() {
        return lineBreak("");
    }

    /**
     * Generate common counter variable "i".
     *
     * @return the string
     */
    public static String i() {
        return I;
    }

    /**
     * Generate common counter variable "j".
     *
     * @return the string
     */
    public static String j() {
        return J;
    }

    /**
     * Generate common counter variable "x".
     *
     * @return the string
     */
    public static String x() {
        return X;
    }

    /**
     * Generate common counter variable "y".
     *
     * @return the string
     */
    public static String y() {
        return Y;
    }

    /**
     * Generate code for zero.
     *
     * @return the string
     */
    public static String zero() {
        return ZERO;
    }

    /**
     * Generate code for one.
     *
     * @return the string
     */
    public static String one() {
        return ONE;
    }

    /**
     * Generate code for two.
     *
     * @return the string
     */
    public static String two() {
        return TWO;
    }

    /**
     * Generate "arr" code.
     *
     * @return the string
     */
    public static String arr() {
        return ARR;
    }

    /**
     * Generate ".arr" code.
     *
     * @return the string
     */
    public static String dotArr() {
        return DOT_ARR;
    }

    /**
     * Generate code for long variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String longVar(final String varName) {
        return LONG + BLANK + varName;
    }

    /**
     * Generate code for (signed) int variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String intVar(final String varName) {
        return INT + BLANK + varName;
    }

    /**
     * Generate code for unsigned int variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String unsignedIntVar(final String varName) {
        return UNSIGNED + BLANK + intVar(varName);
    }

    /**
     * Generate code for (signed) char variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String charVar(final String varName) {
        return CHAR + BLANK + varName;
    }

    /**
     * Generate code for unsigned char variable.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String unsignedCharVar(final String varName) {
        return UNSIGNED + BLANK + charVar(varName);
    }

    /**
     * Long variable equals code.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String longVarEqualsCode(final String varName) {
        return longVar(varName) + BLANK + EQUALS_SIGN + BLANK;
    }

    /**
     * (Signed) int variable equals code.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String intVarEqualsCode(final String varName) {
        return intVar(varName) + BLANK + EQUALS_SIGN + BLANK;
    }

    /**
     * Unsigned int variable equals code.
     *
     * @param varName
     *            the var name
     * @return the string
     */
    public static String uintVarEqualsCode(final String varName) {
        return unsignedIntVar(varName) + BLANK + EQUALS_SIGN + BLANK;
    }

    /**
     * Variable assignment code.
     *
     * @param varName
     *            the var name
     * @param rhsVar
     *            the rhs var
     * @return the string
     */
    public static String varAssignCode(final String varName,
                                        final String rhsVar) {
        return varName + BLANK + EQUALS_SIGN + BLANK + rhsVar;
    }

    /**
     * Constant assignment code.
     *
     * @param constName
     *            the constant's name
     * @param constValue
     *            the constant's value
     * @return the string
     */
    public static String constAssignCode(final String constName,
                                         final int constValue) {
        return constName + EQUALS_SIGN + Integer.toString(constValue);
    }

    /**
     * Variable subtract code.
     *
     * @param varPos
     *            the var pos
     * @param varNeg
     *            the var neg
     * @return the string
     */
    public static String varSubtractCode(final String varPos,
                                          final String varNeg) {
        return varPos + BLANK + MINUS + BLANK + varNeg;
    }

    /**
     * Variable add code.
     *
     * @param varOne
     *            the first variable
     * @param varTwo
     *            the second variable
     * @return the string
     */
    public static String varAddCode(final String varOne,
                                    final String varTwo) {
        return varOne + BLANK + MINUS + BLANK + varTwo;
    }

    /**
     * Variable subtract code.
     *
     * @param varPos
     *            the var pos
     * @param varNeg
     *            the var neg
     * @return the string
     */
    public static String varDivideCode(final String varPos,
                                       final String varNeg) {
        return varPos + BLANK + DIV + BLANK + varNeg;
    }

    /**
     * Variable subtract code.
     *
     * @param varPos
     *            the var pos
     * @param varNeg
     *            the var neg
     * @return the string
     */
    public static String varMultiplyCode(final String varPos,
                                         final String varNeg) {
        return varPos + BLANK + STAR + BLANK + varNeg;
    }

    /**
     * Variable less code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String lt(final String lhsExpr,
                            final String rhsExpr) {
        return lhsExpr + BLANK + LT_SIGN + BLANK + rhsExpr;
    }

    /**
     * Variable less code.
     *
     * @return the string
     */
    public static String lt() {
        return LT_SIGN;
    }

    /**
     * Variable less-equal code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String leq(final String lhsExpr,
                             final String rhsExpr) {
        return lhsExpr + BLANK + LT_SIGN + EQUALS_SIGN + BLANK + rhsExpr;
    }

    /**
     * Variable less-equal code.
     *
     * @return the string
     */
    public static String leq() {
        return LEQ_SIGN;
    }

    /**
     * Variable equal code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String eq(final String lhsExpr,
                            final String rhsExpr) {
        return lhsExpr + BLANK + EQUALS_SIGN + EQUALS_SIGN + BLANK + rhsExpr;
    }

    /**
     * Variable not-equal code.
     *
     * @param lhsExpr
     *            the lhs expression
     * @param rhsExpr
     *            the rhs expression
     * @return the string
     */
    public static String neq(final String lhsExpr,
                             final String rhsExpr) {
        return lhsExpr + BLANK + not(EQUALS_SIGN) + BLANK + rhsExpr;
    }

    /**
     * Parenthesize the expression.
     *
     * @param expression
     *            the expression
     * @return the string
     */
    public static String parenthesize(final String expression) {
        return OPENING_PARENTHESES + expression + CLOSING_PARENTHESES;
    }

    /**
     * Generate function code.
     *
     * @param function
     *            the function
     * @param params
     *            the parameters
     * @return the string
     */
    public static String functionCode(final String function,
                                      final List<String> params) {
        String functionCode = "";
        if (notNullOrEmpty(function) && notNullOrEmpty(params)) {
            for (final String param : params) {
                if (notNullOrEmpty(param)
                        && !"".equals(functionCode)) {
                    functionCode = comma(functionCode);
                }
                functionCode += notNullOrEmpty(param) ? param : "";
            }
        }
        return notNullOrEmpty(function)
                ? function + parenthesize(functionCode)
                : function;
    }

    /**
     * Generate function code.
     *
     * @param function
     *            the function
     * @param params
     *            the parameters
     * @return the string
     */
    public static String functionCode(final String function,
                                       final String... params) {
        final List<String> paramList = new ArrayList<String>();
        for (final String param : params) {
            if (notNullOrEmpty(param)) {
                paramList.add(param);
            }
        }
        return functionCode(function, paramList);
    }

    /**
     * Dis- or conjunct if not null.
     *
     * @param toBeAppended
     *            the term to be appended
     * @param originalTerm
     *            the term which should be complemented
     * @param junctor
     *            the junctor symbol(s)
     * @return the string
     */
    private static String disOrConjunct(final String toBeAppended,
                                        final String originalTerm,
                                        final String junctor) {
        final String junction = BLANK + junctor + BLANK;
        final String disOrConjuncted = notNullOrEmpty(toBeAppended)
                                ? toBeAppended
                                        + (notNullOrEmpty(originalTerm)
                                                ? junction : "")
                                        : "";
        return disOrConjuncted + originalTerm;
    }

    /**
     * Conjunct if not null.
     *
     * @param toBeConjuncted
     *            the term to be conjuncted
     * @param originalTerm
     *            the term which should be complemented
     * @return the string
     */
    public static String conjunct(final String toBeConjuncted,
                                  final String originalTerm) {
        final String conjunctor = AMPERSAND + AMPERSAND;
        return disOrConjunct(toBeConjuncted, originalTerm, conjunctor);
    }

    /**
     * Disjunct if not null.
     *
     * @param toBeDisjuncted
     *            the term to be disjuncted
     * @param originalTerm
     *            the term which should be complemented
     * @return the string
     */
    public static String disjunct(final String toBeDisjuncted,
                                  final String originalTerm) {
        final String disjunctor = PIPE + PIPE;
        return disOrConjunct(toBeDisjuncted, originalTerm, disjunctor);
    }

    /**
     * Generate code for definition.
     *
     * @param name
     *            the definition name
     * @param argument
     *            the argument
     * @return the string
     */
    public static String define(final String name,
                                final String argument) {
        return DEFINE + BLANK + name + BLANK + argument;
    }

    /**
     * Generate code for definition.
     *
     * @param name
     *            the definition name
     * @param argument
     *            the argument
     * @return the string
     */
    public static String define(final String name,
                                final int argument) {
        return define(name, Integer.toString(argument));
    }

    /**
     * Generate code for definition if not defined.
     *
     * @param name
     *            the definition name
     * @param argument
     *            the argument
     * @return the string
     */
    public static String defineIfNonDef(final String name,
                                        final String argument) {
        return lineBreak(IFNDEF + BLANK + name)
                + lineBreak(BLANK + define(name, argument))
                + ENDIF;
    }

    /**
     * Generate code for definition if not defined.
     *
     * @param name
     *            the definition name
     * @param argument
     *            the argument
     * @return the string
     */
    public static String defineIfNonDef(final String name,
                                        final int argument) {
        return defineIfNonDef(name, Integer.toString(argument));
    }

    /**
     * Generate package-include statement.
     *
     * @param pkgName the pkg name
     * @return the string
     */
    public static String include(final String pkgName) {
        return INCLUDE + BLANK + LT_SIGN
                + pkgName + FileLoader.H_FILE_ENDING
                + GT_SIGN;
    }

    /**
     * Generate the loop header code of a for-loop given the name string of
     * the count variable, the string of the comparison symbol, and the name
     * string of the bound constant.
     *
     * @param countVar
     *            the name string of the counting variable
     * @param comparison
     *            the string of the comparison symbol
     * @param boundConst
     *            the name string of the loop bound constant
     * @param guardReinforce
     *            the guard reinforce
     * @return the code string of the for-loop header
     */
    public static String forLoopHeaderCode(final String countVar,
                                           final String comparison,
                                           final String boundConst,
                                           final String guardReinforce) {
        final String varEqualsZero =
                uintVarEqualsCode(countVar) + zero() + SEMICOLON;
        final String boundGuard =
                countVar + BLANK + comparison + BLANK + boundConst;
        final String loopGuard = conjunct(guardReinforce, boundGuard)
                                + SEMICOLON;
        final String incrCounter = plusPlus(countVar);
        return FOR + BLANK
                + parenthesize(varEqualsZero + BLANK
                                + loopGuard + BLANK
                                + incrCounter)
                + BLANK + OPENING_BRACES;
    }

    /**
     * Generate the loop header code of a for-loop given the name string of
     * the count variable, the string of the comparison symbol, and the name
     * string of the bound constant.
     *
     * @param countVar
     *            the name string of the counting variable
     * @param comparison
     *            the string of the comparison symbol
     * @param boundConst
     *            the name string of the loop bound constant
     * @return the code string of the for-loop header
     */
    public static String forLoopHeaderCode(final String countVar,
                                           final String comparison,
                                           final String boundConst) {
        return forLoopHeaderCode(countVar, comparison, boundConst, null);
    }

    /**
     * Array access.
     *
     * @param dim
     *            the dim
     * @return the string
     */
    public static String arrAcc(final String dim) {
        return OPENING_BRACKETS + dim + CLOSING_BRACKETS;
    }

    /**
     * Array access.
     *
     * @param dim
     *            the dim
     * @return the string
     */
    public static String arrAcc(final int dim) {
        return arrAcc(Integer.toString(dim));
    }

    /**
     * Array access for all given dimensions.
     *
     * @param arr
     *            the array
     * @param dims
     *            the dims
     * @return the string
     */
    public static String arrAccess(final String arr,
                                   final List<String> dims) {
        String arrAccess = notNullOrEmpty(arr) ? arr : "";
        if (notNullOrEmpty(arr) && notNullOrEmpty(dims)) {
            for (final String dim : dims) {
                arrAccess += notNullOrEmpty(dim) ? arrAcc(dim) : "";
            }
        }
        return arrAccess;
    }

    /**
     * Array access for all given dimensions.
     *
     * @param arr
     *            the array
     * @param dims
     *            the dims
     * @return the string
     */
    public static String arrAccess(final String arr,
                                   final String... dims) {
        final List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return arrAccess(arr, dimList);
    }

    /**
     * Array access for all given dimensions.
     *
     * @param arr
     *            the array
     * @param dims
     *            the dims
     * @return the string
     */
    public static String arrAccess(final String arr,
                                   final int... dims) {
        final List<String> dimList = new ArrayList<String>();
        for (final int dim : dims) {
            dimList.add(Integer.toString(dim));
        }
        return arrAccess(arr, dimList);
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param structName
     *            the struct name
     * @param dims
     *            the dims
     * @return the string
     */
    public static String dotStructAccess(final String owner,
                                         final String structName,
                                         final List<String> dims) {
        String arrAccess = notNullOrEmpty(owner) ? owner : "";
        if (notNullOrEmpty(owner)) {
            arrAccess += notNullOrEmpty(structName)
                    ? (DOT + structName) : dotArr();
            if (notNullOrEmpty(dims)) {
                for (final String dim : dims) {
                    arrAccess += notNullOrEmpty(dim) ? arrAcc(dim) : "";
                }
            }
        }
        return arrAccess;
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param structName
     *            the struct name
     * @param dims
     *            the dims
     * @return the string
     */
    public static String dotStructAccess(final String owner,
                                         final String structName,
                                         final String... dims) {
        final List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return dotStructAccess(owner, structName, dimList);
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param dimList
     *            the dims
     * @return the string
     */
    public static String dotArrStructAccess(final String owner,
                                            final List<String> dimList) {
        return dotStructAccess(owner, null, dimList);
    }

    /**
     * Dot arr struct access for all given dimensions.
     *
     * @param owner
     *            the owner
     * @param dims
     *            the dims
     * @return the string
     */
    public static String dotArrStructAccess(final String owner,
                                             final String... dims) {
        final List<String> dimList = new ArrayList<String>();
        for (final String dim : dims) {
            if (notNullOrEmpty(dim)) {
                dimList.add(dim);
            }
        }
        return dotStructAccess(owner, null, dimList);
    }

    /**
     * Returns the C constant which is the max amount of elements in a given
     * list container.
     *
     * @param cont
     *            the type container representing the list
     * @return the size of the container in C Code
     */
    public static String getListSize(final InternalTypeContainer cont) {
        final String size;
        if (cont.getAccessTypeIfList() == InternalTypeRep.CANDIDATE) {
            size = UnifiedNameContainer.getCandidate();
        } else if (cont.getAccessTypeIfList() == InternalTypeRep.VOTER) {
            size = UnifiedNameContainer.getVoter();
        } else if (cont.getAccessTypeIfList() == InternalTypeRep.SEAT) {
            size = UnifiedNameContainer.getSeats();
        } else {
            size = "";
        }
        if ("".equals(size)) {
            ErrorLogger.log("");
        }
        return size;
    }
    //
    // /**
    // * Creates the C-Type text representation of the given internal type
    // * container.
    // * Arrays are created as arrays: "unsigned int votes[" +
    // * UnifiedNameContainer.getVoter() + "][" +
    // * UnifiedNameContainer.getCandidate()
    // * + "]", for example
    // *
    // * @param electionContainer the container for which the C type should be
    // *                          created
    // * @param name the name of the variable
    // * @return the c type
    // */
    // public static String getCType(ElectionTypeContainer electionContainer,
    //                               String name) {
    //     String decl = "unsigned int " + name;
    //     decl = decl + electionContainer.getInputType().getSimpleType(true);
    //     return decl;
    // }
    //
    // /**
    // * Creates the C-Type text representation of the given
    // * Internaltypecontainer.
    // * Arrays are created as pointers: "unsigned int *", for example.
    // *
    // * @param electionContainer the container for which the C type should be
    // *                          created
    // * @return the c type
    // */
    // public static String getCTypePointer(ElectionTypeContainer
    //                                          electionContainer) {
    //     String decl = electionContainer.getOutputType().getSimpleType(true);
    //     return decl;
    // }

    /**
     * If the given InternaltypeContainer represents a list, it generates the
     * String representing a corresponding C-Array. Ex return: "[" +
     * UnifiedNameContainer.getCandidate() + "]"
     *
     * @param cont
     *            the container for which the C type should be created
     * @return the amount of square brackets and length constants needed
     */
    public static String getCArrayType(final InternalTypeContainer cont) {
        InternalTypeContainer currentContainer = cont;
        String decl = "";
        while (currentContainer.isList()) {
            decl += OPENING_BRACKETS
                    + getListSize(currentContainer)
                    + CLOSING_BRACKETS;
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    /**
     * Generates the declaration String for a voting function depending on its
     * input and result type. This is the voting method which will be presented
     * to the user, so it should not contain structs, but just simple data types
     * (except if it cannot be helped).
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @return the voting function declaration line
     */
    public static String generateSimpleDeclString(final ElectionTypeContainer container) {
        String decl = RESULT + BLANK
                + UnifiedNameContainer.getVotingMethod()
                + UINT_LOOP_START + AMOUNT_VOTES + COMMA + BLANK + VOTES + PAREN_R_BRACE_L;
        final String[] sizeOfDimensions =
                container.getInputType().getSizeOfDimensions();
        if (sizeOfDimensions.length > 0) {
            sizeOfDimensions[0] = AMOUNT_VOTES;
        }

        decl = decl.replace(RESULT,
                container.getInputType().getDataTypeAndSign() + container
                        .getOutputType().getDimensionDescriptor(true));
        decl = decl.replace(VOTES,
                container.getInputType().getDataTypeAndSign() + BLANK
                        + UnifiedNameContainer.getVotingArray()
                        + container.getInputType()
                                .getDimensionDescriptor(sizeOfDimensions));

        return decl;
    }

    /** Generates a stub equal method
     *
      * @param container container for in and output type of the election for that the stub is to be created
     * @return String that contains the method
     */
    public static String generateSimpleEqualsFunction(final ElectionTypeContainer container) {
        String func = INT + BLANK + "equals" + "(" + STRUCT + BLANK + AUTO + UNDERSCORE + RESULT + BLANK + "res1" + COMMA +  STRUCT + BLANK + AUTO + UNDERSCORE + RESULT + BLANK + "res2" + PAREN_R_BRACE_L;
        func += "\n return 0; \n}";
        func = func.replace(RESULT, container.getOutputType().getDataType().toString());
        return func;
    }

    public static String generateSimpleBallotModifier(final ElectionTypeContainer container) {
        String mod = "//" + container.getInputType().getDataTypeAndSign() + container.getInputType().getDimensionDescriptor(true) + NEWLINE;
         mod += (intVarEqualsCode(TOTAL_DIFF)
                + zero() + CCodeHelper.SEMICOLON) + NEWLINE;
        mod += (intVarEqualsCode(POS_DIFF) + zero()
                + CCodeHelper.SEMICOLON) + NEWLINE;
        String voteName = UnifiedNameContainer.getNewVotesName();
        final String voteContainer =
                container.getInputType().getStruct().getStructAccess() + space()
                        + voteName + CCodeHelper.SEMICOLON + NEWLINE;
        mod += (voteContainer) + NEWLINE;

        CodeArrayListBeautifier codeList = new CodeArrayListBeautifier();
        final List<String> loopVars =
                addNestedForLoopTop(codeList,
                        container.getInputType().getSizeOfDimensionsAsList(),
                        new ArrayList<String>());
        container.getInputType().flipVote(voteName, UnifiedNameContainer.getOrigVotesName(),
                loopVars, codeList);

        // addConditionalValue(voteName, inType);
        // The votes have to be valid afterwards
        addNestedForLoopBot(codeList, container.getInputType().getAmountOfDimensions());

        for (String line : codeList.getCodeArrayList()) {
            mod += line + NEWLINE;
        }
        // No more changes than margin allows
        mod += (functionCode(ASSUME, leq(POS_DIFF, MARGIN))
                + CCodeHelper.SEMICOLON) + NEWLINE;
        mod += (functionCode(ASSUME, eq(TOTAL_DIFF, zero()))
                + CCodeHelper.SEMICOLON) + NEWLINE;
        return mod;
    }
    /**
     * Generates the declaration String for a voting function depending on its
     * input and result type.
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @param voteStructName
     *            the vote struct name
     * @return the voting function declaration line
     */
    public static String generateStructDeclString(final ElectionTypeContainer container,
                                                  final String voteStructName) {
        String decl = RESULT + BLANK
                + UnifiedNameContainer.getVotingMethod()
                + UINT_LOOP_START + AMOUNT_VOTES + COMMA + BLANK + VOTES + PAREN_R_BRACE_L;

        decl = decl.replace(RESULT,
                container.getOutputStruct().getStructAccess());
        decl = decl.replace(VOTES,
                container.getInputStruct().getStructAccess() + BLANK
                        + voteStructName);

        return decl;
    }

    /**
     * Generates the complete function block which is placed in the C editor if
     * the user creates a new election description. It adds the explanatory
     * comments and the closing curly bracket
     *
     * @param container
     *            the input format of the voting array passed to the function
     * @param name
     *            the name of the election
     * @param templateHandler
     *            the Template handler which generated input and result types
     * @param stringResourceLoader
     *            the string resource loader currently used
     * @return the complete voting function
     */
    public static ElectionDescription
            generateElectionDescription(final ElectionTypeContainer container,
                                        final String name,
                                        final ElectionTemplateHandler templateHandler,
                                        final StringResourceLoader stringResourceLoader) {
        final ArrayList<String> code = new ArrayList<String>();
        final String inputIdInFile = container.getInputType().getInputIDinFile();
        final String outputIdInFile = container.getOutputType().getOutputIDinFile();

        code.add(lineComment(colon(stringResourceLoader.getStringFromID(inputIdInFile),
                                   stringResourceLoader.getStringFromID(inputIdInFile + EXP))));
        code.add(lineComment(colon(stringResourceLoader.getStringFromID(outputIdInFile),
                                   stringResourceLoader.getStringFromID(outputIdInFile + EXP))));
        code.add(generateSimpleDeclString(container));
        code.add(CLOSING_BRACES + BLANK);
        final ElectionDescription description =
                new ElectionDescription(name,
                                        container.getInputType(),
                                        container.getOutputType(),
                                        0, 0, 0, true);
        description.setCode(code);
        return description;
    }

    /**
     * Returns the max value an element of the given ElectionTypeContainer can
     * have.
     *
     * @param container
     *            the list whose elements max value needs to be determined
     * @return max value an element of the given ElectionTypeContainer can have
     */
    public static String getMax(final ElectionTypeContainer container) {
        return container.getInputType().getMaximalValue();
    }

    /**
     * Returns the minimum value an element of the given ElectionTypeContainer
     * can have.
     *
     * @param container
     *            the list whose elements min value needs to be determined
     * @return minimum value an element of the given ElectionTypeContainer can
     *         have
     */
    public static String getMin(final ElectionTypeContainer container) {
        return container.getInputType().getMinimalValue();
    }

    /**
     * Checks if is valid C name.
     *
     * @param name
     *            the name
     * @return true, if is valid C name
     */
    public static boolean isValidCName(final String name) {
        if (name.matches(characterRegex)) {
            if (!cReservedWords.stream().anyMatch(str -> str.equals(name))) {
                // it is not a reserved word
                return true;
            }
        }
        System.out.println("The given symbolic variable name"
                            + " is not valid in C.");
        return false;
    }

    /**
     *
     *
     * @param code
     *            the code beautifier it should be added to
     * @param dimensions
     *            the size of each dimension,
     * @param nameOfLoopVariables
     *            an empty, new list. Every new loop variable will be appended
     * @return the list
     */
    public static List<String> addNestedForLoopTop(final CodeArrayListBeautifier code,
                                                   final List<String> dimensions,
                                                   final List<String> nameOfLoopVariables) {
        if (dimensions.size() > 0) {
            String name = "loop_" + nameOfLoopVariables.size();
            name = code.getNotUsedVarName(name);
            nameOfLoopVariables.add(name);
            code.add(forLoopHeaderCode(name, lt(), dimensions.get(0)));
            code.addTab();
            return addNestedForLoopTop(code,
                                       dimensions.subList(1, dimensions.size()),
                                       nameOfLoopVariables);
        }
        return nameOfLoopVariables;
    }

    /**
     *
     *
     * @param code
     *            the code
     * @param dimensions
     *            the dimensions
     */
    public static void addNestedForLoopBot(final CodeArrayListBeautifier code,
                                           final int dimensions) {
        for (int i = 0; i < dimensions; i++) {
            code.add(CLOSING_BRACES);
        }
    }
}
