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
            
            FailureExample exmp = createFailureExample();
            
            //System.out.println(exmp.getNumOfCandidates());
            
            for (int i = 0; i < exmp.getVotes().size(); i++) {
                System.out.println("_________");
               // System.out.println("voteslist " + exmp.getVotes().get(i).toString());
                
                Long[] test = exmp.getVotes().get(i);
               
                for (int j = 0; j < test.length; j++) {
                    System.out.println(test[j]);
                }
                
            }
            
         
            
      //      presenter.presentFailureExample(createFailureExample());
        }
    }

    public FailureExample createFailureExample() {

        List<Long> elect = readLongs("elect", getResult());

        switch (getElectionType()) {
        
        case APPROVAL:

            List<Long[][]> votesList = readTwoDimVar("votes", getResult());

            List<Long[]> seatsList = readOneDimVar("seats", getResult());

            return new FailureExample(getElectionType(), null, votesList, elect, seatsList,
                    getNumCandidates(), getNumSeats(), getNumVoters());
            
        case PREFERENCE:
            
            List<Long[]> singleVotesList = readOneDimVar("votes", getResult());

            return new FailureExample(getElectionType(), singleVotesList, null, elect, null,
                    getNumCandidates(), getNumSeats(), getNumVoters());

        case SINGLECHOICE:

            singleVotesList = readOneDimVar("votes", getResult());

            return new FailureExample(getElectionType(), singleVotesList, null, elect, null,
                    getNumCandidates(), getNumSeats(), getNumVoters());

        case WEIGHTEDAPPROVAL:

            votesList = readTwoDimVar("votes", getResult());

            seatsList = readOneDimVar("seats", getResult());

            return new FailureExample(getElectionType(), null, votesList, elect, seatsList,
                    getNumCandidates(), getNumSeats(), getNumVoters());
            
        default:
            ErrorLogger.log("This votingtype hasn't been implemented yet please do so in the class CBMC_Result");
            return null;
        }
    }

    private void addToLongList(List<Long> list, int indexToAddAt, long toAdd) {
        if (list.size() > indexToAddAt) {
            list.set(indexToAddAt, toAdd);
        } else {
            for (int i = list.size(); i < indexToAddAt; i++) {
                list.add(-1l);
            }
            list.add(toAdd);
        }
    }

    private void addToLongOfLongArrayList(List<ArrayList<ArrayList<Long>>> list, int mainIndex, int indexOne,
            int indexTwo, long toAdd) {
        if (list.size() > mainIndex) {
            addToDualArray(list.get(mainIndex), indexOne, indexTwo, toAdd);
        } else {
            for (int i = list.size(); i < mainIndex; i++) {
                list.add(new ArrayList<ArrayList<Long>>());
            }
            addToDualArray(list.get(mainIndex), indexOne, indexTwo, toAdd);
        }
    }

    private void addToDualArray(ArrayList<ArrayList<Long>> list, int indexOne, int indexTwo, long toAdd) {
        if (list.size() > indexOne) {
            addToLongList(list.get(indexOne), indexTwo, toAdd);
        } else {
            for (int i = list.size(); i < indexOne; i++) {
                list.add(new ArrayList<Long>());
            }
            addToLongList(list.get(indexOne - 1), indexTwo, toAdd);
        }
    }

    private List<Long[]> listsToSingleArrays(List<ArrayList<Long>> votesList) {
        List<Long[]> toReturn = new ArrayList<Long[]>();

        if (votesList != null) {
            for (int i = 0; i < votesList.size(); i++) {
                // here we have every array
                if (votesList.get(i) != null) {

                    Long[] toAdd = new Long[votesList.get(i).size()];

                    for (int j = 0; j < votesList.get(i).size(); j++) {
                        toAdd[j] = votesList.get(i).get(j);
                    }

                    toReturn.add(toAdd);
                } else {
                    toReturn.add(new Long[0]);
                }
            }
        }
        return toReturn;
    }

    private List<Long[][]> listsToDualArrays(List<ArrayList<ArrayList<Long>>> votesList) {
        List<Long[][]> toReturn = new ArrayList<Long[][]>();

        if (votesList != null) {
            for (int i = 0; i < votesList.size(); i++) {
                // here we have every array
                if (votesList.get(i) != null && votesList.get(i).get(0) != null) {

                    Long[][] toAdd = new Long[votesList.get(i).size()][votesList.get(i).get(0).size()];

                    for (int j = 0; j < votesList.get(i).size(); j++) {
                        for (int j2 = 0; j2 < votesList.get(i).get(j).size(); j2++) {
                            toAdd[j][j2] = votesList.get(i).get(j).get(j2);
                        }
                    }

                    toReturn.add(toAdd);
                } else {
                    toReturn.add(new Long[0][0]);
                }
            }
        }
        return toReturn;
    }
    
    private List<Long> readLongs(String name, List<String> toExtract) {

        List<Long> toReturn = new ArrayList<Long>();

        Pattern correctChecker   = Pattern.compile("(\\b" + name + "[0-9]+=[0-9]u\\b)(.*)");

        Pattern longExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\b)(.*)"); 
        
        for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher checkerMatcher = correctChecker.matcher(line);
            if (checkerMatcher.find()) {
                Matcher longMatcher = longExtractor.matcher(checkerMatcher.group(0));
                if(longMatcher.find()) {
                    
                    String longLine = longMatcher.group(1);
                    String number = longLine.replaceAll(("[^-?0-9]*"), "");
                    int electIndex = Integer.parseInt(number);

                    // split at the "(" and ")" to extract the bit value
                    String valueAsString = line.split("\\(")[1].split("\\)")[0];
                    // prase the binary value to a long
                    Long value = Long.parseLong(valueAsString, 2);
                    addToLongList(toReturn, electIndex, value);
                }
            }
        }
        
        return toReturn;
    }

    private List<Long[]> readOneDimVar(String name, List<String> toExtract) {
        System.out.println("call");
        ArrayList<ArrayList<Long>> list = new ArrayList<ArrayList<Long>>();

        // this pattern searches for words of the form
        // "votesNUMBER[NUMBER][NUMBER]" where "NUMBER" can by any positive
        // number
        Pattern votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\[[0-9]+\\])(.*)");

        for (Iterator<String> iterator = toExtract.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher votesMatcher = votesExtractor.matcher(line);

            if (votesMatcher.find()) {
                String newLine = votesMatcher.group(1);
                
                // find out the number of this votes array
                int mainIndex = Integer.parseInt(newLine.split("\\[")[0].split(name)[1]);
                
                // get the first index for this array value
                int index = Integer.parseInt(newLine.split("\\[")[1].split("\\]")[0]);
                
                // split at the "(" and ")" to extract the value
                String valueAsString = line.split("\\(")[1].split("\\)")[0];
                
                addToDualArray(list, mainIndex, index, Long.parseLong(valueAsString, 2));
                
            }
        }
        System.out.println("davor");
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                System.out.println(list.get(i).get(j));
            }
            System.out.println("==============");
        }
        System.out.println("danach");
        
        
        return listsToSingleArrays(list);
    }

    private List<Long[][]> readTwoDimVar(String name, List<String> toExtract) {
        List<ArrayList<ArrayList<Long>>> list = new ArrayList<ArrayList<ArrayList<Long>>>();

        // this pattern searches for words of the form
        // "votesNUMBER[NUMBER][NUMBER]" where "NUMBER" can by any positive
        // number
        Pattern votesExtractor = Pattern.compile("(\\b" + name + "[0-9]+\\[[0-9]+\\]\\[[0-9]+\\])(.*)");

        for (Iterator<String> iterator = toExtract.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            Matcher votesMatcher = votesExtractor.matcher(line);

            if (votesMatcher.find()) {
                
                String newLine = votesMatcher.group(1);

                // find out the number of this votes array
                int mainIndex = Integer.parseInt(newLine.split("\\[")[0].split(name)[1]);

                // get the first index for this array value
                int indexOne = Integer.parseInt(newLine.split("\\[")[1].split("\\]")[0]);

                // get the second index for this array value
                int indexTwo = Integer.parseInt(newLine.split("\\[")[2].split("\\]")[0]);

                // split at the "(" and ")" to extract the value
                String valueAsString = line.split("\\(")[1].split("\\)")[0];

                addToLongOfLongArrayList(list, mainIndex, indexOne, indexTwo, Long.parseLong(valueAsString, 2));

            }
        }
        return listsToDualArrays(list);
    }
}
