package edu.pse.beast.pluginhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.inputplugins.Approval;
import edu.pse.beast.types.cbmctypes.inputplugins.Preference;
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice;
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoiceStack;
import edu.pse.beast.types.cbmctypes.inputplugins.WeightedApproval;
import edu.pse.beast.types.cbmctypes.outputplugins.Parliament;
import edu.pse.beast.types.cbmctypes.outputplugins.ParliamentStack;
import edu.pse.beast.types.cbmctypes.outputplugins.SingleCandidate;

public class TypeLoader {

	private static List<InputType> availableInTypes = new ArrayList<InputType>();

	private static List<OutputType> availableOutTypes = new ArrayList<OutputType>();
	
	public static boolean init = false;

	public static void loadTypes() { //TODO load add ons too
		availableInTypes.add(new SingleChoice());
		availableInTypes.add(new Approval());
		availableInTypes.add(new Preference());
		availableInTypes.add(new WeightedApproval());
		availableInTypes.add(new SingleChoiceStack());
		
		availableOutTypes.add(new SingleCandidate());
		availableOutTypes.add(new Parliament());
		availableOutTypes.add(new ParliamentStack());
		
		init = true;
	}

	public static List<InputType> getAvailableInputTypes() {
		if(!init) {
			loadTypes();
		}
		return availableInTypes;
	}

	public static List<OutputType> getAvailableOutputTypes() {
		if(!init) {
			loadTypes();
		}
		return availableOutTypes;
	}

	public static InputType getStandartInputType() {
		if(!init) {
			loadTypes();
		}
		return availableInTypes.get(0);
	}

	public static OutputType getStandartOutputType() {
		if(!init) {
			loadTypes();
		}
		return availableOutTypes.get(0);
	}

	public static InputType getInByID(String id) {
		if(!init) {
			loadTypes();
		}
		for (Iterator<InputType> iterator = availableInTypes.iterator(); iterator.hasNext();) {
			InputType inputType = (InputType) iterator.next();
			if (inputType.getInputIDinFile().equals(id)) {
				return inputType;
			}
		}
		return null;
	}

	public static OutputType getOutByID(String id) {
		if(!init) {
			loadTypes();
		}
		for (Iterator<OutputType> iterator = availableOutTypes.iterator(); iterator.hasNext();) {
			OutputType outputType = (OutputType) iterator.next();
			if (outputType.getOutputIDinFile().equals(id)) {
				return outputType;
			}
		}
		return null;
	}

}
