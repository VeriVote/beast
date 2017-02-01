package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
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
public class PropertyListWindow extends JFrame implements DisplaysStringsToUser, Observer {
	
	PLModelInterface model;
	PLControllerInterface controller;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JToolBar toolBar = new JToolBar();
	private JPanel panel;
	private JPanel endpanel;
	
	private ArrayList<ListItem> items = new ArrayList<ListItem>();
	private JButton addNewButton = new JButton();
	
	private NewPropertyWindow newPropWindow;

	public PropertyListWindow(PLControllerInterface controller, PLModelInterface model) {
		this.controller = controller;
		this.model = model;
		model.addObserver(this);
		init();
	}
	
	
	private void init() {
		newPropWindow = new NewPropertyWindow(controller, model);
		
		// setFrame(new JFrame());
		this.setLayout(new BorderLayout());
		setBounds(600, 100, 500, 500);
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
		
		if (!items.isEmpty()) {
			for (ListItem item : items) {
				panel.add(item, BorderLayout.CENTER);
			}
		}
		/*JScrollPane jsp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);*/
		
		endpanel = new JPanel();
		getContentPane().add(endpanel, BorderLayout.SOUTH);
		
		addNewButton.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//addNewPropertyAction(e);
				controller.addNewProperty();
			}

		});
		endpanel.add(addNewButton, BorderLayout.LINE_END);
		
		
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

	
	// shaky
	public void addItem(PropertyItem prop) {
		ListItem addedItem = new ListItem(controller, model, prop);
		panel.add(addedItem, BorderLayout.WEST);
		panel.revalidate();
		items.add(addedItem);
	}
	public void delItem(int index) {
		ListItem delItem = items.get(index);
		panel.remove(delItem);
		panel.revalidate();
		items.remove(delItem);
	}
	
	
	private void updateItems(ArrayList<PropertyItem> propertyList) {
		items = new ArrayList<ListItem>();
		panel.removeAll();
		panel.revalidate();
		this.validate();
		
		for (PropertyItem propertyItem : propertyList) {
			ListItem current = new ListItem(controller, model, propertyItem);
			items.add(current);
			panel.add(current, BorderLayout.CENTER);
		}
		panel.revalidate();
		panel.repaint();
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
		
	}


	@Override
	public void update(Observable o, Object obj) {
		updateItems(model.getList());
		
		// check if new property was added to model
		//if (list.size() > items.size()) addItem(list.get(model.getDirtyIndex()));
		
		// check if property was deleted from model
		//if (list.size() < items.size()) delItem(model.getUpdateIndex());
		
		/*ArrayList<PropertyItem> list = ((PLModel)o).getDescr();
		items = new ArrayList<ListItem>();
		for (PropertyItem item : list) {
			items.add(new ListItem(controller, model, item));
		}
		updateItems();*/
	}
	
	public void rejectNameChange(PropertyItem prop) {
		for (ListItem li : items) {
			if (prop.equals(li.getPropertyItem())) {
				li.getNameField().setBackground(Color.RED);
				
				li.getNameField().setForeground(Color.RED);
				
				//li.getNameField().setBackground(Color.WHITE);
			}
		}
	}
	

	
	
	
}
