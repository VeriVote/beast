package edu.pse.beast.types.cbmctypes.outputplugins;

import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
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
public class SingleCandidate extends CBMCOutputType {

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
        code.add("void verifyMain() {");
        // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
        // UnifiedNameContainer.getVoter() + "], diff[" +
        // UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");
        code.addTab();
        code.add("int total_diff = 0;");
        code.add("int " + UnifiedNameContainer.getNewResultName() + "1 = "
                + UnifiedNameContainer.getVotingMethod() + "("
                + UnifiedNameContainer.getNewVotesName() + "1);");
        code.add("assert(" + UnifiedNameContainer.getNewResultName() + "1 == "
                + UnifiedNameContainer.getOrigResultName() + ");");
        code.deleteTab();
        // end of the function
        code.add("}");
        return code;
    }

    @Override
    public CodeArrayListBeautifier addVotesArrayAndInit(final CodeArrayListBeautifier code,
                                                        final int voteNumber) {
        String electX = "unsigned int elect" + voteNumber;
        electX = electX + getCArrayType();
        code.add(electX + ";");
        code.add("elect" + voteNumber + " = "
                + UnifiedNameContainer.getVotingMethod() + "(votes" + voteNumber
                + ");");
        return code;
    }

    @Override
    public String getCArrayType() {
        return ""; // we have a single candidate, so no array
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
