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
		List<int[][]> votes = new ArrayList<int[][]>();
		List<int[]> elected = new ArrayList<int[]>();
		votes.add(new int[][]{ {3,2,1}, {2,3,1}, {2,2,2} });
		votes.add(new int[][]{ {2,2,1}, {1,1,1}, {3,3,1} });
		elected.add(new int[]{2,1});
		elected.add(new int[]{2,3});
		
		FailureExample fail = new FailureExample(et, null, votes, null, elected, 3, 1, 3);
		
		win.presentFailureExample(fail);
		
		
	}
}
