package edu.pse.beast.SaverLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.OmniSaverLoader;

public class OmniSaverLoaderTest {
	public static void main(String[] args) {
		new OmniSaverLoaderTest();
	}
	
	public OmniSaverLoaderTest() {
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
        symbolicVariableList.addSymbolicVariable("testVar", new InternalTypeContainer(InternalTypeRep.SEAT));

        PostAndPrePropertiesDescription postAndPrePropertiesDescription = new PostAndPrePropertiesDescription("name",
                preDescr, postDescr, symbolicVariableList);

		List<String> format = OmniSaverLoader.createSaveFormat(electionDescription);
		
		for (Iterator iterator = format.iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			System.out.println(line);
		}
	}
	
}
