package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.PropertyItem;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.PropertyListMenuBarHandler;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
*
* @author Justin
*/
public class PropertyListWindow extends JFrame implements DisplaysStringsToUser, Observer, ActionListener {
	
	PLModelInterface model;
	PLControllerInterface controller;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JToolBar toolBar = new JToolBar();
	private JPanel panel;
	private JPanel endpanel;
	
	//private ObjectRefsForBuilder refs;
	//private PropertyList list;
	
	private ArrayList<ListItem> items = new ArrayList<ListItem>();
	private JButton addNewButton = new JButton();
	
	private NewPropertyWindow newPropWindow;

	//MVC
	public PropertyListWindow(PLControllerInterface controller, PLModelInterface model) {
		this.controller = controller;
		this.model = model;
		model.addObserver(this);
		init();
	}
	
	//superfluous
	/*public PropertyListWindow() {
		init();
	}*/
	
	//MVC
	private void init() {
		newPropWindow = new NewPropertyWindow(controller, model);
		
		// setFrame(new JFrame());
		this.setLayout(new BorderLayout());
		setBounds(600, 100, 500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Property List");
		
		menuBar = new JMenuBar();
		menuFile = new JMenu();
		menuFile.setText("Menu");
		menuEdit = new JMenu();
		menuEdit.setText("Bearbeiten");
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		this.setJMenuBar(menuBar);
		
		
		toolBar.setFloatable(false);
		toolBar.setRollover(true);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		if (items.isEmpty()) items.add(new ListItem(controller, model));
		for (ListItem item : items) {
			panel.add(item, BorderLayout.CENTER);
		}
		
		endpanel = new JPanel();
		getContentPane().add(endpanel, BorderLayout.SOUTH);
		
		addNewButton.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewPropertyAction(e);
			}

		});
		endpanel.add(addNewButton, BorderLayout.LINE_END);
	}
	
	
	/*public PropertyListWindow(ObjectRefsForBuilder refs) {
		this.refs = refs;
		init();
	}*/
	
	public JToolBar getToolbar() {
    	return toolBar;
    }
	
	public ArrayList<ListItem> getList() {
		return items;
	}
	public void setList(ArrayList<ListItem> items) {
		this.items = items;
	}
	/*public void setPropertyList(PropertyList list) {
		this.list = list;
	}*/

	private void updateItems() {
		panel.repaint();
		if (items.isEmpty()) items.add(new ListItem(controller, model));
		for (ListItem item : items) {
			panel.add(item, BorderLayout.CENTER);
		}
	}
	
	private void addNewPropertyAction(ActionEvent e) {
		newPropWindow.toggleVisibility();
	}

	
	@Override
	public void updateStringRes(StringLoaderInterface sli) {
		PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
		StringResourceLoader other = provider.getOtherStringRes();
		StringResourceLoader menu = provider.getMenuStringRes();
		StringResourceLoader toolbarTip = provider.getToolbarTipStringRes();
		
		this.setTitle(other.getStringFromID("title"));
		
		//menuFile.setText(sli.getPropertyListStringResProvider().getMenuStringRes().getStringFromID("menuFile"));
		//menuEdit.setText(sli.getPropertyListStringResProvider().getMenuStringRes().getStringFromID("menuEdit"));
		//addNewButton.setText(sli.getPropertyListStringResProvider().getToolbarTipStringRes().getStringFromID("addNew"));
		//setTitle(sli.getPropertyListStringResProvider().getMenuStringRes().getStringFromID("title"));
	}

	//MVC not yet
	@Override
	public void update(Observable o, Object obj) {
		ArrayList<PropertyItem> list = ((PLModel)o).getDescr();
		items = new ArrayList<ListItem>();
		for (PropertyItem item : list) {
			items.add(new ListItem(controller, model, item));
		}
		updateItems();
	}

	//MVC
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
