//package edu.pse.beast.types;
// TODO remove after rewrite
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ServiceLoader;
//
//import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
//import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
//import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
//import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
//
//public abstract class CommonHelpMethods {
//    /**
//     * extracts a variable with given dimension for the given checker from the output
//     * list
//     *
//     * @param name      the name of the variable
//     * @param dimension the dimension of the variable
//     * @param toExtract the list to extract from
//     * @return a two dim wrapper of the voting results
//     */
//    public abstract List<CBMCResultValueWrapper> extractVariable(String name, int dimension,
//            List<String> toExtract);
//    /**
//     * extracts a two dimensional variable for the given checker from the output
//     * list
//     *
//     * @param toExtract the list to extract from
//     * @param name      the name of the variable
//     * @return a two dim wrapper of the voting results
//     */
//    public abstract List<CBMCResultWrapperMultiArray>
//                readTwoDimVarLong(String name,
//                                  List<String> toExtract);
//
//    /**
//     * extracts a long variable out of the output from cbmc
//     *
//     * @param name      the name of the variable
//     * @param toExtract all the lines that should get checked, it they contain the
//     *                  searched variable
//     * @return a list of all occurrences
//     */
//    public abstract List<CBMCResultWrapperLong> readLongs(String name, List<String> toExtract);
//
//    /**
//     * this method is used to extract
//     *
//     * @param name      the name of the saved variable
//     * @param toExtract the string list to extract the variable out of
//     * @return a list of all variables with a matching name with their index and
//     *         values that occurred in the give list
//     */
//    public abstract List<CBMCResultWrapperSingleArray>
//                readOneDimVarLong(String name,
//                                  List<String> toExtract);
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