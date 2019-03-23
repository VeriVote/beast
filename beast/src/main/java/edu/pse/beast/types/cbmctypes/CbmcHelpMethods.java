package edu.pse.beast.types.cbmctypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.types.CommonHelpMethods;

public class CbmcHelpMethods extends CommonHelpMethods {

    private final String segmentEnder = "-----------------------------------";

//	// this is the last line in the cbmc output, if the verification was
//	// successful
//	private final String SUCCESSLINE = "VERIFICATION SUCCESSFUL";
//
//	// this is the last line in the cbmc output, if the assertion
//	// failed
//	private final String FAILURELINE = "VERIFICATION FAILED";

    @Override
    public Long readSymbolicVariable(String name, List<String> toExtract) {
        Long toReturn = null;

        Pattern correctChecker = Pattern.compile("(\\b" + name + "=[0-9]+u*)(.*)");

        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, segmentEnder);

        while (line.length() > 0) {
            Matcher checkerMatcher = correctChecker.matcher(line);
            if (checkerMatcher.find()) {
                // split at the "(" and ")" to extract the bit value
                String valueAsString = line.split("\\(")[1].split("\\)")[0];
                // parse the binary value to a long
                toReturn = Long.parseLong(valueAsString, 2);
            }
            line = mergeLinesToOne(iterator, segmentEnder);
        }

        return toReturn;
    }

    @Override
    public List<CBMCResultWrapperLong> readLongs(String name, List<String> toExtract) {

        List<CBMCResultWrapperLong> toReturn = new ArrayList<CBMCResultWrapperLong>();

        Pattern correctChecker = Pattern.compile("(\\b" + name + "[0-9]+=[0-9]+u*)(.*)");

        Pattern longExtractor = Pattern.compile("(\\b" + name + "[0-9]+)(.*)");

        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, segmentEnder);

        line = mergeLinesToOne(iterator, segmentEnder);

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
                    // prase the binary value to a long
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
            line = mergeLinesToOne(iterator, segmentEnder);
        }
        return toReturn;
    }

    @Override
    public List<CBMCResultWrapperSingleArray> readOneDimVarLong(String name, List<String> toExtract) {

        List<CBMCResultWrapperSingleArray> list = new ArrayList<CBMCResultWrapperSingleArray>();

        // this pattern searches for words of the form
        // "votesNUMBER[NUMBER]" where "NUMBER" can by any positive
        // number. Also, the next character has to be an equals sign
        Pattern votesExtractor = null;

        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, segmentEnder);

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

                    // strip away whitespaces and the double braces that
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
                                    wrapper.addTo(i, subValueArray[i]);
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
            line = mergeLinesToOne(iterator, segmentEnder);
        }
        return list;
    }

    @Override
    public List<CBMCResultWrapperMultiArray> readTwoDimVarLong(String name, List<String> toExtract) {
        List<CBMCResultWrapperMultiArray> list = new ArrayList<CBMCResultWrapperMultiArray>();

        Pattern votesExtractor = null;

        Iterator<String> iterator = toExtract.iterator();
        String line = mergeLinesToOne(iterator, segmentEnder);

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

                    // strip away whitespaces and the double braces that
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
            line = mergeLinesToOne(iterator, segmentEnder);
        }
        return list;
    }

    /**
     * 
     * @param toMerge
     * @param regexToEndAt
     * @return
     */
    private String mergeLinesToOne(Iterator<String> toMerge, String regexToEndAt) {
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
}
