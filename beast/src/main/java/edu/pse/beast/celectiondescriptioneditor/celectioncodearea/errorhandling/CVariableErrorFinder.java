package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling;

import static edu.pse.beast.toolbox.CCodeHelper.charVar;
import static edu.pse.beast.toolbox.CCodeHelper.defineIfNonDef;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.intVar;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedCharVar;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedIntVar;
import static edu.pse.beast.toolbox.CCodeHelper.x;
import static edu.pse.beast.toolbox.CCodeHelper.y;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.propertychecker.CBMCCodeGenerator;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.UnifiedNameContainer;

/**
 * This class uses an external compiler (either gcc on linux or cl on windows)
 * to find errors in the c code.
 *
 * @author Holger Klein
 */
public final class CVariableErrorFinder {
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
        // TODO Use unified name container here later.
        final ArrayList<String> separated = new ArrayList<String>();
        separated.add(defineIfNonDef(UnifiedNameContainer.getVoter(), one()));
        separated.add(defineIfNonDef(UnifiedNameContainer.getCandidate(), one()));
        separated.add(defineIfNonDef(UnifiedNameContainer.getSeats(), one()));
        separated.add(defineIfNonDef(UnifiedNameContainer.getStacks(), one()));

        // Since we want to reserve the function name "verify", we define it
        // here..
        separated.add(CCodeHelper.VOID + space() + functionCode("verify") + space()
                + CCodeHelper.OPENING_BRACES + CCodeHelper.CLOSING_BRACES);

        // WORKAROUND: Will change if I think of a more elegant solution (if
        // there is one) (look at issue 49 on github)
        // Maybe it is possible to include all CBMC functions, but I will have
        // to see.
        // At least I can extract it to a file, which would make updating
        // easier.

        separated.add(CCodeHelper.VOID + space()
                + functionCode(CBMCCodeGenerator.CPROVER_ASSERT,
                intVar(x()), intVar(y()))
                + space() + CCodeHelper.OPENING_BRACES + CCodeHelper.CLOSING_BRACES);
        separated.add(CCodeHelper.VOID + space()
                + functionCode(CBMCCodeGenerator.CPROVER_ASSUME, intVar(x()))
                + space() + CCodeHelper.OPENING_BRACES + CCodeHelper.CLOSING_BRACES);

        separated.addAll(Arrays.asList(electionDesc.getContainer() // Add all used structs
                .getStructDefinitions().split("\\n")));

        separated.add(CCodeHelper.VOID + space()
                + functionCode(CBMCCodeGenerator.ASSUME, intVar(x()))
                + space() + CCodeHelper.OPENING_BRACES + CCodeHelper.CLOSING_BRACES);
        separated.add(CCodeHelper.VOID + space()
                + functionCode(CBMCCodeGenerator.ASSERT, intVar(x()))
                + space() + CCodeHelper.OPENING_BRACES + CCodeHelper.CLOSING_BRACES);
        separated.add(CCodeHelper.VOID + space()
                + functionCode(CBMCCodeGenerator.ASSERT2, intVar(x()), intVar(y()))
                + space() + CCodeHelper.OPENING_BRACES + CCodeHelper.CLOSING_BRACES);

        separated.add(intVar(functionCode(CBMCCodeGenerator.NONDET_INT)
                + space() + CCodeHelper.OPENING_BRACES
                + CCodeHelper.RETURN + space() + zero() + CCodeHelper.SEMICOLON
                + CCodeHelper.CLOSING_BRACES));
        separated.add(unsignedIntVar(functionCode(CBMCCodeGenerator.NONDET_UINT)
                + space() + CCodeHelper.OPENING_BRACES
                + CCodeHelper.RETURN + space() + zero() + CCodeHelper.SEMICOLON
                + CCodeHelper.CLOSING_BRACES));

        separated.add(unsignedCharVar(functionCode(CBMCCodeGenerator.NONDET_UCHAR)
                + space() + CCodeHelper.OPENING_BRACES
                + CCodeHelper.RETURN + space() + zero() + CCodeHelper.SEMICOLON
                + CCodeHelper.CLOSING_BRACES));
        separated.add(charVar(functionCode(CBMCCodeGenerator.NONDET_CHAR)
                + space() + CCodeHelper.OPENING_BRACES
                + CCodeHelper.RETURN + space() + zero() + CCodeHelper.SEMICOLON
                + CCodeHelper.CLOSING_BRACES));

        // WORKAROUND end

        separated.addAll(code);
        separated.add(intVar(functionCode(CBMCCodeGenerator.MAIN))
                + space() + CCodeHelper.OPENING_BRACES);
        separated.add(CCodeHelper.CLOSING_BRACES);
        final int lineOffset = separated.size() + 1;

        return new ArrayList<CodeError>(
                DeepErrorChecker.checkCodeForErrors(separated, lineOffset)
        );
    }
}
