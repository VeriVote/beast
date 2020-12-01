package edu.pse.beast.api;

import static edu.pse.beast.toolbox.CCodeHelper.generateSimpleDeclStringForCodeArea;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;




class BEASTTest {

	@Test
	void testBEASTSimpleElectionDescription() throws InterruptedException {
		InputType it = InputType.getInputTypes().get(2);
		OutputType ot = OutputType.getOutputTypes().get(3);
		ElectionDescription descr = new ElectionDescription("simple", it, ot);
		

		String elecDescrCode = generateSimpleDeclStringForCodeArea(descr.getContainer());	
		
		int lockedLineStart = 0;
		int lockedLineEnd = lockedLineStart + elecDescrCode.length();
		
		elecDescrCode += "\n\n";
		elecDescrCode += "\treturn 0;";
		
		elecDescrCode += "\n\n}";
		
		int lockedBracePos = elecDescrCode.length() - 1;

		descr.setCode(elecDescrCode);
		descr.setLockedPositions(lockedLineStart, lockedLineEnd, lockedBracePos);

		
		
		List<String> complexCode = descr.getComplexCodeAndSetHeaderLoopBounds();
		for(String s : complexCode) {
			System.out.println(s);
		}

		PreAndPostConditionsDescription conds = new PreAndPostConditionsDescription("simple");

		conds.getPreConditionsDescription().setCode("");
		conds.getPostConditionsDescription().setCode("ELECT1 == ELECT2;\n");
		
		List<PreAndPostConditionsDescription> condList = new ArrayList<>();
		condList.add(conds);

		List<Integer> vots = new ArrayList<>();
		vots.add(1);
		List<Integer> cands = new ArrayList<>();
		cands.add(1);
		List<Integer> seats = new ArrayList<>();
		seats.add(1);
		
		TimeOut timeOut = new TimeOut(TimeUnit.SECONDS, 1);

		ElectionCheckParameter param = new ElectionCheckParameter(vots, cands, seats, timeOut, 1, "");
		
		BEAST beast = new BEAST();
		
		BEASTCallback cb = new BEASTCallback() {
		};		
		
		BEASTTestSession p = beast.startTest(cb, descr, condList, param);
		
		p.doAfter(() -> {
			System.out.println("done");
		});
		p.waitSync();		
	}

}
