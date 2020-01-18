//package edu.pse.beast.propertychecker;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//
//
//public class CBMCResult_Test {
//    CBMCResult result = null;
//    List<String> lines = null;
//
//    @Before
//    public void create() {
//        //create some teststrings
//        lines = new ArrayList<String>();
//        lines.add("begin");
//        lines.add("----------------------------------------------------");
//        lines.add("elect1=4u (00000000000000000000000000000100)");
//        lines.add("----------------------------------------------------");
//        lines.add("votes1={ 2u, 4u, 8u, 16u } "
//                  + "({ 00000000000000000000000000000010, 00000000000000000000000000000100, "
//                  + "00000000000000000000000000001000, 00000000000000000000000000010000 })");
//
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        lines.add("votes2={ { 1u, 2u, 3u, 4u, 5u }, { 6u, 7u, 8u, 9u, 10u }, "
//                  + "{ 11u, 12u, 13u, 14u, 15u }, { 16u, 17u, 18u, 19u, 20u } } "
//                  + "({ { 00000000000000000000000000000001, 00000000000000000000000000000010, "
//                  + "00000000000000000000000000000011, 00000000000000000000000000000100, "
//                  + "00000000000000000000000000000101 }, { 00000000000000000000000000000110, "
//                  + "00000000000000000000000000000111, 00000000000000000000000000001000, "
//                  + "00000000000000000000000000001001, 00000000000000000000000000001010 }, "
//                  + "{ 00000000000000000000000000001011, 00000000000000000000000000001100, "
//                  + "00000000000000000000000000001101, 000000000000000000000000000001110, "
//                  + "00000000000000000000000000001111 }, { 000000000000000000000000000010000, "
//                  + "00000000000000000000000000010001, 00000000000000000000000000010010, "
//                  + "00000000000000000000000000010011, 00000000000000000000000000010100 } })");
//
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        lines.add("votes2={ { 1u, 2u, 3u, 4u, 5u }, { 6u, 7u, 8u, 9u, 10u }, "
//                  + "{ 11u, 12u, 13u, 14u, 15u }, { 16u, 17u, 18u, 19u, 20u } } "
//                  + "({ { 00000000000000000000000000000001, 00000000000000000000000000000010, "
//                  + "00000000000000000000000000000011, 00000000000000000000000000000100, "
//                  + "00000000000000000000000000000101 }, { 00000000000000000000000000000110, "
//                  + "00000000000000000000000000000111, 00000000000000000000000000001000, "
//                  + "00000000000000000000000000001001, 00000000000000000000000000001010 }, "
//                  + "{ 00000000000000000000000000001011, 00000000000000000000000000001100, "
//                  + "00000000000000000000000000001101, 000000000000000000000000000001110, "
//                  + "00000000000000000000000000001111 }, { 000000000000000000000000000010000, "
//                  + "00000000000000000000000000010001, 00000000000000000000000000010010, "
//                  + "00000000000000000000000000010011, 00000000000000000000000000010100 } })");
//
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        lines.add("votes3[0l][0l]=3ul (00000000000000000000000000000011)");
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        lines.add("votes3[0l][1l]=4ul (00000000000000000000000000000100)");
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        lines.add("votes4[0l]=5ul (00000000000000000000000000000101)");
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        lines.add("votes4[1l]=6ul (00000000000000000000000000000110)");
//        lines.add("path to file that is checked");
//        lines.add("----------------------------------------------------");
//        result = new CBMCResult();
//       // result.setResult(lines);
//    }
//
//    @Test
//    public void nullType() {
//        result.setElectionType(null);
//        result.setResult(lines);
//        assertNull(result.getFailureExample());
//    }
//
//    /***
//     * try to extract the single dim values from the result
//     */
//    public void singleDim() {
//        result.setResult(lines);
//        List<CBMCResultWrapperSingleArray> singleDim = result.getFailureExample().getVotes();
//        CBMCResultWrapperSingleArray zero = singleDim.get(0);
//        assertEquals(zero.getName(), "votes");
//        assertEquals(zero.getMainIndex(), 1);
//
//        Long[] vars = zero.getArray();
//        assertEquals((long) (vars[0]), 2l);
//        assertEquals((long) (vars[1]), 4l);
//        CBMCResultWrapperSingleArray one = singleDim.get(1);
//        assertEquals(one.getName(), "votes");
//        assertEquals(one.getMainIndex(), 4);
//        Long[] varsO = one.getArray();
//        assertEquals((long) (varsO[0]), 5l);
//    }
//
//    /**
//     * try to extract the multi dim values from the result
//     */
//    public void multiDim() {
//        result.setResult(lines);
//        List<CBMCResultWrapperMultiArray> multiDim = result.getFailureExample().getVoteList();
//        CBMCResultWrapperMultiArray zero = multiDim.get(0);
//        assertEquals(zero.getName(), "votes");
//        assertEquals(zero.getMainIndex(), 2);
//        Long[][] vars = zero.getArray();
//        assertEquals((long) (vars[0][0]), 1l);
//        assertEquals((long) (vars[1][1]), 7l);
//
//        CBMCResultWrapperMultiArray one = multiDim.get(1);
//        assertEquals(one.getName(), "votes");
//        assertEquals(one.getMainIndex(), 3);
//        Long[][] varsO = one.getArray();
//        assertEquals((long) (varsO[0][0]), 3l);
//    }
//}
