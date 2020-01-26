package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.toolbox.UnifiedNameContainer;

/**
 * This class uses an external compiler (either gcc on linux or cl on windows)
 * to find errors in the c code.
 *
 * @author Holger Klein
 */
public final class CVariableErrorFinder {
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant ONE. */
    private static final String ONE = "1";

    /** The Constant "#ifndef". */
    private static final String IF_UNDEFINED = "#ifndef";
    /** The Constant "#define". */
    private static final String DEFINE = "#define";
    /** The Constant "#endif". */
    private static final String END_IF = "#endif";

    /**
     * Instantiates a new c variable error finder.
     */
    private CVariableErrorFinder() { }

    /**
     * Find errors in the given c code.
     *
     * @param code
     *            the c code as list of code lines
     * @param electionDesc
     *            the election description
     * @return a list of code errors
     */
    public static List<CodeError> findErrors(final List<String> code,
                                             final ElectionDescription electionDesc) {
        // TODO use unified name container here later
        final ArrayList<String> separated = new ArrayList<String>();
        separated.add(IF_UNDEFINED + BLANK + UnifiedNameContainer.getVoter());
        separated.add(DEFINE + BLANK + UnifiedNameContainer.getVoter() + BLANK + ONE);
        separated.add(END_IF);

        separated.add(IF_UNDEFINED + BLANK + UnifiedNameContainer.getCandidate());
        separated.add(DEFINE + BLANK + UnifiedNameContainer.getCandidate() + BLANK + ONE);
        separated.add(END_IF);

        separated.add(IF_UNDEFINED + BLANK + UnifiedNameContainer.getSeats());
        separated.add(DEFINE + BLANK + UnifiedNameContainer.getSeats() + BLANK + ONE);
        separated.add(END_IF);

        // Since we want to reserve the function name "verify", we define it
        // here..
        separated.add("void verify() {}");

        // WORKAROUND: Will change if I think of a more elegant solution (if
        // there is one) (look at issue 49 on github)
        // Maybe it is possible to include all CBMC functions, but I will have
        // to see.
        // At least I can extract it to a file, which would make updating
        // easier.

        separated.add("void __CPROVER_assert(int x, int y) {}");
        separated.add("void __CPROVER_assume(int x) {}");

        separated.addAll(Arrays.asList(electionDesc.getContainer()
                .getStructDefinitions().split("\\n"))); // add all used structs

        separated.add("void assume(int x) {}");
        separated.add("void assert(int x) {}");
        separated.add("void assert2(int x, int y) {}");

        separated.add("int nondet_int() {return 0;}");
        separated.add("unsigned int nondet_uint() {return 0;}");
        separated.add("unsigned char nondet_uchar() {return 0;}");
        separated.add("char nondet_char() {return 0;}");

        // WORKAROUND end

        separated.addAll(code);

        separated.add("int main() {");
        separated.add("}");

        final int lineOffset = separated.size() + 1;

        final ArrayList<CodeError> found =
                new ArrayList<CodeError>(
                        DeepErrorChecker.checkCodeForErrors(separated, lineOffset)
                );
        return found;
    }
}
