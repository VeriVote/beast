package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.propertylist.PropertyListMenuBarHandler;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
*
* @author Justin
*/
public class PropertyListWindow extends JFrame implements DisplaysStringsToUser {
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JToolBar toolBar;
	private JPanel panel;
	private JPanel endpanel;
	
	private ObjectRefsForBuilder refs;
	
	private ArrayList<ListItem> items;
	private JButton addNewButton = new JButton();

	public PropertyListWindow() {
		init();
	}
	
	public PropertyListWindow(ObjectRefsForBuilder refs) {
		this.refs = refs;
		init();
	}
	
	public JToolBar getToolbar() {
    	return toolBar;
    }
	
	
	public ArrayList<ListItem> getList() {
		return items;
	}
	public void setList(ArrayList<ListItem> items) {
		this.items = items;
	}

	private void init() {
		
		// setFrame(new JFrame());
		this.setLayout(new BorderLayout());
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Eigenschaftenliste");
		
		menuBar = new JMenuBar();
		menuFile = new JMenu();
		menuFile.setText("Menu");
		menuEdit = new JMenu();
		menuEdit.setText("Bearbeiten");
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		this.setJMenuBar(menuBar);
		
		
		
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		/*for (ListItem item : items) {
			panel.add(item);
		}*/
		
		endpanel = new JPanel();
		getContentPane().add(endpanel, BorderLayout.LINE_END);
		
		addNewButton.setText("Neu");
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewPropertyAction(e);
			}

		});
		endpanel.add(addNewButton);
		
		
		
	}
	
	private void addNewPropertyAction(ActionEvent e) {
		new NewPropertyWindow();
		
	}

	@Override
	public void updateStringRes(StringLoaderInterface sli) {
		menuFile.setText(sli.getPropertyListStringResProvider().getMenuStringRes().getStringFromID("menuFile"));
		menuEdit.setText(sli.getPropertyListStringResProvider().getMenuStringRes().getStringFromID("menuEdit"));
		addNewButton.setText(sli.getPropertyListStringResProvider().getToolbarTipStringRes().getStringFromID("addNew"));
		setTitle(sli.getPropertyListStringResProvider().getMenuStringRes().getStringFromID("title"));
	}
	
	
	
}
