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
 *
 * @author Holger-Desktop
 */
public class CCodeHelper {

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

    public String getCType(InternalTypeContainer cont, String name) {
        InternalTypeContainer currentContainer = cont;
        String decl = "unsigned int " + name;
        while (currentContainer.isList()) {
            decl += "[" + getListSize(currentContainer) + "]";
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    public String getCArrayType(InternalTypeContainer cont) {
        InternalTypeContainer currentContainer = cont;
        String decl = "";
        while (currentContainer.isList()) {
            decl += "[" + getListSize(currentContainer) + "]";
            currentContainer = currentContainer.getListedType();
        }
        return decl;
    }

    public String generateDeclString(ElectionTypeContainer input, ElectionTypeContainer res) {
        String decl = "RESULT voting(VOTES) {";
        decl = decl.replace("RESULT", getCType(res.getType(), ""));
        decl = decl.replace("VOTES", getCType(input.getType(), "votes"));
        return decl;
    }

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

    public String getMin(ElectionTypeContainer inputElectionType, InternalTypeRep rep) {
        if (rep == InternalTypeRep.WEIGHTEDAPPROVAL) {
            return String.valueOf(inputElectionType.getLowerBound());
        } else if (rep == InternalTypeRep.INTEGER) {
            return "0";
        } else if (rep == InternalTypeRep.CANDIDATE) {
            return "0";
        } else if (rep == InternalTypeRep.APPROVAL) {
            return "0";
        }
        return null;
    }

    public String getMax(ElectionTypeContainer inputElectionType, InternalTypeRep rep) {
        if (rep == InternalTypeRep.WEIGHTEDAPPROVAL) {
            return String.valueOf(inputElectionType.getUpperBound());
        } else if (rep == InternalTypeRep.INTEGER) {
            return "C";
        } else if (rep == InternalTypeRep.CANDIDATE) {
            return "C";
        } else if (rep == InternalTypeRep.APPROVAL) {
            return "2";
        }
        return null;
    }
}
