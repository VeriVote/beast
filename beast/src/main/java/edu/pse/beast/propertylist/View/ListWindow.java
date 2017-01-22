package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ListWindow {
	
	private JFrame frame;
	private ArrayList<ListItem> items;
	private JButton addNewButton = new JButton();

	public ListWindow() {
		init();
		frame.setVisible(true);
	}

	private void init() {
		
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 450, 300);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		getFrame().setJMenuBar(menuBar);
		
		JToolBar toolBar = new JToolBar();
		getFrame().getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getFrame().getContentPane().add(panel, BorderLayout.CENTER);
		
		for (ListItem item : items) {
			panel.add(item);
		}
		
		JPanel endpanel = new JPanel();
		getFrame().getContentPane().add(endpanel, BorderLayout.PAGE_END);
		
		endpanel.add(addNewButton);
		
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
