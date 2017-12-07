/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer.ElectionInputTypeIds;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer.ElectionOutputTypeIds;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.stringresource.EnumToIdMapping;
import edu.pse.beast.stringresource.StringResourceLoader;

import java.util.ArrayList;

import org.openqa.selenium.OutputType;

/**
 * This class contains functionallity to generate C Code from internal data
 * structurs
 *
 * @author Holger-Desktop
 */
public class CCodeHelper {

	/**
	 * returns the C constant which is the max amount of elements in a given list
	 * container
	 *
	 * @param cont
	 *            the typecontainer representing the list
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
	 * @param internalTypeContainer 
	 *
	 * @param electionInputTypeIds
	 *            the container for which the C type should be created
	 * @param name
	 *            the name of the variable
	 * @return the c type
	 */
	public String getCType(ElectionInputTypeIds inputType, InternalTypeContainer internalTypeContainer, String name) {

		InternalTypeContainer currentContainer = internalTypeContainer.getListedType();
		
		String decl = "unsigned int " + name;
		
		switch (inputType) {
		case APPROVAL:
			
			decl += "[V][C]";
			
			break;
		case PREFERENCE:


			
			decl += "[V][C]";
			
			break;
		case SINGLE_CHOICE:

			decl += "[V]";
			
			break;
		case WEIGHTED_APPROVAL:

			decl += "[V][C]";
			
			break;

		default:
			
			//do nothing
			
			break;
		}
		return decl;
	}

	/**
	 * creates the C-Type text representation of the given Internaltypecontainer,
	 * arrays are created as pointers: "unsigned int *", for example
	 *
	 * @param electionOutputTypeIds
	 *            the container for which the C type should be created
	 * @return the c type
	 */
	public String getCTypePointer(ElectionOutputTypeIds outputType) {

		String decl = "";

		switch (outputType) {

		case CAND_OR_UNDEF:

			decl = "unsigned int";

			break;

		case CAND_PER_SEAT:

			decl = "struct result";

			break;

		default:

			decl = "unsigned int";

			break;

		}
		return decl;
	}

	/**
	 * if the given InternaltypeContainer represents a list, it generates the String
	 * representing a corresponding C-Array. Ex return: "[C]"
	 *
	 * @param cont
	 *            the container for which the C type should be created
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
	 * generates the Decleration String for a voting function depending on its input
	 * and result typ
	 *
	 * @param input
	 *            the input format of the voting array passed to the function
	 * @param res
	 *            the result format of the voting function
	 * @return the voting function declaration line
	 */
	public String generateDeclString(ElectionTypeContainer input, ElectionTypeContainer res) {
		String decl = "RESULT voting(VOTES) {";
		decl = decl.replace("RESULT", getCTypePointer(res.getOutputID()));
		decl = decl.replace("VOTES", getCType(input.getInputID(), input.getType(), "votes"));
		return decl;
	}

	/**
	 * Generates the complete function block which is placed in the C editor if the
	 * user creates a new election description. It adds the explanatory comments and
	 * the closing curly bracket
	 *
	 * @param input
	 *            the input format of the voting array passed to the function
	 * @param res
	 *            the result format of the voting function
	 * @param name
	 *            the name of the election
	 * @param templateHandler
	 *            the Templatehandler which generated input and result types
	 * @param stringResourceLoader
	 *            the string ressource loader currently used
	 * @return the complete voting function
	 */
	public ElectionDescription generateElectionDescription(ElectionTypeContainer.ElectionInputTypeIds input,
			ElectionTypeContainer.ElectionOutputTypeIds res, String name, ElectionTemplateHandler templateHandler,
			StringResourceLoader stringResourceLoader) {

		ElectionDescription description = new ElectionDescription(name, templateHandler.getById(input),
				templateHandler.getById(res), 2);
		ArrayList<String> code = new ArrayList<>();
		String inputIdInFile = EnumToIdMapping.getIDInFile(input);
		String resIdInFile = EnumToIdMapping.getIDInFile(res);
		code.add("//" + stringResourceLoader.getStringFromID(inputIdInFile) + ": "
				+ stringResourceLoader.getStringFromID(inputIdInFile + "_exp"));
		code.add("//" + stringResourceLoader.getStringFromID(resIdInFile) + ": "
				+ stringResourceLoader.getStringFromID(resIdInFile + "_exp"));
		code.add(generateDeclString(templateHandler.getById(input), templateHandler.getById(res)) + " ");
		code.add("} ");
		description.setCode(code);
		return description;
	}

	/**
	 * returns the max value an element of the given ElectionTypeContainer can have
	 *
	 * @param inputType
	 *            the list whose elements max value needs to be determined
	 * @return max value an element of the given ElectionTypeContainer can have
	 */
	public String getMax(ElectionTypeContainer inputType) {
		switch (inputType.getInputID()) {
		case APPROVAL:
			return "2";
		case SINGLE_CHOICE:
			return "C";
		case PREFERENCE:
			return "C";
		case WEIGHTED_APPROVAL:
			return String.valueOf(inputType.getUpperBound());
		default:
			throw new AssertionError(inputType.getInputID().name());

		}
	}

	/**
	 * returns the minimum value an element of the given ElectionTypeContainer can
	 * have
	 *
	 * @param inputType
	 *            the list whose elements min value needs to be determined
	 * @return minimum value an element of the given ElectionTypeContainer can have
	 */
	public String getMin(ElectionTypeContainer inputType) {
		switch (inputType.getInputID()) {
		case SINGLE_CHOICE:
			return "0";
		case PREFERENCE:
			return "0";
		case APPROVAL:
			return "0";
		case WEIGHTED_APPROVAL:
			return String.valueOf(inputType.getLowerBound());
		default:
			throw new AssertionError(inputType.getInputID().name());

		}
	}
}
