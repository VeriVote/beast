package edu.pse.beast.types;

import static edu.pse.beast.toolbox.CCodeHelper.dotStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
/**
 * The Class InputType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class InputType extends InOutType {

    /**
     * The constructor.
     *
     * @param unsigned
     *            the unsigned
     * @param dataType
     *            the data type
     * @param dimensions
     *            the dimensions
     * @param sizeOfDimensions
     *            the size of dimensions
     */
    public InputType(final boolean unsigned, final DataType dataType,
                     final int dimensions,
                     final String[] sizeOfDimensions) {
        super(unsigned, dataType, dimensions, sizeOfDimensions);
    }

    /**
     * Gets the input types.
     *
     * @return the input types
     */
    public static List<InputType> getInputTypes() {
        final ServiceLoader<InputType> loader =
                ServiceLoader.load(InputType.class);
        final List<InputType> types = new ArrayList<InputType>();
        for (Iterator<InputType> iterator = loader.iterator();
                iterator.hasNext();) {
            final InputType type = iterator.next();
            types.add(type);
        }
        return types;
    }

    @Override
    public final String toString() {
        return otherToString();
    }

    /**
     * Gets the input I din file.
     *
     * @return the ID used by this type in the String resources
     */
    public abstract String getInputIDinFile();

    /**
     * Checks if is voting for one candidate.
     *
     * @return true, if one voter can only vote for one candidate
     */
    public abstract boolean isVotingForOneCandidate();

    /**
     * Adds the headers needed for the specified checker.
     *
     * @param code
     *            the list to which the headers should be added to
     */
    public abstract void addCheckerSpecificHeaders(CodeArrayListBeautifier code);

    // /**
    //  * Extracts the voting data out of the given bounded model checker output
    //  * into a wrapper object.
    //  *
    //  * @param result
    //  *            the result of the computation from which the values
    //  *            will be extracted
    //  * @param numberCandidates
    //  *            the number of candidates
    //  * @return a wrapper which contains the values
    //  */
    // public final ResultValueWrapper extractVotes(List<String> result,
    //                                              int numberCandidates) {
    //     return this.helper.extractVariable("" + UnifiedNameContainer.getNewVotesName() + "",
    //                                        getDimension(),
    //                                        result).get(0);
    // } TODO remove unused code at the end of refactoring

    /**
     * Vets a value to determine if it is legal for the input type, or not.
     * Will always return a legal value for the given input type.
     *
     * @param container
     *            the type container
     * @param rows
     *            the new row of values
     * @param rowNumber
     *            TODO
     * @param positionInRow
     *            the position in row
     * @param newValue
     *            The value to be checked. If it is permissible this will equal the return value
     * @return the new value
     */
    /*

     */
    public abstract String vetValue(ElectionTypeContainer container,
                                    List<NEWRowOfValues> rows,
                                    int rowNumber, int positionInRow,
                                    String newValue);

    // public List<ResultValueWrapper> readVote(List<String> toExtract) {
    // return this.helper.extractVariable(UnifiedNameContainer.getVotingArray(),
    // getDimension(), toExtract);
    // }

    /**
     * Wrong input type array.
     *
     * @param amountCandidates
     *            the amount candidates
     * @param amountVoters
     *            the amount voters
     * @return the string[]
     */
    public String[] wrongInputTypeArray(final int amountCandidates,
                                        final int amountVoters) {
        final String[] toReturn = new String[amountCandidates];
        Arrays.fill(toReturn, "wrong input type");
        return toReturn;
    }

    /**
     * Gets the vote points.
     *
     * @param votes
     *            the votes
     * @param amountCandidates
     *            the amount candidates
     * @param amountVoters
     *            the amount voters
     * @return the vote points
     */
    public abstract String[] getVotePoints(String[][] votes,
                                           int amountCandidates,
                                           int amountVoters);

    /**
     * Gets the vote points.
     *
     * @param votes
     *            the votes
     * @param amountCandidates
     *            the amount candidates
     * @param amountVoters
     *            the amount voters
     * @return the vote points
     */
    public abstract String[] getVotePoints(String[] votes, int amountCandidates,
                                           int amountVoters);

    // public abstract void addMarginMainCheck(CodeArrayListBeautifier code, int
    // margin, List<String> origResult);

    /**
     * Returns the assignment of votingData e.g {1,2,3} for a array of shape
     * [3].
     *
     * @param wrapper
     *            the wrapper
     * @return the voting result code
     */
    public final String getVotingResultCode(final CBMCResultValueWrapper wrapper) {
        return printArray(wrapper);
    }

    /**
     * Adds the code for vote sum.
     *
     * @param code
     *            the code
     * @param unique
     *            the unique
     */
    public abstract void addCodeForVoteSum(CodeArrayListBeautifier code,
                                           boolean unique);

    @Override
    public abstract InternalTypeContainer getInternalTypeContainer();

    /**
     * Vets amount candidates. Will return a correct number of candidates.
     *
     * @param amountCandidates
     *            the amount candidates proposed
     * @return A correct candidate number, if amountCandidates is allowed, it will be returned
     */
    public abstract int vetAmountCandidates(int amountCandidates);

    /**
     * Vets amount voters. Will return a correct number of voters.
     * Negative values will most likely be considered wrong and therefore 1 will be returned.
     *
     * @param amountVoters
     *            the amount voters
     * @return A correct number of voters, if amountVoters is allowed, it will be returned.
     */
    public abstract int vetAmountVoters(int amountVoters);

    /**
     * Vets amount seats. Will return a correct number of seats.
     * Negative values will most likely be considered wrong in all cases and therefore 1 will be returned.
     * @param amountSeats
     *            the amount seats
     * @return A correct number of seats, if amountSeats is allowed, it will be returned.
     */
    public abstract int vetAmountSeats(int amountSeats);

    /**
     * Gets the num voting points.
     *
     * @param result
     *            the result
     * @return the num voting points
     */
    public abstract int getNumVotingPoints(ResultValueWrapper result);

    /**
     * Gets the vote description string.
     *
     * @param origVotes
     *            the orig votes
     * @return the vote description string
     */
    public abstract String getVoteDescriptionString(List<List<String>> origVotes);

    /**
     * Convert row to result value.
     *
     * @param row
     *            the row
     * @return the CBMC result value
     */
    public abstract CBMCResultValue convertRowToResultValue(NEWRowOfValues row);

    @Override
    public final String getInfo() { // TODO move later on further down
        return "";
    }

    /**
     * ASSERTION: newVotesName already has to be bounded to the max and min
     * values. It can have change the vote of origVotesName at a position
     * defined by loopNames to a vote that is different than the original one.
     *
     * @param newVotesName
     *            the new votes name
     * @param origVotesName
     *            the orig votes name
     * @param loopVars
     *            the loop vars
     * @param code
     *            the code
     */
    public abstract void flipVote(String newVotesName, String origVotesName,
                                  List<String> loopVars,
                                  CodeArrayListBeautifier code);

    /**
     * Sets the vote value.
     *
     * @param newVotesName
     *            the new votes name
     * @param origVotesName
     *            the orig votes name
     * @param loopVars
     *            the loop vars
     * @return the string
     */
    public String setVoteValue(final String newVotesName,
                               final String origVotesName,
                               final List<String> loopVars) {
        final String newVotesNameAcc = getFullVoteAccess(newVotesName, loopVars);
        final String origVotesNameAcc = getFullVoteAccess(origVotesName, loopVars);
        return varAssignCode(newVotesNameAcc, origVotesNameAcc) + CCodeHelper.SEMICOLON;
    }

    /**
     * Gets the full vote access.
     *
     * @param voteName
     *            the vote name
     * @param loopVars
     *            the loop vars
     * @return the full vote access
     */
    public String getFullVoteAccess(final String voteName,
                                    final List<String> loopVars) {
        return dotStructAccess(voteName,
                               UnifiedNameContainer.getStructValueName(),
                               loopVars);
    }

    /**
     * Checks for variable as min value.
     *
     * @return true, if successful
     */
    public abstract boolean hasVariableAsMinValue();

    /**
     * Checks for variable as max value.
     *
     * @return true, if successful
     */
    public abstract boolean hasVariableAsMaxValue();

    /**
     * Gets the minimal value.
     *
     * @return the minimal value a voter can assign
     */
    public abstract String getMinimalValue();

    /**
     * Gets the maximal value.
     *
     * @return the maximal value a voter can assign
     */
    public abstract String getMaximalValue();

    /**
     * Gets the sizes in order.
     *
     * @param amountVoters
     *            the amount voters
     * @param amountCandidates
     *            the amount candidates
     * @param amountSeats
     *            the amount seats
     * @return the sizes in order
     */
    public List<Integer> getSizesInOrder(final int amountVoters,
                                         final int amountCandidates,
                                         final int amountSeats,
                                         final int amountStacks) {
        return recGetSizesInOrder(amountVoters, amountCandidates, amountSeats,
                                  getSizeOfDimensionsAsList());
    }

    /**
     * Rec get sizes in order.
     *
     * @param amountVoters
     *            the amount voters
     * @param amountCandidates
     *            the amount candidates
     * @param amountSeats
     *            the amount seats
     * @param sizesOfDimensions
     *            the sizes of dimensions
     * @return the list
     */
    private List<Integer> recGetSizesInOrder(final int amountVoters,
                                             final int amountCandidates,
                                             final int amountSeats,
                                             final List<String> sizesOfDimensions) {
        final List<Integer> toReturn = new ArrayList<Integer>();
        if (sizesOfDimensions.size() == 0) {
            return toReturn;
        } else {
            final String sizeOfDim = sizesOfDimensions.get(0);
            if (sizeOfDim.contentEquals("V")) {
                toReturn.add(amountVoters);
            } else if (sizeOfDim.contentEquals("C")) {
                toReturn.add(amountCandidates);
            } else if (sizeOfDim.contentEquals("S")) {
                toReturn.add(amountSeats);
            }
            toReturn.addAll(
                    recGetSizesInOrder(amountVoters, amountCandidates,
                                       amountSeats,
                                       sizesOfDimensions.subList(
                                               1, sizesOfDimensions.size()
                                       )
                            )
            );
            return toReturn;
        }
    }

    /**
     * Restrict votes.
     *
     * @param voteName
     *            the vote name
     * @param code
     *            the code
     */
    public abstract void restrictVotes(String voteName,
                                       CodeArrayListBeautifier code);

    public abstract int vetAmountStacks(int amountStacks);
}
