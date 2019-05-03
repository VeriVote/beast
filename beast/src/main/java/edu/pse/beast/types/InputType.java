package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainers.ResultValueWrapper;

public abstract class InputType implements InOutType {
    protected CommonHelpMethods helper;

    public InputType() {
        getHelper();
    }

    public static List<InputType> getInputTypes() {
        ServiceLoader<InputType> loader = ServiceLoader.load(InputType.class);
        List<InputType> types = new ArrayList<InputType>();
        for (Iterator<InputType> iterator = loader.iterator(); iterator.hasNext();) {
            InputType type = (InputType) iterator.next();
            types.add(type);
        }
        return types;
    }

    @Override
    public final String toString() {
        return otherToString();
    }
    
    @Deprecated
    public final List<List<String>> getNewVotes(List<String> lastFailedRun, int index) {
        List<List<String>> toReturn = new ArrayList<List<String>>();
        
        toReturn.addAll(this.helper.extractVariable(
                "" + UnifiedNameContainer.getNewVotesName() + "", getDimension(), lastFailedRun)
                .get(index).getList());

        return toReturn;
    }
    
    public final List<List<String>> getVotingArray(List<String> lastFailedRun, int index) {
        return helper.extractVariable(UnifiedNameContainer.getVotingArray(), getDimension(),
                                      lastFailedRun).get(index).getList();
    }

    protected abstract void getHelper();

    /**
     * returns a String containing the shape of the input object e.g "[" +
     * UnifiedNameContainer.getVoter() + "]" for single choice
     *
     * @return the String with the input object
     */
    public abstract String getInputString();

    /**
     *
     * @return the ID used by this type in the String resources
     */
    public abstract String getInputIDinFile();

    /**
     *
     * @param container
     * @return the minimal value a voter can assign
     */
    public abstract String getMinimalValue();

    /**
     *
     * @param container
     * @return the maximal value a voter can assign
     */
    public abstract String getMaximalValue();

    /**
     *
     * @return true, if one voter can only vote for one candidate
     */
    public abstract boolean isVotingForOneCandidate();

    /**
     * adds the headers needed for the specified checker
     *
     * @param code the list to which the headers should be added to
     */
    public abstract void addCheckerSpecificHeaders(CodeArrayListBeautifier code);

    /**
     * adds the verify method to the code list
     *
     * @param code    the list with the previous code
     * @param outType the output type (whether we have a single output candidate or
     *                a struct)
     */
    public abstract void addVerifyMethod(CodeArrayListBeautifier code, OutputType outType);
    
    /**
     * extracts the voting data out of the given bounded model checker output into a wrapper object
     *
     * @param result           the result of the computation from which the values
     *                         will be extracted
     * @param numberCandidates the number of candidates
     * @return a wrapper which contains the values
     */
    public final ResultValueWrapper extractVotes(List<String> result, int numberCandidates) {
        return this.helper.extractVariable("" + UnifiedNameContainer.getNewVotesName() + "",
                                           getDimension(),
                                           result).get(0);
    }

    /**
     * vets a value to determine if it is legal for the input type, or not
     *
     * @param newValue       the new value
     * @param container      the type container
     * @param newRowOfValues the new row of values
     * @return the new value
     */
    public abstract String vetValue(String newValue, ElectionTypeContainer container,
                                    NEWRowOfValues newRowOfValues);

    public List<ResultValueWrapper> readVote(List<String> toExtract) {
        return this.helper.extractVariable(UnifiedNameContainer.getVotingArray(),
                                           getDimension(), toExtract);
    }


    public String[] wrongInputTypeArray(int amountCandidates, int amountVoters) {
        String[] toReturn = new String[amountCandidates];
        Arrays.fill(toReturn, "wrong input type");
        return toReturn;
    }

    public abstract String[] getVotePoints(String[][] votes, int amountCandidates,
                                           int amountVoters);

    public abstract String[] getVotePoints(String[] votes, int amountCandidates,
                                           int amountVoters);

    // public abstract void addMarginMainCheck(CodeArrayListBeautifier code, int
    // margin, List<String> origResult);

    public abstract List<String> getVotingResultCode(String[][] votingData);

    /**
     *
     * @return the dimension of the array which holds the votes (e.g 1 for single
     *         choice, 2 for approval)
     */
    public abstract int getDimension();

    /**
     * so far only used for preference voting
     *
     * @param code       the code
     * @param voteNumber the amount of votes
     */
    public abstract void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, int voteNumber);

    public abstract void addCodeForVoteSum(CodeArrayListBeautifier code, boolean unique);

    public abstract InternalTypeContainer getInternalTypeContainer();

    public abstract int vetAmountCandidates(int amountCandidates);

    public abstract int vetAmountVoters(int amountVoters);

    public abstract int vetAmountSeats(int amountSeats);

    public abstract int getNumVotingPoints(String[][] votingData);

    public abstract String getVoteDescriptionString(List<List<String>> origVotes);

    public abstract String getMaximalSize(int listDepth);

    public abstract boolean hasVariableAsMinValue();

    public abstract boolean hasVariableAsMaxValue();
}