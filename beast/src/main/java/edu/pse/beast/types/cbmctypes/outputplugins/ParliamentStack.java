package edu.pse.beast.types.cbmctypes.outputplugins;

import static edu.pse.beast.toolbox.CCodeHelper.arrAcc;
import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.colon;
import static edu.pse.beast.toolbox.CCodeHelper.comma;
import static edu.pse.beast.toolbox.CCodeHelper.dotArr;
import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.lt;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.uintVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedIntVar;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.propertychecker.CBMCCodeGenerator;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

/**
 * The Class ParliamentStack.
 *
 * @author Lukas Stapelbroek
 */
public final class ParliamentStack extends CBMCOutputType {
    /** The Constant I. */
    private static final String I = "i";
    /** The Constant TMP_STRING. */
    private static final String TMP_STRING = "tmp";

    /** The Constant DIMENSIONS. */
    private static final int DIMENSIONS = 1;

    /** The Constant SIZE_OF_DIMENSIONS. */
    private static final String[] SIZE_OF_DIMENSIONS = {
            UnifiedNameContainer.getCandidate()
    };

    /**
     * The constructor.
     */
    public ParliamentStack() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getOutputIDinFile() {
        return "STACK_PER_PARTY";
    }

    @Override
    public boolean isOutputOneCandidate() {
        return false;
    }

    @Override
    public CodeArrayListBeautifier addMarginVerifyCheck(final CodeArrayListBeautifier code) {
        code.add(CCodeHelper.VOID + space() + "verifyMain()"
                + space() + CCodeHelper.OPENING_BRACES);
        // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
        // UnifiedNameContainer.getVoter() + "], diff[" +
        // UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");

        code.addTab();
        code.add(varAssignCode(CCodeHelper.STRUCT + space() + "stack_result"
                                    + space() + TMP_STRING,
                               functionCode(UnifiedNameContainer.getVotingMethod(),
                                            UnifiedNameContainer.getNewVotesName()
                                            + one())
                ) + CCodeHelper.SEMICOLON);
        code.add(uintVarEqualsCode("*tmp_result") + TMP_STRING + dotArr() + CCodeHelper.SEMICOLON);
        // Create the array where the new seats will get saved
        code.add(unsignedIntVar(
                arrAccess(UnifiedNameContainer.getNewResultName() + one(),
                          UnifiedNameContainer.getSeats())
                ) + CCodeHelper.SEMICOLON);
        // iterate over the seat array, and fill it
        code.add(forLoopHeaderCode(I, lt(), UnifiedNameContainer.getSeats()));
        code.addTab();
        // We do this, so our cbmc parser can read out the value of the
        // array
        code.add(varAssignCode(
                    arrAccess(UnifiedNameContainer.getNewResultName() + one(), I),
                    arrAccess("tmp_result", I)
                ) + CCodeHelper.SEMICOLON);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES); // Close the for loop
        // iterate over all
        code.add(forLoopHeaderCode(I, lt(), UnifiedNameContainer.getSeats()));
        code.addTab();
        // candidates / seats
        code.add(functionCode(CBMCCodeGenerator.ASSERT,
                              eq(arrAccess(
                                      UnifiedNameContainer.getNewResultName() + one(), I),
                                 arrAccess(
                                      UnifiedNameContainer.getOrigResultName(), I))
                ) + CCodeHelper.SEMICOLON);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES); // End of the for loop
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES); // End of the function
        final CodeArrayListBeautifier c = new CodeArrayListBeautifier();
        c.add("IF SOMETHING GOES WRONG: SEARCH FOR DEBUG56693");
        return c;
    }

    @Override
    public CodeArrayListBeautifier addVotesArrayAndInit(final CodeArrayListBeautifier code,
                                                        final int voteNumber) {
        code.add(varAssignCode(super.getContainer().getOutputStruct().getStructAccess()
                                + space() + ELECT + voteNumber,
                               functionCode(UnifiedNameContainer.getVotingMethod(),
                                            VOTES + voteNumber)
                ) + CCodeHelper.SEMICOLON);
        return code;
    }

    @Override
    public String getCArrayType() {
        return arrAcc(UnifiedNameContainer.getCandidate());
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(
                new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                InternalTypeRep.VOTER
        );
    }

    @Override
    public String getResultDescriptionString(final List<String> result) {
        String toReturn = "";
        int index = 0;
        for (Iterator<String> iterator = result.iterator(); iterator
                .hasNext();) {
            final String currentValue = iterator.next();
            try {
                toReturn += comma(colon(GUIController.getController()
                                            .getElectionSimulation().getPartyName(index),
                                        currentValue));
            } catch (NumberFormatException e) {
                toReturn += comma(colon(Integer.toString(index), currentValue));
            }
            index++;
        }
        return arrAcc(toReturn);
    }

    @Override
    public String otherToString() {
        return "Parliament stack";
    }
}
