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
            presenter.presentFailureExample(createFailureExample());
        }
    }

    public FailureExample createFailureExample() {
        
        List<Long> elect = new ArrayList<Long>();
        
        Pattern electExtractor = Pattern.compile("(\\belect[0-9]+\\b)(.*)");
        
        for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();
            
            Matcher electMatcher = electExtractor.matcher(line);

            if (electMatcher.find()) {
                if (electMatcher.groupCount() > 0) {
                    
                    String electLine = electMatcher.group(1);
                    String number = electLine.replaceAll(("[^-?0-9]*"), "");
                    int electIndex = Integer.parseInt(number);

                    //split at the "(" and ")" to extract the bit value
                    String valueAsString = line.split("\\(")[1].split("\\)")[0];
                    //prase the binary value to a long
                    Long value = Long.parseLong(valueAsString, 2);
                    addToLongList(elect, electIndex, value);
                    
                }
            }
        }
        
        
        
        switch (getElectionType()) {
        case APPROVAL:
            
            List<Long[][]> votesList = new ArrayList<Long[][]>();
            
            //this pattern searches for words of the form "votes*NUMBER*" where "NUMBER" can by any positive number
            Pattern votesExtractor = Pattern.compile("(\\bvotes[0-9]+\\[[0-9]+\\]\\[[0-9]+\\])(.*)");

            for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
                String line = (String) iterator.next();

                Matcher votesMatcher = votesExtractor.matcher(line);

                if (votesMatcher.find()) {
                    if (votesMatcher.groupCount() > 0) {
                        String newLine = votesMatcher.group(1);
                        String number = newLine.replaceAll(("[^-?0-9]*"), "");

                        int votesIndex = Integer.parseInt(number);

                        //split at the "(" and ")" to extract the array values
                        String valueAsString = line.split("\\(")[1].split("\\)")[0];
                        
                        
                        //the votes arrays are 2 dimensional, so we first get all of them out
                        String valuesAsString = valueAsString.split("\\{")[1].split("\\}")[0];
                        
                        
                        
                        
                        //remove all whitespaces
                        valuesAsString = valuesAsString.replaceAll(" +", "");
                        
                        String[] valuesAsStringArray = valuesAsString.split(",");
                        
                        Long[] values = new Long[valuesAsStringArray.length];
                        
                        for (int i = 0; i < values.length; i++) {
                            //parse the value from a binary number to a long
                            values[i] = Long.parseLong(valuesAsStringArray[i], 2);
                        }
                          
          //              addToLongOfLongArrayList(votesList, votesIndex, values);
                        
                    }
                }
            }
            
            
            
            
            
            

     //       return new FailureExample(getElectionType(), null, voteList, elect, getNumCandidates(), getNumSeats(),
     //               getNumVoters());
            break;

        case PREFERENCE:
            for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
                String line = (String) iterator.next();

            }
 //           return new FailureExample(getElectionType(), null, voteList, elect, getNumCandidates(), getNumSeats(),
  //                  getNumVoters());
            break;

        case SINGLECHOICE:
            
 //           List<Long[]> votesList = new ArrayList<Long[]>();
           
            votesExtractor = Pattern.compile("(\\bvotes[0-9]*\\b)(.*)");

            for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
                String line = (String) iterator.next();

                Matcher votesMatcher = votesExtractor.matcher(line);

                if (votesMatcher.find()) {
                    if (votesMatcher.groupCount() > 0) {
                        String newLine = votesMatcher.group(1);
                        String number = newLine.replaceAll(("[^-?0-9]*"), "");

                        int votesIndex = Integer.parseInt(number);

                        //split at the "(" and ")" to extract the array values
                        String valueAsString = line.split("\\(")[1].split("\\)")[0];
                        
                        
                        //the votes arrays are 1 dimensional, so we just have to search between the "{" and "}"
                        String valuesAsString = valueAsString.split("\\{")[1].split("\\}")[0];
                        
                        //remove all whitespaces
                        valuesAsString = valuesAsString.replaceAll(" +", "");
                        
                        String[] valuesAsStringArray = valuesAsString.split(",");
                        
                        Long[] values = new Long[valuesAsStringArray.length];
                        
                        for (int i = 0; i < values.length; i++) {
                            //parse the value from a binary number to a long
                            values[i] = Long.parseLong(valuesAsStringArray[i], 2);
                        }
                          
  //                     addToLongArrayList(votesList, votesIndex, values);
                        
                    }
                }
            }
            
  //          return new FailureExample(getElectionType(), votesList, null, elect, null, getNumCandidates(), getNumSeats(), getNumVoters());
            
        case WEIGHTEDAPPROVAL:
            for (Iterator<String> iterator = getResult().iterator(); iterator.hasNext();) {
                String line = (String) iterator.next();

            }
    //        return new FailureExample(getElectionType(), null, voteList, elect, getNumCandidates(), getNumSeats(),
   //                 getNumVoters());
            break;
        default:
            ErrorLogger.log("This votingtype hasn't been implemented yet please do so in the class CBMC_Result");
            return null;
        }
        
        return null;
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
    
    private void addToLongArrayList(List<Long[]> list, int indexToAddAt, Long[] toAdd) {
        if (list.size() > indexToAddAt) {
            list.set(indexToAddAt, toAdd);
        } else {
            for (int i = list.size(); i < indexToAddAt; i++) {
                list.add(new Long[0]);
            }
            list.add(toAdd);
        }
    }
    
    private void addToLongOfLongArrayList(List<Long[][]> list, int indexToAddAt, Long[][] toAdd) {
        if (list.size() > indexToAddAt) {
            list.set(indexToAddAt, toAdd);
        } else {
            for (int i = list.size(); i < indexToAddAt; i++) {
                list.add(new Long[0][0]);
            }
            list.add(toAdd);
        }
    }
}
