package edu.pse.beast.propertychecker;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import edu.pse.beast.celectiondescriptioneditor.electiontemplates.ElectionTemplateHandler;
//import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
//import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
//import edu.pse.beast.types.InternalTypeContainer;
//import edu.pse.beast.types.InternalTypeRep;
//
//public class PropertyCheckerTest {
//    public static void main(String[] args) {
//        new PropertyCheckerTest();
//    }
//
//    public PropertyCheckerTest() {
//        testWhole();
//    }
//
//    public void testWhole() {
//
//        ElectionTypeContainer inputType =
//                new ElectionTemplateHandler().getById(
//                    ElectionTypeContainer.ElectionInputTypeIds.WEIGHTED_APPROVAL);
//        ElectionTypeContainer outputType = new ElectionTemplateHandler().getStandardResult();
//
//        ElectionDescription electionDescription =
//            new ElectionDescription("name", inputType, outputType, 0);
//        ArrayList<String> userCode = new ArrayList<>();
//        userCode.add("votingcode");
//        userCode.add("abalsdf");
//        electionDescription.setCode(userCode);
//
//        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();
//
//        String pre = "FOR_ALL_VOTERS(i) : (i!=u && i!=w ==> (VOTES1(i) == VOTES2(i)));";
//
//        String post = "ELECT1 == ELECT2;";
//        // String post = "1 == 2;";
//
//        FormalPropertiesDescription preDescr = new FormalPropertiesDescription(pre);
//        FormalPropertiesDescription postDescr = new FormalPropertiesDescription(post);
//
//        PreAndPostConditionsDescription preAndPostConditionsDescription =
//                new PreAndPostConditionsDescription("name", preDescr,
//                                                    postDescr, symbolicVariableList);
//
//        SymbolicVariableList symVariableList = new SymbolicVariableList();
//        symVariableList.addSymbolicVariable(
//                "u",
//                new InternalTypeContainer(InternalTypeRep.VOTER)
//        );
//        symVariableList.addSymbolicVariable(
//                "w",
//                new InternalTypeContainer(InternalTypeRep.VOTER)
//        );
//        // symVariableList.addSymbolicVariable("i", new
//        // InternalTypeContainer(InternalTypeRep.VOTER));
//
//        preAndPostConditionsDescription.setSymbolicVariableList(symVariableList);
//
//        PropertyChecker propCheck = CheckerFactoryFactory.createPropertyChecker("cbmc");
//
////      implElectionDescriptionSource eSrc =
////          new implElectionDescriptionSource(electionDescription);
//
//        List<PreAndPostConditionsDescription> tmp =
//            new ArrayList<PreAndPostConditionsDescription>();
//        tmp.add(preAndPostConditionsDescription);
//
////      implPropertyDescriptionSource pSrc = new implPropertyDescriptionSource(tmp);
//
//        List<Integer> amountVoters = new ArrayList<Integer>();
//        amountVoters.add(10);
//
//        List<Integer> amountCandidates = new ArrayList<Integer>();
//        amountCandidates.add(100);
//
//        List<Integer> amountSeats = new ArrayList<Integer>();
//        amountSeats.add(4);
//
//        ElectionCheckParameter ecp =
//            new ElectionCheckParameter(amountVoters, amountCandidates,
//                                       amountSeats, new TimeOut(TimeUnit.MINUTES, 2),
//                                       8, "--trace;--unwind 105");
//
////      implParameterSource parmSrc = new implParameterSource(ecp);
//
//        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::");
//
////      List<ResultInterface> result =
//            propCheck.checkPropertiesForDescription(eSrc, pSrc, parmSrc);
//        boolean loop = false;
//        while (!loop) {
//            loop = true;
////          for (Iterator<ResultInterface> iterator = result.iterator();
////                   iterator.hasNext();) {
////              ResultInterface resultInterface = (ResultInterface) iterator.next();
////              loop = loop && resultInterface.readyToPresent();
////          }
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("______________________________________");
//        System.out.println("======================================");
//        System.out.println("______________________________________");
//
//  //      for (Iterator<ResultInterface> iterator = result.iterator(); iterator.hasNext();) {
//   //         ResultInterface resultInterface = (ResultInterface) iterator.next();
//   //         resultInterface.presentTo(null);
//    //    }
//        System.out.println("::::::::::::::::::::::::::::::::::::::::::");
////        for (Iterator iterator = ((Result)result.get(0)).getResult().iterator();
////                 iterator.hasNext();) {
////            String line = (String) iterator.next();
////            System.out.println(line);
////        }
//    }
//}
