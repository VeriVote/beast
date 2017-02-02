package edu.pse.beast.saverloader;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;

import java.util.*;

import static edu.pse.beast.datatypes.internal.InternalTypeRep.VOTER;

/**
*
* @author Justin
*/
public class PostAndPrePropertiesDescriptionSaverLoader {
    private final static String headTag = "||HEAD||";
    private final static String tagLeft = "<";
    private final static String tagRight = ">";
    private final static String tagEnder = "/";
    private final static String listIdentfier = "@";

    private PostAndPrePropertiesDescriptionSaverLoader() {

    }

    /**
     *
     * @param descr
     *            the description to save
     * @return the String to be saved in a file
     */
    public static List<String> createSaveFormat(PostAndPrePropertiesDescription descr) {

        // create the list
        List<String> savedFormat = new ArrayList<String>();
        savedFormat.add("<PostAndPrePropertiesDescription>");

        // save the name
        saveBasicType(savedFormat, "name", descr.getName());

        // save the preProp Code to the list
        saveBasicType(savedFormat, "prePropCode", descr.getPrePropertiesDescription().getCode());

        // save the postProp Code to the list
        saveBasicType(savedFormat, "postPropCode", descr.getPostPropertiesDescription().getCode());

        // save the symbolic variable list
        saveSymbolicVarList(savedFormat, "symbolicVarList", descr.getSymbolicVariableList());

        savedFormat.add("<!PostAndPrePropertiesDescription>");
        return savedFormat;
    }

    private static void saveBasicType(List<String> resultList, String varName, String value) {
        resultList.add(tagLeft + varName + tagRight);
        resultList.add(value);
        resultList.add(tagLeft + tagEnder + varName + tagRight);
    }

    private static void saveCodeLines(List<String> resultList, String varName, String code) {
        //signals that it is a list
        varName = varName + listIdentfier;
        // save the code
        resultList.add(tagLeft + varName + tagRight);
        resultList.add(code);
        resultList.add(tagLeft + tagEnder + varName + tagRight);
    }

    private static void saveSymbolicVarList(List<String> resultList, String varName, LinkedList<SymbolicVariable> symbolicVariableList) {
        //signals that it is a list
        varName = varName + listIdentfier;
        // save the code
        resultList.add(tagLeft + varName + tagRight);
        for (Iterator<SymbolicVariable> iterator = symbolicVariableList.iterator(); iterator.hasNext();) {
            String variableType = "";
            switch (iterator.next().getInternalTypeContainer().getInternalType()) {
                case VOTER:
                    variableType = "VOTER";
                    break;
                case CANDIDATE:
                    variableType = "CANDIDATE";
                    break;
                case SEAT:
                    variableType = "SEAT";
                    break;
            }
            String variableLine = iterator.next().getId() + " " + variableType;
            resultList.add(variableLine);
        }
        resultList.add(tagLeft + tagEnder + varName + tagRight);
    }

    public static PostAndPrePropertiesDescription createFromSaveFormat(List<String> format) {
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
