package edu.pse.beast.propertylist.View;

import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


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
				"Big, bigger, biggest fish to fry will be found at the sea right off Maine and Northbridge/Upton.",
				"Yawn");
		win.presentFailure(error);
		
		/*DataTable data = new DataTable(Integer.class);
		data.add(5);
		data.add(6);
		
		PiePlot plot = new PiePlot(data);*/
		
		
		//win.showPlot(plot);
		
		/*plot.setLegendVisible(true);
		
		DrawablePanel panely = new DrawablePanel(plot);
		
		JFrame graph = new JFrame();
		
		graph.add(panely);
		
		graph.pack();
		
		graph.setVisible(true);*/
		
		//win.showPlot(panely);
		
		/*
		
		Plot graph
		
		JPanel thePanel = new JPanel();
		
		Graphics grap = (Graphics)plot;
		
		thePanel.printAll(plot);
		
		thePanel.add(plot);
		
		win.showPlot((Component)plot);*/
		
	}

}
