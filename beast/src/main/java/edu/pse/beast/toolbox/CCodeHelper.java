/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.stringresource.StringResourceLoader;

import java.util.ArrayList;

/**
 * This class contains functionallity to generate C Code from internal data
 * structurs
 * @author Holger-Desktop
 */
public class CCodeHelper {

    /**
     * returns the C constant which is the max amount of elements in
     * a given list container
     * @param cont the typecontainer representing the list
     * @return the size of the container in C Code
     */
    public String getListSize(InternalTypeContainer cont) {
        if (cont.getAccesTypeIfList() == InternalTypeRep.CANDIDATE) {
            return "C";
        } else if (cont.getAccesTypeIfList() == InternalTypeRep.VOTER) {
            return "V";
        } else if (cont.getAccesTypeIfList() == InternalTypeRep.SEAT) {
            return "S";
        }
        ErrorLogger.log("");
        return "";
    }

    /**
     * creates the C-Type text representation of the given Internaltypecontainer,
     * arrays are created as arrays: "unsigned int votes[V][C]", for example
     * @param cont the container for which the C type should be created
     * @param name the name of the variable
     * @return the c type
     */
    public String getCType(InternalTypeContainer cont, String name) {
        InternalTypeContainer currentContainer = cont;
        String decl = "unsigned int " + name;
        while (currentContainer.isList()) {
            decl += "[" + getListSize(currentContainer) + "]";
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }
    
    /**
     * creates the C-Type text representation of the given Internaltypecontainer,
     * arrays are created as pointers: "unsigned int *", for example
     * @param cont the container for which the C type should be created
     * @return the c type
     */
    public String getCTypePointer(InternalTypeContainer cont) {
        InternalTypeContainer currentContainer = cont;
        String decl = "unsigned int";
        while (currentContainer.isList()) {
            decl += " *";
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    /**
     * if the given InternaltypeContainer represents a list, it generates the 
     * String representing a corresponding C-Array. Ex return: "[C]"
     * @param cont the container for which the C type should be created
     * @return the amount of square brackets and length constants needed
     */
    public String getCArrayType(InternalTypeContainer cont) {
        InternalTypeContainer currentContainer = cont;
        String decl = "";
        while (currentContainer.isList()) {
            decl += "[" + getListSize(currentContainer) + "]";
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    /**
     * generates the Decleration String for a voting function depending on its
     * input and result typ
     * @param input the input format of the voting array passed to the function
     * @param res the result format of the voting function
     * @return the voting function declaration line
     */
    public String generateDeclString(ElectionTypeContainer input, ElectionTypeContainer res) {
        String decl = "RESULT voting(VOTES) {";
        decl = decl.replace("RESULT", getCTypePointer(res.getType()));
        decl = decl.replace("VOTES", getCType(input.getType(), "votes"));
        return decl;
    }

    /**
     * Generates the complete function block which is placed in the C editor
     * if the user creates a new election description. It adds the explanatory
     * comments and the closing curly bracket
     * @param input  the input format of the voting array passed to the function
     * @param res the result format of the voting function
     * @param name the name of the election
     * @param templateHandler the Templatehandler which generated input and result types
     * @param stringResourceLoader the string ressource loader currently used
     * @return the complete voting function
     */
    public ElectionDescription generateElectionDescription(
            String input,
            String res,
            String name,
            ElectionTemplateHandler templateHandler,
            StringResourceLoader stringResourceLoader) {

        ElectionDescription description = new ElectionDescription(
                name,
                templateHandler.getById(input),
                templateHandler.getById(res),
                2);
        ArrayList<String> code = new ArrayList<>();
        code.add("//" + stringResourceLoader.getStringFromID(input) + ": " + stringResourceLoader.getStringFromID(input + "_exp"));
        code.add("//" + stringResourceLoader.getStringFromID(input) + ": " + stringResourceLoader.getStringFromID(res + "_exp"));
        code.add(generateDeclString(templateHandler.getById(input), templateHandler.getById(res)) + " ");
        code.add("} ");
        description.setCode(code);
        return description;
    }

    /**
     * returns the minimum value an element of the given ElectionTypeContainer
     * can have
     * @param inputElectionType the list whose elements min value needs to be
     * determined
     * @param rep the InternalTypeRep contained in the ElectionTypeContainer
     * @return minimum value an element of the given ElectionTypeContainer
     * can have
     */
    public String getMin(ElectionTypeContainer inputElectionType, InternalTypeRep rep) {
        if (null != rep) switch (rep) {
            case WEIGHTEDAPPROVAL:
                return String.valueOf(inputElectionType.getLowerBound());
            case INTEGER:
                return "0";
            case CANDIDATE:
                return "0";
            case APPROVAL:
                return "0";
            default:
                break;
        }
        return null;
    }
    
     /**
     * returns the max value an element of the given ElectionTypeContainer
     * can have
     * @param inputElectionType the list whose elements max value needs to be
     * determined
     * @param rep the InternalTypeRep contained in the ElectionTypeContainer
     * @return max value an element of the given ElectionTypeContainer
     * can have
     */
    public String getMax(ElectionTypeContainer inputElectionType, InternalTypeRep rep) {
        if (null != rep) switch (rep) {
            case WEIGHTEDAPPROVAL:
                return String.valueOf(inputElectionType.getUpperBound());
            case INTEGER:
                return "C";
            case CANDIDATE:
                return "C";
            case APPROVAL:
                return "2";
            default:
                break;
        }
        return null;
    }
}
