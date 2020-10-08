package edu.pse.beast.propertychecker;

import static edu.pse.beast.toolbox.CCodeHelper.comma;
import static edu.pse.beast.toolbox.CCodeHelper.constAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.typeVar;

import java.io.File;
import java.util.List;

/**
 * The Class CBMCProcess.
 *
 * @author Lukas Stapelbroek
 */
public abstract class CBMCProcess extends Checker {
    /** The Constant QUOTE. */
    private static final String QUOTE = "\"";

    /** The Constant PASS_C_CONST. */
    private static final String PASS_C_CONST = "-D";

    static String UNWIND_SET = "--unwindset";

    /** The Constant ENABLE_USER_INCLUDE. */
    private static final String ENABLE_USER_INCLUDE = "-I";

    /** The Constant CBMC_XML_OUTPUT. */
    static final String CBMC_XML_OUTPUT = "--xml-ui";

    /**
     * Put some name in quotes.
     *
     * @param name
     *            the name string
     * @return the quoted string
     */
    static String quote(final String name) {
        return name != null && !"".equals(name)
                ? QUOTE + name + QUOTE : QUOTE;
    }

    /**
     * Print a quote.
     *
     * @return the quote
     */
    static String quote() {
        return quote("");
    }

    /**
     * Pass constant.
     *
     * @param constName
     *            the const name
     * @param constValue
     *            the const value
     * @return the string
     */
    static String passConstant(final String constName,
                               final int constValue) {
        return typeVar(PASS_C_CONST, constAssignCode(constName, constValue));
    }

    /**
     * User include files (typically c-header-files ending with ".h").
     *
     * @param folderPath
     *            the path to the folder that holds the user include files
     * @return the string
     */
    static String getUserIncludeFiles(final String folder) {
        return typeVar(ENABLE_USER_INCLUDE, folder);
    }

    /**
     * Produce unwind strings.
     *
     * @param unwindset the unwindset
     * @return the string
     */
    static String unwindString(final List<String> unwindset) {
        String unw = unwindset.isEmpty() ? "" : UNWIND_SET + space();
        int i = 0;
        for (final String unwind: unwindset) {
            unw += (i++ != (unwindset.size() - 1)) ? comma(unwind) : unwind;
        }
        return quote(unw);
    }

    /**
     * Creates a new CBMCProcess that is a super class for the system specific
     * processes that run cbmc.
     *
     * @param voters
     *            the amount of voters
     * @param candidates
     *            the amount of candidates
     * @param seats
     *            the amount of seats
     * @param advanced
     *            the string that represents the advanced options
     * @param toCheck
     *            the file to check with cbmc
     * @param parent
     *            the parent CheckerFactory, that has to be notified about
     *            finished checking
     * @param result
     *            the result
     */
    public CBMCProcess(final int voters, final int candidates, final int seats,
                       final String advanced, final List<String> unwindset,
                       final File toCheck, final CheckerFactory parent,
                       final Result result) {
        super(voters, candidates, seats, advanced, unwindset, toCheck, parent, result);
    }
}
