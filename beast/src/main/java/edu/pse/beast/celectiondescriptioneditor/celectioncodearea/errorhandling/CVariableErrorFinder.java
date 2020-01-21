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
        ArrayList<String> seperated = new ArrayList<String>();
        seperated.add(IF_UNDEFINED + BLANK + UnifiedNameContainer.getVoter());
        seperated.add(DEFINE + BLANK + UnifiedNameContainer.getVoter() + BLANK + ONE);
        seperated.add(END_IF);

        seperated.add(IF_UNDEFINED + BLANK + UnifiedNameContainer.getCandidate());
        seperated.add(DEFINE + BLANK + UnifiedNameContainer.getCandidate() + BLANK + ONE);
        seperated.add(END_IF);

        seperated.add(IF_UNDEFINED + BLANK + UnifiedNameContainer.getSeats());
        seperated.add(DEFINE + BLANK + UnifiedNameContainer.getSeats() + BLANK + ONE);
        seperated.add(END_IF);

        // Since we want to reserve the function name "verify", we define it
        // here..
        seperated.add("void verify() {}");

        // WORKAROUND: Will change if I think of a more elegant solution (if
        // there is one) (look at issue 49 on github)
        // Maybe it is possible to include all CBMC functions, but I will have
        // to see.
        // At least I can extract it to a file, which would make updating
        // easier.

        seperated.add("void __CPROVER_assert(int x, int y) {}");
        seperated.add("void __CPROVER_assume(int x) {}");

        seperated.addAll(Arrays.asList(electionDesc.getContainer()
                .getStructDefinitions().split("\\n"))); // add all used structs

        seperated.add("void assume(int x) {}");
        seperated.add("void assert(int x) {}");
        seperated.add("void assert2(int x, int y) {}");

        seperated.add("int nondet_int() {return 0;}");
        seperated.add("unsigned int nondet_uint() {return 0;}");
        seperated.add("unsigned char nondet_uchar() {return 0;}");
        seperated.add("char nondet_char() {return 0;}");

        // WORKAROUND end

        seperated.addAll(code);

        seperated.add("int main() {");
        seperated.add("}");

        int lineOffset = seperated.size() + 1;

        final ArrayList<CodeError> found =
                new ArrayList<CodeError>(
                        DeepErrorChecker.checkCodeForErrors(seperated, lineOffset)
                );
        return found;
    }
}
