package edu.pse.beast.types.cbmctypes.outputplugins;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

/**
 * The Class Parliament.
 *
 * @author Lukas Stapelbroek
 */
public final class Parliament extends CBMCOutputType {
    /** The Constant DIMENSIONS. */
    private static final int DIMENSIONS = 1;

    /** The Constant SIZE_OF_DIMENSIONS. */
    private static final String[] SIZE_OF_DIMENSIONS = {
            UnifiedNameContainer.getCandidate()
    };

    /**
     * The constructor.
     */
    public Parliament() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getOutputIDinFile() {
        return "CAND_PER_SEAT";
    }

    @Override
    public boolean isOutputOneCandidate() {
        return false;
    }

    @Override
    public CodeArrayListBeautifier addMarginVerifyCheck(final CodeArrayListBeautifier code) {
        code.add("void verifyMain() {");
        // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
        // UnifiedNameContainer.getVoter() + "], diff[" +
        // UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");

        code.addTab();
        code.add(super.getContainer().getOutputStruct().getStructAccess()
                + "tmp = " + UnifiedNameContainer.getVotingMethod() + "("
                + UnifiedNameContainer.getNewVotesName() + "1);");
        code.add("unsigned int *tmp_result = tmp."
                + UnifiedNameContainer.getStructValueName() + ";");
        // create the array where the new seats will get saved
        code.add("unsigned int " + UnifiedNameContainer.getNewResultName()
                + "1[" + UnifiedNameContainer.getSeats() + "];");
        // iterate over the seat array, and fill it
        code.add("for (int i = 0; i < " + UnifiedNameContainer.getSeats()
                + "; i++) {");
        code.addTab();
        // we do this, so our cbmc parser can read out the value of the
        // array
        code.add("" + UnifiedNameContainer.getNewResultName()
                + "1[i] = tmp_result[i];");
        code.deleteTab();
        code.add("}"); // close the for loop
        // iterate over all candidates / seats
        code.add("for (int i = 0; i < " + UnifiedNameContainer.getSeats()
                + "; i++) {");
        code.addTab();
        code.add(
                "assert(" + UnifiedNameContainer.getNewResultName() + "1[i] == "
                        + UnifiedNameContainer.getOrigResultName() + "[i]);");
        code.deleteTab();
        code.add("}"); // end of the for loop
        code.deleteTab();
        code.add("}"); // end of the function
        return code;
    }

    @Override
    public CodeArrayListBeautifier addVotesArrayAndInit(final CodeArrayListBeautifier code,
                                                        final int voteNumber) {
        String electX = super.getContainer().getOutputStruct().getStructAccess()
                + " elect" + voteNumber + " = "
                + UnifiedNameContainer.getVotingMethod() + "(votes" + voteNumber
                + ");";
        code.add(electX);
        return code;
    }

    @Override
    public String getCArrayType() {
        return "[" + UnifiedNameContainer.getSeats() + "]";
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
        String toReturn = "[";
        for (Iterator<String> iterator = result.iterator(); iterator
                .hasNext();) {
            String currentValue = iterator.next();
            try {
                toReturn = toReturn
                        + GUIController.getController().getElectionSimulation()
                                .getPartyName(Integer.parseInt(currentValue))
                        + ", ";
            } catch (NumberFormatException e) {
                toReturn = toReturn + currentValue + ", ";
            }
        }
        toReturn = toReturn + "]";
        return toReturn;
    }

    @Override
    public String otherToString() {
        return "Parliament";
    }
}
