package edu.pse.beast.api;

import static edu.pse.beast.toolbox.CCodeHelper.generateSimpleDeclStringForCodeArea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class CreationHelper {
	public static ElectionDescription createSimpleDescription(String code) {
		InputType it = InputType.getInputTypes().get(2);
		OutputType ot = OutputType.getOutputTypes().get(3);
		ElectionDescription descr = new ElectionDescription("simple", it, ot);

		String elecDescrCode = generateSimpleDeclStringForCodeArea(descr.getContainer());

		int lockedLineStart = 0;
		int lockedLineEnd = lockedLineStart + elecDescrCode.length();

		elecDescrCode += "\n\n";
		
		elecDescrCode += code;

		elecDescrCode += "\n\n}";

		int lockedBracePos = elecDescrCode.length() - 1;

		descr.setCode(elecDescrCode);
		descr.setLockedPositions(lockedLineStart, lockedLineEnd, lockedBracePos);

		List<String> complexCode = descr.getComplexCodeAndSetHeaderLoopBounds();
		return descr;
	}
	
	public static List<PreAndPostConditionsDescription> createSimpleCondList(String name, String preCode, String postCode) {
		PreAndPostConditionsDescription conds = new PreAndPostConditionsDescription(name);

		conds.getPreConditionsDescription().setCode(preCode);
		conds.getPostConditionsDescription().setCode(postCode);

		List<PreAndPostConditionsDescription> condList = new ArrayList<>();
		condList.add(conds);

		return condList;
	}
	
	public static ElectionCheckParameter createParams(int maxV, int maxC, int maxS) {
		List<Integer> vots = new ArrayList<>();
		for(int i = 1; i <= maxV; ++i) {
			vots.add(i);
		}
		List<Integer> cands = new ArrayList<>();
		for(int i = 1; i <= maxC; ++i) {
			cands.add(i);
		}
		List<Integer> seats = new ArrayList<>();
		for(int i = 1; i <= maxS; ++i) {
			seats.add(i);
		}

		TimeOut timeOut = new TimeOut(TimeUnit.SECONDS, 1);

		ElectionCheckParameter param = new ElectionCheckParameter(vots, cands, seats, timeOut, 1, "");
		
		return param;
	}
}
