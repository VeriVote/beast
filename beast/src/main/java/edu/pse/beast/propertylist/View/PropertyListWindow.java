package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
*
* @author Justin
*/
public class PropertyListWindow extends JFrame{
	
	private ArrayList<ListItem> items;
	private JButton addNewButton = new JButton();

	public PropertyListWindow() {
		init();
	}
	
	public ArrayList<ListItem> getList() {
		return items;
	}
	public void setList(ArrayList<ListItem> items) {
		this.items = items;
	}

	private void init() {
		
		// setFrame(new JFrame());
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Eigenschaftenliste");
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu();
		JMenu menuEdit = new JMenu();
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		setJMenuBar(menuBar);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		for (ListItem item : items) {
			panel.add(item);
		}
		
		JPanel endpanel = new JPanel();
		getContentPane().add(endpanel, BorderLayout.PAGE_END);
		
		endpanel.add(addNewButton);
		
	}
}
