package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import edu.pse.beast.codearea.errorhandling.CodeError;

/**
 * This class is used by the C error finder. It generates code errors from the
 * given information.
 *
 * @author Holger Klein
 */
public final class CCodeErrorFactory {

    /** The Constant IDS. */
    private static final String[] IDS =
        {
        "compilererror",
        "antlr"
        };

    /**
     * Instantiates a new c code error factory.
     */
    private CCodeErrorFactory() { }

    /**
     * Wraps the error from the compiler into a CodeError Object. If information
     * for these parts are missing, just pass "" for the strings, and 0 for the
     * positions.
     *
     * @param line
     *            the line the error is in
     * @param posInLine
     *            the position in that line
     * @param varName
     *            the name of the variable that causes the error
     * @param message
     *            the message the compiler gave in full text
     * @return the created code error, containing all information given to this
     *         function
     */
    public static CodeError generateCompilerError(final int line,
                                                  final int posInLine,
                                                  final String varName,
                                                  final String message) {
        CodeError toReturn = new CodeError(line, posInLine, IDS[0], 0, -1, -1);
        toReturn.setExtraInfo("var", varName);
        toReturn.setExtraInfo("msg", message);
        return toReturn;
    }
}
