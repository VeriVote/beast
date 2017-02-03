package edu.pse.beast.propertylist.View;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.PropertyListBuilder;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

public class ErrorDisplayTest {
	

	

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
		/*OptionsInterface options = new OptionsInterface();
		ObjectRefsForBuilder refs = new ObjectRefsForBuilder(options, new StringLoaderInterface("de"), 
				options.getLanguageOptions(), new SaverLoaderInterface());
        PropertyList propertyList = new PropertyListBuilder().createPropertyList(refs, null);*/
	}
	
	public static void main(String[] args) {
		
		ResultPresenterWindow win = new ResultPresenterWindow();
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<String> error = Arrays.asList("Some lines to test the window.", 
				"Big, bigger, biggest icecream will be found at the sea right off Maine and Northbridge/Upton.",
				"Yawn");
		win.presentFailure(error);
		
	}

}
