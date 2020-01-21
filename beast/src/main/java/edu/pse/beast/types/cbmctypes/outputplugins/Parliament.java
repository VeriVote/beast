package edu.pse.beast.types.cbmctypes.outputplugins;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CCodeHelper;
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
    /** The Constant SEMICOLON. */
    private static final String SEMICOLON = ";";
    /** The Constant BLANK. */
    private static final String BLANK = " ";
    /** The Constant COMMA. */
    private static final String COMMA = ",";
    /** The Constant EQUALS_SIGN. */
    private static final String EQUALS_SIGN = "=";
    /** The Constant PLUS_PLUS. */
    private static final String PLUS_PLUS = "++";
    /** The Constant LT_SIGN. */
    private static final String LT_SIGN = "<";

    /** The Constant I. */
    private static final String I = "i";
    /** The Constant ZERO. */
    private static final String ZERO = "0";
    /** The Constant ONE. */
    private static final String ONE = "1";

    /** The Constant OPENING_PARENTHESES. */
    private static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    private static final String CLOSING_PARENTHESES = ")";
    /** The Constant OPENING_BRACES. */
    private static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    private static final String CLOSING_BRACES = "}";
    /** The Constant OPENING_BRACKETS. */
    private static final String OPENING_BRACKETS = "[";
    /** The Constant CLOSING_BRACKETS. */
    private static final String CLOSING_BRACKETS = "]";

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
        code.add(CCodeHelper.VOID + BLANK + "verifyMain()" + BLANK + OPENING_BRACES);
        // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
        // UnifiedNameContainer.getVoter() + "], diff[" +
        // UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");

        code.addTab();
        code.add(super.getContainer().getOutputStruct().getStructAccess()
                + "tmp" + BLANK + EQUALS_SIGN + BLANK
                + UnifiedNameContainer.getVotingMethod() + OPENING_PARENTHESES
                + UnifiedNameContainer.getNewVotesName() + ONE
                + CLOSING_PARENTHESES + SEMICOLON);
        code.add(CCodeHelper.UNSIGNED + BLANK + CCodeHelper.INT + BLANK
                + "*tmp_result" + BLANK + EQUALS_SIGN + BLANK + "tmp."
                + UnifiedNameContainer.getStructValueName() + SEMICOLON);
        // create the array where the new seats will get saved
        code.add(CCodeHelper.UNSIGNED + BLANK + CCodeHelper.INT + BLANK
                + UnifiedNameContainer.getNewResultName() + ONE
                + OPENING_BRACKETS + UnifiedNameContainer.getSeats()
                + CLOSING_BRACKETS + SEMICOLON);
        // iterate over the seat array, and fill it
        code.add(CCodeHelper.FOR + BLANK + OPENING_PARENTHESES
                + CCodeHelper.INT + BLANK + I + BLANK + EQUALS_SIGN + BLANK
                + ZERO + SEMICOLON + BLANK + I + BLANK
                + UnifiedNameContainer.getSeats() + SEMICOLON + BLANK
                + I + PLUS_PLUS + CLOSING_PARENTHESES
                + BLANK + OPENING_BRACES);
        code.addTab();
        // we do this, so our cbmc parser can read out the value of the
        // array
        code.add("" + UnifiedNameContainer.getNewResultName() + ONE
                + OPENING_BRACKETS + I + CLOSING_BRACKETS + BLANK
                + EQUALS_SIGN + BLANK
                + "tmp_result" + OPENING_BRACKETS + I
                + CLOSING_BRACKETS + SEMICOLON);
        code.deleteTab();
        code.add(CLOSING_BRACES); // close the for loop
        // iterate over all candidates / seats
        code.add(CCodeHelper.FOR + BLANK + OPENING_PARENTHESES
                + CCodeHelper.INT + BLANK + I + BLANK
                + EQUALS_SIGN + BLANK + ZERO + SEMICOLON + BLANK
                + I + BLANK + LT_SIGN + BLANK
                + UnifiedNameContainer.getSeats() + SEMICOLON + BLANK
                + I + PLUS_PLUS + CLOSING_PARENTHESES
                + BLANK + OPENING_BRACES);
        code.addTab();
        code.add("assert" + OPENING_PARENTHESES
                + UnifiedNameContainer.getNewResultName() + ONE
                + OPENING_BRACKETS + I + CLOSING_BRACKETS + BLANK
                + "==" + BLANK + UnifiedNameContainer.getOrigResultName()
                + OPENING_BRACKETS + I + CLOSING_BRACKETS
                + CLOSING_PARENTHESES + SEMICOLON);
        code.deleteTab();
        code.add(CLOSING_BRACES); // end of the for loop
        code.deleteTab();
        code.add(CLOSING_BRACES); // end of the function
        return code;
    }

    @Override
    public CodeArrayListBeautifier addVotesArrayAndInit(final CodeArrayListBeautifier code,
                                                        final int voteNumber) {
        String electX = super.getContainer().getOutputStruct().getStructAccess()
                + BLANK + "elect" + voteNumber + BLANK + EQUALS_SIGN + BLANK
                + UnifiedNameContainer.getVotingMethod() + OPENING_PARENTHESES
                + "votes" + voteNumber
                + CLOSING_PARENTHESES + SEMICOLON;
        code.add(electX);
        return code;
    }

    @Override
    public String getCArrayType() {
        return OPENING_BRACKETS + UnifiedNameContainer.getSeats() + CLOSING_BRACKETS;
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
        String toReturn = OPENING_BRACKETS;
        for (Iterator<String> iterator = result.iterator(); iterator
                .hasNext();) {
            String currentValue = iterator.next();
            try {
                toReturn = toReturn
                        + GUIController.getController().getElectionSimulation()
                                .getPartyName(Integer.parseInt(currentValue))
                        + COMMA + BLANK;
            } catch (NumberFormatException e) {
                toReturn = toReturn + currentValue + COMMA + BLANK;
            }
        }
        toReturn = toReturn + CLOSING_BRACKETS;
        return toReturn;
    }

    @Override
    public String otherToString() {
        return "Parliament";
    }
}
