package edu.pse.beast.propertylist.ViewAutomated;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;

import javax.swing.JButton;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.TestHelper;
import edu.pse.beast.propertylist.View.PropertyListWindow;

public class GuiFileTests {
	
	private final long wait = 500;
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

	/*@Test
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
	}*/
	
	@Test
	public void newPropertyListTest() throws InterruptedException, AWTException {
		PropertyList proli = helper.getPropListOfCurrentInstance();
		PropertyListWindow view = proli.getView();
		
		view.setVisible(true);
		//ErrorLogger.log(view.getToolbar().getComponentAtIndex(0).getClass().toString());
		
		//view.getAddNewButton().doClick();
		//view.getAddNewButton().doClick();
		Thread.sleep(wait);
		
		JButton newList = (JButton) view.getToolbar().getComponentAtIndex(0);
		
		//JButton saveList = (JButton) view.getToolbar().getComponentAtIndex(1);
		
		//newList.doClick();
		
		proli.getModel().setNewName("testee");
		
		
		newList.doClick();
		
		Thread.sleep(wait);
		
		assertEquals(proli.getModel().getName(), "New PropertyList");
		
		/*int[] keys = {
				KeyEvent.VK_ENTER
		};
		
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);*/

		//helper.performKeystrokes(keys, 50);
		
		/*Window[] windows = view.getOwnedWindows();
		for (Window win : windows) {
			ErrorLogger.log(win.getClass().toString());
		}
		windows[0].setVisible(true);*/

		
		
		//Thread.sleep(wait);
		

	
		
		
		/*Object[] opts = opt.getOptions();
		JButton no = (JButton) opts[2];
		no.doClick();
		Thread.sleep(wait);*/
	}

}
