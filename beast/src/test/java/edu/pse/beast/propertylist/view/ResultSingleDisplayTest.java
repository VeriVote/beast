package edu.pse.beast.propertylist.view; // TODO remove

//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//
///**
// * Shows a failure example where the election is Single-Choice and one seat is
// * allocated.
// *
// * @author Justin Hecht
// */
//public class ResultSingleDisplayTest {
//    private ResultPresenterWindow win;
//
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownAfterClass() throws Exception {
//    }
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        win.dispose();
//    }
//
////  @Test
////  public void testSingleArrayFailureExample() throws InterruptedException {
////
////    win = new ResultPresenterWindow();
////    win.setVisible(true);
////    win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////
////    ElectionType et = ElectionType.SINGLECHOICE;
////    List<Long[]> vote = new ArrayList<Long[]>();
////    List<Long> elected = new ArrayList<Long>();
////    vote.add(new Long[]{3l,2l,1l});
////    vote.add(new Long[]{2l,2l,1l});
////    elected.add(3l);
////    elected.add(2l);
////
////    ArrayList<CBMCResultWrapperSingleArray> votes =
////        new ArrayList<CBMCResultWrapperSingleArray>();
////    ArrayList<CBMCResultWrapperLong> elect = new ArrayList<CBMCResultWrapperLong>();
////
////    votes.add(new CBMCResultWrapperSingleArray(0, "wildelection"));
////    votes.get(0).addTo(0, 3l);
////    votes.get(0).addTo(1, 2l);
////    votes.get(0).addTo(2, 1l);
////    votes.add(new CBMCResultWrapperSingleArray(1, "wilderelection"));
////    votes.get(1).addTo(0, 3l);
////    votes.get(1).addTo(1, 3l);
////    votes.get(1).addTo(2, 3l);
////
////    elect.add(new CBMCResultWrapperLong(0, "wildcandidate"));
////    elect.get(0).setValue(2l);
////    elect.add(new CBMCResultWrapperLong(1, "wildercandidate"));
////    elect.get(1).setValue(4l);
////
////
////    FailureExample fail = new FailureExample(et, votes, null, elect, null, 4, 1, 3);
////
////    win.presentFailureExample(fail);
////
////    Thread.sleep(wait);
////  }
//}
