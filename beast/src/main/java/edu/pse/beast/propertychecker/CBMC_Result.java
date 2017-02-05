package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 * 
 * @author Lukas
 *
 */
public class CBMC_Result extends Result {

    private FailureExample failureExample = null;

    @Override
    public void presentTo(ResultPresenterElement presenter) {
        if (!isFinished()) {
            ErrorLogger.log("Result isn't ready yet");
            return;
        } else if (isTimedOut()) {
            presenter.presentTimeOut();
        } else if (!isValid()) {
            presenter.presentFailure(getError());
        } else if (isSuccess()) {
            presenter.presentSuccess();
        } else {
            System.out.println("test");
     //       presenter.presentFailureExample(failureExample);

            boolean debug = true;

            if (debug) {

                long time = System.currentTimeMillis();

                FailureExample exmp = failureExample;

                // System.out.println(exmp.getNumOfCandidates());

                System.out.println("get elect 1: " + exmp.getElect().get(0).getValue());
                System.out.println("get elect 2: " + exmp.getElect().get(1).getValue());
                for (int i = 0; i < exmp.getVoteList().size(); i++) {
                    // System.out.println("_________");
                    // System.out.println("voteslist " +
                    // exmp.getVotes().get(i).toString());

                    Long[][] test = exmp.getVoteList().get(i).getArray();

                    System.out.println(exmp.getVoteList().get(i).getName() + exmp.getVoteList().get(i).getMainIndex());
                    System.out.println("========");
                    for (int j = 0; j < test.length; j++) {
                        for (int j2 = 0; j2 < test[j].length; j2++) {
                            System.out.print(test[j][j2] + " | ");
                        }
                        System.out.println("");
                        System.out.println("_______________________");
                    }

                }
                long test2 = exmp.getElect().get(0).getValue();

                // System.out.println(test2);

                // presenter.presentFailureExample(createFailureExample());

                System.out.println("total time: " + (System.currentTimeMillis() - time));
            }
        }
    }

    @Override
    public void setResult(List<String> result) {
        super.setResult(result);
        failureExample = createFailureExample();
        
        System.out.println("test");
    }

    public FailureExample createFailureExample() {

        // datermine the elect values
        List<CBMC_Result_Wrapper_long> elect = readLongs("elect", getResult());

        System.out.println("asdf");
        
        
        // define these arrays, because switch case doesn't let me reassign the
        // same name,
        // and i am a bit worried, that they won't get created properly;
        List<CBMC_Result_Wrapper_multiArray> votesList;
        List<CBMC_Result_Wrapper_singleArray> seatsList;
        List<CBMC_Result_Wrapper_singleArray> singleVotesList;

        ErrorLogger.log("(CBMC_Result):  possible optimization: try to only use one loop");

        switch (getElectionType()) {

        case APPROVAL:

            votesList = readTwoDimVar("votes", getResult());

            seatsList = readOneDimVar("seats", getResult());

            return new FailureExample(getElectionType(), null, votesList, elect, seatsList, getNumCandidates(),
                    getNumSeats(), getNumVoters());

        case PREFERENCE:

            singleVotesList = readOneDimVar("votes", getResult());

            return new FailureExample(getElectionType(), singleVotesList, null, elect, null, getNumCandidates(),
                    getNumSeats(), getNumVoters());

        case SINGLECHOICE:

            singleVotesList = readOneDimVar("votes", getResult());

            return new FailureExample(getElectionType(), singleVotesList, null, elect, null, getNumCandidates(),
                    getNumSeats(), getNumVoters());

        case WEIGHTEDAPPROVAL:

            votesList = readTwoDimVar("votes", getResult());

            seatsList = readOneDimVar("seats", getResult());

            return new FailureExample(getElectionType(), null, votesList, elect, seatsList, getNumCandidates(),
                    getNumSeats(), getNumVoters());

        default:
            ErrorLogger.log("This votingtype hasn't been implemented yet please do so in the class CBMC_Result");
            return null;
        }
    }

