package edu.pse.beast.types.cbmctypes.outputplugins;

import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.unsignedIntVar;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

/**
 * The Class SingleCandidate.
 *
 * @author Lukas Stapelbroek
 */
public final class SingleCandidate extends CBMCOutputType {
    /** The Constant DIMENSIONS. */
    private static final int DIMENSIONS = 0;

    /** The Constant SIZE_OF_DIMENSIONS. */
    private static final String[] SIZE_OF_DIMENSIONS = {};

    /**
     * The constructor.
     */
    public SingleCandidate() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getOutputIDinFile() {
        return "CAND_OR_UNDEF";
    }

    @Override
    public boolean isOutputOneCandidate() {
        return true;
    }

    @Override
    public CodeArrayListBeautifier addMarginVerifyCheck(final CodeArrayListBeautifier code) {
        code.add(CCodeHelper.VOID + space() + "verifyMain()"
                + space() + CCodeHelper.OPENING_BRACES);
        // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
        // UnifiedNameContainer.getVoter() + "], diff[" +
        // UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");
        code.addTab();
        code.add(varAssignCode(CCodeHelper.INT + space() + "total_diff",
                               zero())
                + CCodeHelper.SEMICOLON);
        code.add(varAssignCode(CCodeHelper.INT + space()
                                + UnifiedNameContainer.getNewResultName() + one(),
                               functionCode(UnifiedNameContainer.getVotingMethod(),
                                            UnifiedNameContainer.getNewVotesName() + one()))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode("assert",
                              eq(UnifiedNameContainer.getNewResultName() + one(),
                                 UnifiedNameContainer.getOrigResultName()))
                + CCodeHelper.SEMICOLON);
        code.deleteTab();
        // end of the function
        code.add(CCodeHelper.CLOSING_BRACES);
        return code;
    }

    @Override
    public CodeArrayListBeautifier addVotesArrayAndInit(final CodeArrayListBeautifier code,
                                                        final int voteNumber) {
        String electX = unsignedIntVar(ELECT + voteNumber);
        electX = electX + getCArrayType();
        code.add(electX + CCodeHelper.SEMICOLON);
        code.add(varAssignCode(ELECT + voteNumber,
                               functionCode(UnifiedNameContainer.getVotingMethod(),
                                            VOTES + voteNumber))
                + CCodeHelper.SEMICOLON);
        return code;
    }

    @Override
    public String getCArrayType() {
        return ""; // We have a single candidate, so no array.
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(InternalTypeRep.INTEGER);
    }

    @Override
    public String getResultDescriptionString(final List<String> result) {
        String toReturn = "winner: ";
        try {
            toReturn = GUIController.getController().getElectionSimulation()
                    .getPartyName(Integer.parseInt(result.get(0)));
        } catch (NumberFormatException e) {
            toReturn = result.get(0);
        }
        return toReturn;
    }

    @Override
    public String otherToString() {
        return "Single candidate";
    }
}
