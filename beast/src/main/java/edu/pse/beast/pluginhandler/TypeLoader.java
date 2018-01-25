package edu.pse.beast.pluginhandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class TypeLoader {

	private static List<InputType> availableInTypes = new ArrayList<InputType>();

	private static List<OutputType> availableOutTypes = new ArrayList<OutputType>();

	public static void loadTypes() {
		// TODO load jars here
	}

	public static List<InputType> getAvailableInputTypes() {
		return availableInTypes;
	}

	public static List<OutputType> getAvailableOutputTypes() {
		return availableOutTypes;
	}

	public static InputType getStandartInputType() {
		return availableInTypes.get(0);
	}

	public static OutputType getStandartOutputType() {
		return availableOutTypes.get(0);
	}

	public static InputType getInByID(String id) {
		for (Iterator<InputType> iterator = availableInTypes.iterator(); iterator.hasNext();) {
			InputType inputType = (InputType) iterator.next();
			if (inputType.getInputIDinFile().equals(id)) {
				return inputType;
			}
		}
		return null;
	}

	public static OutputType getOutByID(String id) {
		for (Iterator<OutputType> iterator = availableOutTypes.iterator(); iterator.hasNext();) {
			OutputType outputType = (OutputType) iterator.next();
			if (outputType.getOutputIDinFile().equals(id)) {
				return outputType;
			}
		}
		return null;
	}

}
