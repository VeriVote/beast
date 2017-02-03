package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.TimeOut;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.highlevel.ResultInterface;
import toBeImplemented.implElectionDescriptionSource;
import toBeImplemented.implParameterSource;
import toBeImplemented.implPropertyDescriptionSource;

public class testMain {
	public static void main(String[] args) {
		new testMain();
	}

	public testMain() {
		testWhole();
	}

	public void testWhole() {
		
		
		InternalTypeContainer intype1 = new InternalTypeContainer(InternalTypeRep.APPROVAL);
        InternalTypeContainer intype2 = new InternalTypeContainer(intype1, InternalTypeRep.CANDIDATE);
        InternalTypeContainer intype3 = new InternalTypeContainer(intype2, InternalTypeRep.VOTER);
        ElectionTypeContainer inputType = new ElectionTypeContainer(intype3, "input");
        InternalTypeContainer type2 = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        InternalTypeContainer outtype = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        ElectionTypeContainer outputType = new ElectionTypeContainer(outtype, "output");

        ElectionDescription electionDescription = new ElectionDescription("name", inputType, outputType, 0);
        ArrayList<String> userCode = new ArrayList<>();
        userCode.add("votingcode");
        userCode.add("abalsdf");
        electionDescription.setCode(userCode);

        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();

        String pre = "FOR_ALL_VOTERS(i) : (i!=u && i!=w ==> (VOTES1(i) == VOTES2(i)));";

        String post = "ELECT1 == ELECT2;";
        // String post = "1 == 2;";

        FormalPropertiesDescription preDescr = new FormalPropertiesDescription(pre);
        FormalPropertiesDescription postDescr = new FormalPropertiesDescription(post);

        PostAndPrePropertiesDescription postAndPrePropertiesDescription = new PostAndPrePropertiesDescription("name", preDescr, postDescr, symbolicVariableList);

        SymbolicVariableList symVariableList = new SymbolicVariableList();
        symVariableList.addSymbolicVariable("u", new InternalTypeContainer(InternalTypeRep.VOTER));
        symVariableList.addSymbolicVariable("w", new InternalTypeContainer(InternalTypeRep.VOTER));
        // symVariableList.addSymbolicVariable("i", new InternalTypeContainer(InternalTypeRep.VOTER));

        postAndPrePropertiesDescription.setSymbolicVariableList(symVariableList);

		

		PropertyChecker propCheck = CheckerFactoryFactory.createPropertyChecker("cbmc");

		implElectionDescriptionSource eSrc = new implElectionDescriptionSource(electionDescription);

		List<PostAndPrePropertiesDescription> tmp = new ArrayList<PostAndPrePropertiesDescription>();
		tmp.add(postAndPrePropertiesDescription);
		tmp.add(postAndPrePropertiesDescription);
		tmp.add(postAndPrePropertiesDescription);
		tmp.add(postAndPrePropertiesDescription);
		tmp.add(postAndPrePropertiesDescription);
		tmp.add(postAndPrePropertiesDescription);

		implPropertyDescriptionSource pSrc = new implPropertyDescriptionSource(tmp);

		List<Integer> amountVoters = new ArrayList<Integer>();
		amountVoters.add(4);

		List<Integer> amountCandidates = new ArrayList<Integer>();
		amountCandidates.add(5);

		List<Integer> amountSeats = new ArrayList<Integer>();
		amountSeats.add(4);

		ElectionCheckParameter ecp = new ElectionCheckParameter(amountVoters, amountCandidates, amountSeats,
				new TimeOut(TimeUnit.SECONDS, 20), 4, "--trace;--unwind 7");

		implParameterSource parmSrc = new implParameterSource(ecp);

		System.out.println(":::::::::::::::::::::::::::::::::::::::::::::");

		List<ResultInterface> resultate = propCheck.checkPropertiesForDescription(eSrc, pSrc, parmSrc);

		
		boolean loop = false;
		
		while (!loop) {
			loop = true;
			for (Iterator iterator = resultate.iterator(); iterator.hasNext();) {
				ResultInterface resultInterface = (ResultInterface) iterator.next();
				loop = loop && resultInterface.readyToPresent();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("______________________________________");
		System.out.println("======================================");
		System.out.println("______________________________________");

		
		for (Iterator iterator = resultate.iterator(); iterator.hasNext();) {
			ResultInterface resultInterface = (ResultInterface) iterator.next();
			resultInterface.presentTo(null);
		}
	}
}
