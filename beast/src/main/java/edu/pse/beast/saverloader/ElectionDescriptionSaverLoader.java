package edu.pse.beast.saverloader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import toBeImplemented.ElectionTypeContainerSaverLoader;

/**
 *
 * @author Lukas
 */
public final class ElectionDescriptionSaverLoader {

    private final static String headTag = "||HEAD||";
    private final static String tagLeft = "<";
    private final static String tagRight = ">";
    private final static String tagEnder = "/";
    private final static String listIdentfier = "@";
    
    private ElectionDescriptionSaverLoader() {

    }

    /**
     * 
     * @param descr
     *            the description to save
     * @return the String to be saved in a file
     */
    public static List<String> createSaveFormat(ElectionDescription descr) {

        // create the list
        List<String> savedFormat = new ArrayList<String>();
        savedFormat.add("<ElectionDescription>");

        // save the name
        saveBasicType(savedFormat, "name", descr.getName());

        // save the code to the list
        saveCodeLines(savedFormat, "code", descr.getCode());

        // save the inputType
        saveElectionTypeContainer(savedFormat, "inputType", descr.getInputType());

        // save the outputType
        saveElectionTypeContainer(savedFormat, "outType", descr.getOutputType());

        // save the voting declaration line
        saveBasicType(savedFormat, "votingDeclLine", "" + descr.getVotingDeclLine());

        savedFormat.add("<!EletionDescription>");
        return savedFormat;
    }

    private static void saveBasicType(List<String> resultList, String varName, String value) {
        resultList.add(tagLeft + varName + tagRight);
        resultList.add(value);
        resultList.add(tagLeft + tagEnder + varName + tagRight);
    }

    private static void saveCodeLines(List<String> resultList, String varName, List<String> codeList) {
        //signals that it is a list
        varName = varName + listIdentfier;
        // save the code
        resultList.add(tagLeft + varName + tagRight);
        for (Iterator<String> iterator = codeList.iterator(); iterator.hasNext();) {
            String codeLine = (String) iterator.next();
            resultList.add(codeLine);
        }
        resultList.add(tagLeft + tagEnder + varName + tagRight);
    }

    private static void saveElectionTypeContainer(List<String> resultList, String varName,
            ElectionTypeContainer container) {
        List<String> containerLines = ElectionTypeContainerSaverLoader.createSaveFormat(container);
        resultList.add(tagLeft + varName + tagRight);
        for (Iterator<String> iterator = containerLines.iterator(); iterator.hasNext();) {
            String codeLine = (String) iterator.next();
            resultList.add(codeLine);
        }
        resultList.add(tagLeft + tagEnder + varName + tagRight);
    }

    public static ElectionDescription createFromSaveFormat(List<String> format) {
        if (!checkFormat(format)) {
            return null;
        }
        
        //TODO finish
        
        Stack<String> tags = new Stack<String>();
        /**
         * a string that sits at the front. it prevents nullpointer errors
         */
        tags.add(headTag);
        
        
        
        for (Iterator<String> iterator = format.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            // search for tags
            if (line.contains(tagLeft)) {
                line = line.replace(tagLeft, "").replace(tagRight, "");
                if (line.contains(tagEnder)) {
                    if (!((tagEnder + tags.pop()).equals(line))) {
                    }
                } else {
                    tags.push(line);
                }
            }
        }
        
        
        
        //TODO finish
        return null;
    }

    private static boolean checkFormat(List<String> format) {

        if (format.size() < 2) {
            /**
             * there are not at leas 1 tag of each
             */
            return false;
        }

        Stack<String> tags = new Stack<String>();
        /**
         * a string that sits at the front. it prevents nullpointer errors
         */
        tags.add(headTag);

        for (Iterator<String> iterator = format.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            // no text without tags. if only head is there, a tag hast to follow
            if (tags.size() == 1) {
                if (!(line.contains(tagLeft))) {
                    return false;
                }
            }

            // search for tags
            if (line.contains(tagLeft)) {
                line = line.replace(tagLeft, "").replace(tagRight, "");
                if (line.contains(tagEnder)) {
                    if (!((tagEnder + tags.pop()).equals(line))) {
                        return false;
                    }
                } else {
                    tags.push(line);
                }
            }
        }

        if (tags.size() == 1) {
            return tags.pop().equals(headTag);
        } else {
            return false;
        }
    }
}
