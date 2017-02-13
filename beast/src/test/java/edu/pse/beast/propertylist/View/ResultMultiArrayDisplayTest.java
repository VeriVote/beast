package edu.pse.beast.propertylist.View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.datatypes.electiondescription.ElectionType;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrappersingleArray;

public class ResultMultiArrayDisplayTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}

	public static void main(String[] args) {
		
		ResultPresenterWindow win = new ResultPresenterWindow();
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ElectionType elt = ElectionType.SINGLECHOICE;
		List<Long[][]> voted = new ArrayList<Long[][]>();
		List<Long[]> electeds = new ArrayList<Long[]>();
		voted.add(new Long[][]{ {3l,2l,1l}, {2l,3l,1l}, {2l,2l,2l} });
		voted.add(new Long[][]{ {2l,2l,1l}, {1l,1l,1l}, {3l,3l,1l} });
		electeds.add(new Long[]{2l,1l});
		electeds.add(new Long[]{2l,3l});
		
	//	FailureExample fail = new FailureExample(et, null, votes, null, elected, 3, 1, 3);
		
	//	win.presentFailureExample(fail);
		
		ElectionType et = ElectionType.APPROVAL;
		/*List<Long[]> vote = new ArrayList<Long[]>();
		List<Long> elected = new ArrayList<Long>();
		vote.add(new Long[]{3l,2l,1l});
		vote.add(new Long[]{2l,2l,1l});
		elected.add(3l);
		elected.add(2l);*/
		
		ArrayList<CBMCResultWrapperMultiArray> votes = new ArrayList<CBMCResultWrapperMultiArray>();
		
		
		votes.add(new CBMCResultWrapperMultiArray(0, "wildelection"));
		votes.get(0).addTo(0, 0, 1l);
		votes.get(0).addTo(0, 1, 1l);
		votes.get(0).addTo(0, 2, 1l);
		votes.get(0).addTo(0, 3, 1l);
		votes.get(0).addTo(0, 4, 1l);
		
		votes.get(0).addTo(1, 0, 1l);
		votes.get(0).addTo(1, 1, 0l);
		votes.get(0).addTo(1, 2, 1l);
		votes.get(0).addTo(1, 3, 1l);
		votes.get(0).addTo(1, 4, 0l);
		
		votes.get(0).addTo(2, 0, 1l);
		votes.get(0).addTo(2, 1, 1l);
		votes.get(0).addTo(2, 2, 0l);
		votes.get(0).addTo(2, 3, 1l);
		votes.get(0).addTo(2, 4, 1l);
		
		votes.get(0).addTo(3, 0, 1l);
		votes.get(0).addTo(3, 1, 0l);
		votes.get(0).addTo(3, 2, 0l);
		votes.get(0).addTo(3, 3, 0l);
		votes.get(0).addTo(3, 4, 0l);
		
		votes.get(0).addTo(4, 0, 1l);
		votes.get(0).addTo(4, 1, 1l);
		votes.get(0).addTo(4, 2, 1l);
		votes.get(0).addTo(4, 3, 1l);
		votes.get(0).addTo(4, 4, 1l);
		
		votes.add(new CBMCResultWrapperMultiArray(1, "wilderelection"));
		votes.get(1).addTo(0, 0, 1l);
		votes.get(1).addTo(0, 1, 1l);
		votes.get(1).addTo(0, 2, 1l);
		votes.get(1).addTo(0, 3, 1l);
		votes.get(1).addTo(0, 4, 1l);
		
		votes.get(1).addTo(1, 0, 1l);
		votes.get(1).addTo(1, 1, 0l);
		votes.get(1).addTo(1, 2, 1l);
		votes.get(1).addTo(1, 3, 1l);
		votes.get(1).addTo(1, 4, 0l);
		
		votes.get(1).addTo(2, 0, 1l);
		votes.get(1).addTo(2, 1, 1l);
		votes.get(1).addTo(2, 2, 0l);
		votes.get(1).addTo(2, 3, 1l);
		votes.get(1).addTo(2, 4, 1l);
		
		votes.get(1).addTo(3, 0, 1l);
		votes.get(1).addTo(3, 1, 0l);
		votes.get(1).addTo(3, 2, 0l);
		votes.get(1).addTo(3, 3, 0l);
		votes.get(1).addTo(3, 4, 0l);
		
		votes.get(1).addTo(4, 0, 1l);
		votes.get(1).addTo(4, 1, 1l);
		votes.get(1).addTo(4, 2, 1l);
		votes.get(1).addTo(4, 3, 1l);
		votes.get(1).addTo(4, 4, 1l);
		
		ArrayList<CBMCResultWrappersingleArray> seats = new ArrayList<CBMCResultWrappersingleArray>();
		
		seats.add(new CBMCResultWrappersingleArray(0, "wildseats"));
		seats.get(0).addTo(0, 0l);
		seats.get(0).addTo(1, 2l);
		seats.add(new CBMCResultWrappersingleArray(1, "wilderseats"));
		seats.get(1).addTo(0, 1l);
		seats.get(1).addTo(1, 2l);
		
		FailureExample fail = new FailureExample(et, null, votes, null, seats, 5, 2, 5);
		
		win.presentFailureExample(fail);
		
	}
}
