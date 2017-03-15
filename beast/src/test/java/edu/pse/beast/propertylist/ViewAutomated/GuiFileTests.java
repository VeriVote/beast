package edu.pse.beast.propertylist.ViewAutomated;

import static org.junit.Assert.*;

import java.awt.MenuBar;

import javax.swing.JMenuBar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.TestHelper;
import edu.pse.beast.propertylist.View.PropertyListWindow;

public class GuiFileTests {
	
	private final long wait = 300;
	TestHelper helper = new TestHelper();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		helper.startNewBEASTInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void saveTest() throws InterruptedException {
		PropertyList proli = helper.getPropListOfCurrentInstance();
		PropertyListWindow view = proli.getView();
		
		JMenuBar bar = (JMenuBar) view.getMainMenuBar();
		view.setVisible(true);
		Thread.sleep(wait);
		bar.getMenu(0).doClick();
		Thread.sleep(wait);
		//bar.getMenu(0).getItem(0).doClick();
		Thread.sleep(wait);
	}
	
	@Test
	public void buttonTest() {
		PropertyList proli = helper.getPropListOfCurrentInstance();
		PropertyListWindow view = proli.getView();
		
	}

}
