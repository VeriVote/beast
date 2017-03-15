package edu.pse.beast.propertylist.View;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.PiePlot;
import de.erichseifert.gral.ui.DrawablePanel;
import org.junit.*;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;


public class ErrorDisplayTest {
	
	ResultPresenterWindow win;

	

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
		win.dispose();
	}
	
	@Test
	public void testErrorDisplay() throws InterruptedException {
		
		win = new ResultPresenterWindow();
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		List<String> error = Arrays.asList("Some lines to test the window.", 
				"Big, bigger, biggest fish to fry will be found at the sea right off Maine and Northbridge/Upton.",
				"Yawn",
				"Darker, darker still.",
				"I can't get no satisfaction",
				"but i try",
				"and i try",
				"and i try",
				"yeah");
		win.presentFailure(error);
		
		Thread.sleep(100);
		
		/*DataTable data = new DataTable(Integer.class);
		data.add(5);
		data.add(6);
		
		PiePlot plot = new PiePlot(data);
		
		DrawablePanel graph = new DrawablePanel(plot);
		
		graph.setSize(400,400);
		
		JPanel iframe = new JPanel();
		iframe.add(graph);
		iframe.setSize(200, 200);
		iframe.setVisible(true);
		
		//win.panel.add(iframe);
		
		//win.panel.revalidate();
		//win.panel.repaint();
		
		graph.setSize(200,200);;
		
		win.getContentPane().add(graph, 2);
		
		DrawablePanel graph2 = new DrawablePanel(plot);
		
		graph2.setSize(200,200);;
		
		win.getContentPane().add(graph2, 3);
		
		//win.getContentPane().add(iframe, BorderLayout.SOUTH, 2);
		
		//win.getContentPane().add(iframe, 2);
		
		iframe.setSize(200, 200);
		
		win.revalidate();
		win.repaint();
		
		/*JFrame res = new JFrame();
		res.add(graph);
		res.setSize(200, 200);
		res.setVisible(true);*/
		
		/*graph.setBounds(win.getBounds());
		
		win.showPlot(graph);
		win.pack();
		win.revalidate();
		win.repaint();*/
		
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
