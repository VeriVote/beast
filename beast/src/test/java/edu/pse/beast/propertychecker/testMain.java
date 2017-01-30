package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    
    public void testWhole () {
        String pre = "FOR_ALL_VOTERS(v) : EXISTS_ONE_CANDIDATE(c) : (c == VOTES2(v) && (VOTE_SUM_FOR_CANDIDATE(c)>= 3 ==> c < 2));";
        String post = "VOTES2 == VOTES1;";
        
        PostAndPrePropertiesDescription descr = new PostAndPrePropertiesDescription(
                "test1",
                new FormalPropertiesDescription(pre),
                new FormalPropertiesDescription(post));
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("c", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("v", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        
        descr.setSymbolicVariableList(list);
        
        InternalTypeContainer input = new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
        InternalTypeContainer res = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
        
        ElectionDescription electionDescr = new ElectionDescription(
                "descr",
                new ElectionTypeContainer(input, ""), 
                new ElectionTypeContainer(res, ""), 0);
        
        String code = "unsigned int voting(unsigned int voters[V], unsigned int candidates[C], unsigned int seats[S]) {\n"+
                "return 0;\n" +
                "}\n";
        
        electionDescr.setCode(Arrays.asList(code.split("\n")));

        
        
        
        PropertyChecker propCheck = CheckerFactoryFactory.createPropertyChecker("cbmc");
        
        
        
        
        implElectionDescriptionSource eSrc = new implElectionDescriptionSource(electionDescr);
        
        List<PostAndPrePropertiesDescription> tmp = new ArrayList<PostAndPrePropertiesDescription>();
        tmp.add(descr);
        
        implPropertyDescriptionSource pSrc = new implPropertyDescriptionSource(tmp);
        
        
        List<Integer> amountVoters = new ArrayList<Integer>();
        amountVoters.add(5);
        amountVoters.add(7);
        
        List<Integer> amountCandidates = new ArrayList<Integer>();
        amountCandidates.add(5);
        
        List<Integer> amountSeats = new ArrayList<Integer>();
        amountSeats.add(5);
        
        ElectionCheckParameter ecp = new ElectionCheckParameter(amountVoters, amountCandidates, amountSeats, new TimeOut(TimeUnit.SECONDS, 20), 4, "");
        
        implParameterSource parmSrc = new implParameterSource(ecp);
        
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::");
        
        
        propCheck.checkPropertiesForDescription(eSrc, pSrc, parmSrc);
        
        
//        String generated = new CBMCCodeGenerator_Holger().generateCode(descr, electionDescr);
//        System.out.println("");
//        System.out.println("");
//        
//        List<String> tocheck = new ArrayList<String>(Arrays.asList(generated.split("\n")));
//        
//        for (Iterator iterator = tocheck.iterator(); iterator.hasNext();) {
//            String string = (String) iterator.next();
//            System.out.println(string);
//        }
//        System.out.println("=============");
//        System.out.println(generated);
    }
}