    private List<CBMC_Result_Wrapper_long> readLongs(String name, List<String> toExtract) {

        List<CBMC_Result_Wrapper_long> toReturn = new ArrayList<CBMC_Result_Wrapper_long>();

        Pattern correctChecker = Pattern.compile("(\\b" + name + "[0-9]+=[0-9]+u)(.*)");

        Pattern longExtractor = Pattern.compile("(\\b" + name + "[0-9]+)(.*)");

        for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

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
                    // prase the binary value to a long
                    Long value = Long.parseLong(valueAsString, 2);

                    boolean added = false;

                    for (Iterator<CBMC_Result_Wrapper_long> innerIterator = toReturn.iterator(); innerIterator
                            .hasNext();) {
                        CBMC_Result_Wrapper_long wrapper = (CBMC_Result_Wrapper_long) innerIterator.next();
                        if (wrapper.getMainIndex() == electIndex) {
                            wrapper.setValue(value);
                            added = true;
                        }
                    }

                    if (!added) {
                        toReturn.add(new CBMC_Result_Wrapper_long(electIndex, name));
                        toReturn.get(toReturn.size() - 1).setValue(value);
                    }
                }
            }
        }
        return toReturn;
    }

    private List<CBMC_Result_Wrapper_singleArray> readOneDimVar(String name, List<String> toExtract) {

        List<CBMC_Result_Wrapper_singleArray> list = new ArrayList<CBMC_Result_Wrapper_singleArray>();

        // this pattern searches for words of the form
        // "votesNUMBER[NUMBER]" where "NUMBER" can by any positive
        // number. Also, the next character has to be an equals sign
        Pattern votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\[[0-9]+\\])(=.*)");

        for (Iterator<String> iterator = toExtract.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher votesMatcher = votesExtractor.matcher(line);

            if (votesMatcher.find()) {
                String newLine = votesMatcher.group(1);

                // find out the number of this votes array
                int mainIndex = Integer.parseInt(newLine.split("\\[")[0].split(name)[1]);

                // get the first index for this array value
                int arrayIndex = Integer.parseInt(newLine.split("\\[")[1].split("\\]")[0]);

                // split at the "(" and ")" to extract the value
                String valueAsString = line.split("\\(")[1].split("\\)")[0];

                long value = Long.parseLong(valueAsString, 2);

                boolean added = false;

                for (Iterator<CBMC_Result_Wrapper_singleArray> innerIterator = list.iterator(); innerIterator
                        .hasNext();) {
                    CBMC_Result_Wrapper_singleArray wrapper = (CBMC_Result_Wrapper_singleArray) innerIterator.next();

                    if (wrapper.getMainIndex() == mainIndex) {
                        wrapper.addTo(arrayIndex, value);
                        added = true;
                    }
                }

                if (!added) {
                    list.add(new CBMC_Result_Wrapper_singleArray(mainIndex, name));
                    list.get(list.size() - 1).addTo(arrayIndex, value);
                }

            }
        }
        return list;
    }

    private List<CBMC_Result_Wrapper_multiArray> readTwoDimVar(String name, List<String> toExtract) {

        List<CBMC_Result_Wrapper_multiArray> list = new ArrayList<CBMC_Result_Wrapper_multiArray>();

        // this pattern searches for words of the form
        // "votesNUMBER[NUMBER][NUMBER]" where "NUMBER" can by any positive
        // number. Also, the next character has to be an equals sign
        Pattern votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\[[0-9]+\\]\\[[0-9]+\\])(=.*)");

        for (Iterator<String> iterator = toExtract.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher votesMatcher = votesExtractor.matcher(line);

            if (votesMatcher.find()) {

                String newLine = votesMatcher.group(1);

//                System.out.println("NEWLINE: " + newLine);

                // find out the number of this votes array
                int mainIndex = Integer.parseInt(newLine.split("\\[")[0].split(name)[1]);

  //              System.out.println("mainindex " + mainIndex);

                // get the first index for this array value
                int arrayIndexOne = Integer.parseInt(newLine.split("\\[")[1].split("\\]")[0]);

                // get the second index for this array value
                int arrayIndexTwo = Integer.parseInt(newLine.split("\\[")[2].split("\\]")[0]);

                // split at the "(" and ")" to extract the value
                String valueAsString = line.split("\\(")[1].split("\\)")[0];

                long value = Long.parseLong(valueAsString, 2);

    //            System.out.println("value: " + value);
     //           System.out.println(line);
                boolean added = false;

                for (Iterator<CBMC_Result_Wrapper_multiArray> innerIterator = list.iterator(); innerIterator
                        .hasNext();) {
                    CBMC_Result_Wrapper_multiArray wrapper = (CBMC_Result_Wrapper_multiArray) innerIterator.next();

                    if (wrapper.getMainIndex() == mainIndex) {
                        wrapper.addTo(arrayIndexOne, arrayIndexTwo, value);
                        added = true;
                    }
                }

                if (!added) {
                    list.add(new CBMC_Result_Wrapper_multiArray(mainIndex, name));
                    list.get(list.size() - 1).addTo(arrayIndexOne, arrayIndexTwo, value);
                }
            }
        }
        return list;
    }
}
