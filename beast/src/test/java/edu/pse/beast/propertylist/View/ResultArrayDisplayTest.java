package edu.pse.beast.propertylist.View;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.ElectionType;
import edu.pse.beast.datatypes.FailureExample;

public class ResultArrayDisplayTest {

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
		
		ElectionType et = ElectionType.SINGLECHOICE;
		List<Long[][]> votes = new ArrayList<Long[][]>();
		List<Long[]> elected = new ArrayList<Long[]>();
		votes.add(new Long[][]{ {3l,2l,1l}, {2l,3l,1l}, {2l,2l,2l} });
		votes.add(new Long[][]{ {2l,2l,1l}, {1l,1l,1l}, {3l,3l,1l} });
		elected.add(new Long[]{2l,1l});
		elected.add(new Long[]{2l,3l});
		
		FailureExample fail = new FailureExample(et, null, votes, null, elected, 3, 1, 3);
		
		win.presentFailureExample(fail);
		
		
	}
}
