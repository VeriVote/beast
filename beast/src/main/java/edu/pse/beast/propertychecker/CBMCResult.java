package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.types.InternalTypeContainer;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class CBMCResult extends Result {
    private static final String SEGMENT_END = "-----------------------------------";

    // this is the last line in the cbmc output, if the verification was
    // successful
    private static final String SUCCESS_LINE = "VERIFICATION SUCCESSFUL";

    // this is the last line in the cbmc output, if the assertion
    // failed
    private static final String FAILURE_LINE = "VERIFICATION FAILED";

    private boolean createsExample = true;

    public CBMCResult() {
        // empty constructor
    }

    public CBMCResult(boolean createsExample) {
        this.createsExample = createsExample;
    }

    @Override
    public void setResult(List<String> result) {
        super.setResult(result);
        if (createsExample) {
            failureExample = createFailureExample();
        }
    }

    /**
     * this method creates a failure example from the given output from cbmc
     *
     * @return a failure example that show how the voters voted and who won then
     */
    private FailureExample createFailureExample() {
        // determine the elect values
        if (!isMarginComp()) {
            FailureExample toReturn = null;

            if (getResult() != null && getElectionDescription() != null) {
                // define these arrays, because switch case does not let me reassign
                // the
                // same name,
                // and i am a bit worried, that they will not get created properly;
                List<CBMCResultWrapperMultiArray> votesList = getElectionDescription().getContainer().getInputType()
                        .readVoteList(getResult());
                List<CBMCResultWrapperSingleArray> singleVotesList = getElectionDescription().getContainer()
                        .getInputType().readSingleVoteList(getResult());

                // this list can be empty, if no voting for seats took place
                List<CBMCResultWrapperSingleArray> seatsList = getElectionDescription().getContainer().getOutputType()
                        .readSeatList(getResult());
                List<CBMCResultWrapperLong> elect = getElectionDescription().getContainer().getOutputType()
                        .readElect(getResult());

                toReturn = new FailureExample(getElectionDescription(), singleVotesList, votesList, elect, seatsList,
                        getNumCandidates(), getNumSeats(), getNumVoters());
//        // it is voting for seats, and not for candidates
//
//        if (getElectionDescription().getContainer().getOutputType().isOutputOneCandidate()) {
//
////          switch (getElectionDescription().getInputType().getInputID()) {
////
////          // get the fitting type and extract the values out of it,
////          // because we
////          // know the format of the values
////          // for each specific type
////          case APPROVAL:
////
////            votesList = readTwoDimVar("votes", getResult());
////
////            // read all the variables with form electN[] that are
////            // arrays, to
////            // extract the seats that
////            // got chosen
////            seatsList = readOneDimVar("elect", getResult());
////
////            toReturn = new FailureExample(getElectionDescription(), null, votesList, null, seatsList,
////                getNumCandidates(), getNumSeats(), getNumVoters());
////            break;
////          case PREFERENCE:
////
////            votesList = readTwoDimVar("votes", getResult());
////
////            // read all the variables with form electN[] that are
////            // arrays, to
////            // extract the seats that
////            // got chosen
////            seatsList = readOneDimVar("elect", getResult());
////
////            toReturn = new FailureExample(getElectionDescription(), null, votesList, null, seatsList,
////                getNumCandidates(), getNumSeats(), getNumVoters());
////            break;
////          case SINGLE_CHOICE:
////
////            singleVotesList = readOneDimVar("votes", getResult());
////
////            // read all the variables with form electN[] that are
////            // arrays, to
////            // extract the seats that
////            // got chosen
////            seatsList = readOneDimVar("elect", getResult());
////
////            toReturn = new FailureExample(getElectionDescription(), singleVotesList, null, null, seatsList,
////                getNumCandidates(), getNumSeats(), getNumVoters());
////            break;
////          case WEIGHTED_APPROVAL:
////
////            votesList = readTwoDimVar("votes", getResult());
////
////            // read all the variables with form electN[] that are
////            // arrays, to
////            // extract the seats that
////            // got chosen
////            seatsList = readOneDimVar("elect", getResult());
////
////            toReturn = new FailureExample(getElectionDescription(), null, votesList, null, seatsList,
////                getNumCandidates(), getNumSeats(), getNumVoters());
////            break;
////          default:
////            ErrorForUserDisplayer.displayError(
////                "This voting type you are using has not been implemented yet to be displayed. "
////                    + "Please do so in the class CBMC_Result");
////            this.setError(
////                "This voting type has not been implemented yet please do so in the class CBMC_Result");
////            return null;
////          }
//
//        } else {
//          // read the elect value, because here we do not work with seats
//          List<CBMCResultWrapperLong> elect = readLongs("elect", getResult());
//
//          switch (getElectionDescription().getInputType().getInputID()) {
//
//          // get the fitting type and extract the values out of it,
//          // because we
//          // know the format of the values
//          // for each specific type
//          case APPROVAL:
//
//            votesList = readTwoDimVar("votes", getResult());
//
//            toReturn = new FailureExample(getElectionDescription(), null, votesList, elect, null,
//                getNumCandidates(), getNumSeats(), getNumVoters());
//            break;
//          case PREFERENCE:
//
//            votesList = readTwoDimVar("votes", getResult());
//
//            toReturn = new FailureExample(getElectionDescription(), null, votesList, elect, null,
//                getNumCandidates(), getNumSeats(), getNumVoters());
//            break;
//          case SINGLE_CHOICE:
//
//            singleVotesList = readOneDimVar("votes", getResult());
//
//            toReturn = new FailureExample(getElectionDescription(), singleVotesList, null, elect, null,
//                getNumCandidates(), getNumSeats(), getNumVoters());
//            break;
//          case WEIGHTED_APPROVAL:
//
//            votesList = readTwoDimVar("votes", getResult());
//
//            toReturn = new FailureExample(getElectionDescription(), null, votesList, elect, null,
//                getNumCandidates(), getNumSeats(), getNumVoters());
//            break;
//          default:
//            ErrorForUserDisplayer.displayError(
//                "This voting type you are using has not been implemented yet to be displayed. "
//                    + "Please do so in the class CBMC_Result");
//            this.setError(
//                "This voting type has not been implemented yet please do so in the class CBMC_Result");
//            return null;
//
//          }
//
////        }
                // determine the values for the symbolic variables
                // that the user set
                // get ALL symbolic variables
                List<SymbolicVariable> symbolicVariableList = super.getPropertyDesctiption()
                        .getSymbolicVariablesCloned();
                // iterate through them
                for (Iterator<SymbolicVariable> iterator = symbolicVariableList.iterator(); iterator.hasNext();) {
                    SymbolicVariable symbolicVariable = (SymbolicVariable) iterator.next();
                    InternalTypeContainer internalType = symbolicVariable.getInternalTypeContainer();
                    String name = symbolicVariable.getId();
                    if (!internalType.isList()) {
                        // extract the value of "name" in the result
                        // if it is null, the variable could not be found
                        Long extracted = readSymbolicVariable(name, getResult());
                        if (extracted != null) {
                            long number = (long) extracted;
                            switch (internalType.getInternalType()) {
                            case VOTER:
                                toReturn.addSymbolicVoters(name, number);
                                break;
                            case CANDIDATE:
                                toReturn.addSymbolicCandidate(name, number);
                                break;
                            case SEAT:
                                toReturn.addSymbolicSeat(name, number);
                                break;
                            default:
                                // do nothing
                            }
                        }
                    }
                }
                return toReturn;
            } else {
                this.setError(
                        "No input could be read from the Checker, "
                        + "please make sure that it is there and working properly");
                return null;
            }
        } else { // we have a margin computation and have to create the example object for this
            FailureExample toReturn =
                    new FailureExample(getElectionDescription(), null, null, null, null, 0, 0, 0);
            toReturn.setHasFinalMargin(hasFinalMargin());
            if (hasFinalMargin()) {
                toReturn.setFinalMargin(getFinalMargin());
            }
            toReturn.setOrigVoting(origVoting);
            toReturn.setOrigWinner(origWinner);
            toReturn.setNewVotes(newVotes);
            toReturn.setNewWinner(super.newWinner);
            return toReturn;
        }
    }

    protected Long readSymbolicVariable(String name, List<String> toExtract) {
        Long toReturn = null;
        Pattern correctChecker = Pattern.compile("(\\b" + name + "=[0-9]+u*)(.*)");
        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, SEGMENT_END);
        while (line.length() > 0) {
            Matcher checkerMatcher = correctChecker.matcher(line);
            if (checkerMatcher.find()) {
                // split at the "(" and ")" to extract the bit value
                String valueAsString = line.split("\\(")[1].split("\\)")[0];
                // parse the binary value to a long
                toReturn = Long.parseLong(valueAsString, 2);
            }
            line = mergeLinesToOne(iterator, SEGMENT_END);
        }
        return toReturn;
    }

    /**
     * extracts a long variable out of the output from cbmc
     *
     * @param name      the name of the variable
     * @param toExtract all the lines that should get checked, it they contain the
     *                  searched var
     * @return a list of all occurrences
     */
    protected List<CBMCResultWrapperLong> readLongs(String name, List<String> toExtract) {
        List<CBMCResultWrapperLong> toReturn = new ArrayList<CBMCResultWrapperLong>();
        Pattern correctChecker = Pattern.compile("(\\b" + name + "[0-9]+=[0-9]+u*)(.*)");
        Pattern longExtractor = Pattern.compile("(\\b" + name + "[0-9]+)(.*)");
        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, SEGMENT_END);
        line = mergeLinesToOne(iterator, SEGMENT_END);
        while (line.length() > 0) {
            Matcher checkerMatcher = correctChecker.matcher(line);
            if (checkerMatcher.find()) {
                Matcher longMatcher = longExtractor.matcher(checkerMatcher.group(0));
                if (longMatcher.find()) {
                    String longLine = longMatcher.group(1);
                    // replace all no number characters
                    String number = longLine.replaceAll(("[^-?0-9]*"), "");
                    int electIndex = Integer.parseInt(number);
                    // split at the "(" and ")" to extract the bit value
                    String valueAsString = line.split("\\(")[1].split("\\)")[0];
                    // parse the binary value to a long
                    String value = "" + Long.parseLong(valueAsString, 2);
                    boolean added = false;
                    for (Iterator<CBMCResultWrapperLong> innerIterator = toReturn.iterator(); innerIterator
                            .hasNext();) {
                        CBMCResultWrapperLong wrapper = (CBMCResultWrapperLong) innerIterator.next();
                        if (wrapper.getMainIndex() == electIndex) {
                            wrapper.setValue(value);
                            added = true;
                        }
                    }
                    if (!added) {
                        toReturn.add(new CBMCResultWrapperLong(electIndex, name));
                        toReturn.get(toReturn.size() - 1).setValue(value);
                    }
                }
            }
            line = mergeLinesToOne(iterator, SEGMENT_END);
        }
        return toReturn;
    }

    /**
     * this method is used to extract
     *
     * @param name      the name of the saved variable
     * @param toExtract the string list to extract the variable out of
     * @return a list of all variables with a matching name with their index and
     *         values that occurred in the give list
     */
    protected List<CBMCResultWrapperSingleArray> readOneDimVar(String name, List<String> toExtract) {
        List<CBMCResultWrapperSingleArray> list = new ArrayList<CBMCResultWrapperSingleArray>();
        // this pattern searches for words of the form
        // "votesNUMBER[NUMBER]" where "NUMBER" can by any positive
        // number. Also, the next character has to be an equals sign
        Pattern votesExtractor = null;
        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, SEGMENT_END);
        while (line.length() > 0) {
            if (line.contains("[")) {
                // pattern that checks for a pattern like
                // "votesNUMBER[NUMBER(letters)] = ...."
                votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\[[0-9]+[a-zA-Z]*\\])(=.*)");
                Matcher votesMatcher = votesExtractor.matcher(line);
                if (votesMatcher.find()) {
                    String newLine = votesMatcher.group(1);
                    // find out the number of this votes array
                    int mainIndex = Integer.parseInt(newLine.split("=")[0].split(name)[1].split("\\[")[0]);
                    // get the first index for this array value
                    int arrayIndex = Integer
                            .parseInt((newLine.split("\\[")[1].split("\\]")[0]).replaceAll("[^\\d.]", ""));
                    // split at the "(" and ")" to extract the value
                    String valueAsString = line.split("\\(")[1].split("\\)")[0];
                    String value = "" + Long.parseLong(valueAsString, 2);
                    boolean added = false;
                    for (Iterator<CBMCResultWrapperSingleArray> innerIterator = list.iterator(); innerIterator
                            .hasNext();) {
                        CBMCResultWrapperSingleArray wrapper = (CBMCResultWrapperSingleArray) innerIterator.next();
                        if (wrapper.getMainIndex() == mainIndex) {
                            wrapper.addTo(arrayIndex, value);
                            added = true;
                        }
                    }
                    if (!added) {
                        list.add(new CBMCResultWrapperSingleArray(mainIndex, name));
                        list.get(list.size() - 1).addTo(arrayIndex, value);
                    }
                }
            } else if (line.contains("{")) {
                // pattern that checks for a pattern like "votesNUMBER = {..."
                votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+)=(\\{[^\\{|\\}]*\\})");
                Matcher votesMatcher = votesExtractor.matcher(line);
                if (votesMatcher.find()) {
                    String newLine = votesMatcher.group(1);
                    // find out the number of this votes array
                    int mainIndex = Integer.parseInt(newLine.split("=")[0].split(name)[1]);
                    String values = line.split("\\(")[1].split("\\)")[0];
                    // strip away white spaces and the double braces that
                    // represent
                    // the whole array
                    // also remove all opening braces
                    values = values.replaceAll(" +", "").replaceAll("\\{+", "").replace("}", "}");
                    String[] subValueArray = values.split("\\}")[0].split(",");
                    for (int i = 0; i < subValueArray.length; i++) {
                        if (!subValueArray[i].equals("")) {
                            boolean added = false;
                            for (Iterator<CBMCResultWrapperSingleArray> innerIterator = list.iterator(); innerIterator
                                    .hasNext();) {
                                CBMCResultWrapperSingleArray wrapper = (CBMCResultWrapperSingleArray) innerIterator
                                        .next();
                                if (wrapper.getMainIndex() == mainIndex) {
                                    wrapper.addTo(i, "" + Long.parseLong(subValueArray[i], 2));
                                    added = true;
                                }
                            }
                            if (!added) {
                                list.add(new CBMCResultWrapperSingleArray(mainIndex, name));
                                list.get(list.size() - 1).addTo(i, "" + Long.parseLong(subValueArray[i], 2));
                            }
                        }
                    }
                }
            }
            line = mergeLinesToOne(iterator, SEGMENT_END);
        }
        return list;
    }

    /**
     * reads a two-dimensional variables that matches a given name from the cbmc
     * output and puts it in a wrapper object
     *
     * @param name      the name of the variables to search for
     * @param toExtract the list to extract the variables out
     * @return the finished list with all variables stored in
     */
    protected List<CBMCResultWrapperMultiArray> readTwoDimVar(String name, List<String> toExtract) {
        List<CBMCResultWrapperMultiArray> list = new ArrayList<CBMCResultWrapperMultiArray>();
        Pattern votesExtractor = null;
        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, SEGMENT_END);
        while (line.length() > 0) {
            if (line.contains("[")) {
                // this pattern searches for words of the form
                // "votesNUMBER[NUMBER][NUMBER]" where "NUMBER" can by any
                // positive
                // number. Also, the next character has to be an equals sign
                votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\[[0-9]+[a-z]*\\]\\[[0-9]+[a-zA-z]*\\])(=.*)");
                Matcher votesMatcher = votesExtractor.matcher(line);
                if (votesMatcher.find()) {
                    String newLine = votesMatcher.group(1);
                    // find out the number of this votes array
                    int mainIndex = Integer.parseInt(newLine.split("=")[0].split(name)[1].split("\\[")[0]);
                    // get the first index for this array value
                    int arrayIndexOne = Integer
                            .parseInt(newLine.split("\\[")[1].split("\\]")[0].replaceAll("[^\\d.]", ""));
                    // get the second index for this array value
                    int arrayIndexTwo = Integer
                            .parseInt(newLine.split("\\[")[2].split("\\]")[0].replaceAll("[^\\d.]", ""));
                    // split at the "(" and ")" to extract the value
                    String valueAsString = line.split("\\(")[1].split("\\)")[0];
                    String value = "" + Long.parseLong(valueAsString, 2);
                    boolean added = false;
                    for (Iterator<CBMCResultWrapperMultiArray> innerIterator = list.iterator(); innerIterator
                            .hasNext();) {
                        CBMCResultWrapperMultiArray wrapper = (CBMCResultWrapperMultiArray) innerIterator.next();
                        if (wrapper.getMainIndex() == mainIndex) {
                            wrapper.addTo(arrayIndexOne, arrayIndexTwo, value);
                            added = true;
                        }
                    }
                    if (!added) {
                        list.add(new CBMCResultWrapperMultiArray(mainIndex, name));
                        list.get(list.size() - 1).addTo(arrayIndexOne, arrayIndexTwo, value);
                    }
                }
            } else if (line.contains("{")) {
                // searches for votesNUMBER={....}
                votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+)=(\\{\\s*((\\{(.*)\\}(,)*\\s*)*)})");
                Matcher votesMatcher = votesExtractor.matcher(line);
                if (votesMatcher.find()) {
                    String newLine = votesMatcher.group(1);
                    // find out the number of this votes array
                    int mainIndex = Integer.parseInt(newLine.split("=")[0].split(name)[1]);
                    String values = line.split("\\(")[1].split("\\)")[0];
                    // strip away white spaces and the double braces that
                    // represent
                    // the whole array
                    // also remove all opening braces
                    values = values.replaceAll(" +", "").replaceAll("\\{+", "").replaceAll("} *}+", "");
                    // every sub array is now seperated by these two characters
                    String[] subArrys = values.split("\\},");
                    for (int i = 0; i < subArrys.length; i++) {
                        String subValues[] = subArrys[i].split(",");
                        for (int j = 0; j < subValues.length; j++) {
                            if (!subValues[j].equals("")) {
                                boolean added = false;
                                for (Iterator<CBMCResultWrapperMultiArray> innerIterator = list
                                        .iterator(); innerIterator.hasNext();) {
                                    CBMCResultWrapperMultiArray wrapper = (CBMCResultWrapperMultiArray) innerIterator
                                            .next();
                                    if (wrapper.getMainIndex() == mainIndex) {
                                        wrapper.addTo(i, j, "" + Long.parseLong(subValues[j], 2));
                                        added = true;
                                    }
                                }
                                if (!added) {
                                    list.add(new CBMCResultWrapperMultiArray(mainIndex, name));
                                    list.get(list.size() - 1).addTo(i, j, "" + Long.parseLong(subValues[j], 2));
                                }
                            }
                        }
                    }
                }
            }
            line = mergeLinesToOne(iterator, SEGMENT_END);
        }
        return list;
    }

    /**
     *
     * @param toMerge
     * @param regexToEndAt
     * @return
     */
    protected String mergeLinesToOne(Iterator<String> toMerge, String regexToEndAt) {
        String toReturn = "";
        boolean notEnded = true;
        while (notEnded) {
            if (toMerge.hasNext()) {
                String nextLine = toMerge.next();
                // add the next line, separated by a whitespace
                toReturn = toReturn + " " + nextLine;
                if (nextLine.contains(regexToEndAt)) {
                    // we found the end of the segment
                    notEnded = false;
                }
            } else {
                return toReturn;
            }
        }
        return toReturn;
    }

    public FailureExample getFailureExample() {
        return failureExample;
    }

    @Override
    public boolean checkAssertionSuccess() {
        if (super.getResult() != null && super.getResult().size() > 0) {
            return super.getResult().get(super.getResult().size() - 1).contains(SUCCESS_LINE);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAssertionFailure() {
        if (super.getResult() != null && super.getResult().size() > 0) {
            return super.getResult().get(super.getResult().size() - 1).contains(FAILURE_LINE);
        } else {
            return false;
        }
    }
}