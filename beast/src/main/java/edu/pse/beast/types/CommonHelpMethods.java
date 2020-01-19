package edu.pse.beast.types; // TODO removeS

//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ServiceLoader;
//
//import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
//
//public abstract class CommonHelpMethods {
//
//    /**
//     * extracts a variable with given dimension for the given checker from the output
//     * list
//     *
//     * @param variableNameMatcher a matcher which matches every variable to be extracted
//     * @param dimension the dimension of the variable
//     * @param toExtract the list to extract from
//     * @return a two dim wrapper of the voting results
//     */
//    public abstract List<ResultValueWrapper> extractVariable(String variableNameMatcher,
//                                                             List<String> toExtract);
//
//    public static List<CommonHelpMethods> getImplementations() {
//        ServiceLoader<CommonHelpMethods> loader = ServiceLoader.load(CommonHelpMethods.class);
//        List<CommonHelpMethods> implementations = new ArrayList<CommonHelpMethods>();
//        for (Iterator<CommonHelpMethods> iterator = loader.iterator(); iterator.hasNext();) {
//            CommonHelpMethods implementation = (CommonHelpMethods) iterator.next();
//            implementations.add(implementation);
//        }
//        return implementations;
//    }
//}
