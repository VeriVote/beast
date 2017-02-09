package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.ResultType;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

/**
*
* @author Justin
*/
public class PropertyListWindow extends JFrame implements DisplaysStringsToUser, Observer {
	
	private PLModelInterface model;
	private PLControllerInterface controller;
	private String title;
	private String currentlyLoadedPropertyListName;
	private StringLoaderInterface sli = new StringLoaderInterface("de");
	private boolean reactsToInput = true;
	
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuEdit;
	private JToolBar toolBar = new JToolBar();
	private JPanel panel;
	private JPanel endpanel;
	
	private ArrayList<ListItem> items = new ArrayList<ListItem>();
	private ListItem nextToPresent;
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
		//this.setResizable(true);
		setBounds(600, 100, 500, 500);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		getContentPane().add(panel, BorderLayout.CENTER);
		
		if (!items.isEmpty()) {
			for (ListItem item : items) {
				panel.add(item, BorderLayout.CENTER);
			}
		}
		JScrollPane jsp = new JScrollPane(panel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		
		endpanel = new JPanel();
		getContentPane().add(endpanel, BorderLayout.SOUTH);
		
		addNewButton.setIcon(new ImageIcon(getClass().getResource("/images/other/add.png")));
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//addNewPropertyAction(e);
				if (reactsToInput) controller.addNewProperty();
			}

		});
		endpanel.add(addNewButton, BorderLayout.LINE_END);
		setResizable(false);
		
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
	/*public void addItem(PropertyItem prop) {
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
	}*/
	
	
	private void updateItems(ArrayList<PropertyItem> propertyList) {
		items = new ArrayList<ListItem>();
		panel.removeAll();
		panel.revalidate();
		this.validate();
		
		for (PropertyItem propertyItem : propertyList) {
			ListItem current = new ListItem(controller, model, propertyItem);
			if (propertyItem.getResultType() == ResultType.TESTED) this.setNextToPresent(current);
			current.updateStringRes(sli);
			items.add(current);
			panel.add(current, BorderLayout.CENTER);
			//propertyItem.getResultInterface().presentTo(current);
		}
		panel.revalidate();
		panel.repaint();
	}
	
	public void updateView() {
		panel.revalidate();
		this.validate();
		panel.repaint();
	}
	
	
	private void addNewPropertyAction(ActionEvent e) {
		newPropWindow.toggleVisibility();
	}

	
	@Override
	public void updateStringRes(StringLoaderInterface sli) {
		this.sli = sli;
		PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
		StringResourceLoader other = provider.getOtherStringRes();
		StringResourceLoader menu = provider.getMenuStringRes();
		StringResourceLoader toolbarTip = provider.getToolbarTipStringRes();

		title = other.getStringFromID("title");
		setWindowTitle(other.getStringFromID("title"));
		this.addNewButton.setText(other.getStringFromID("newButton"));
		
		for (ListItem item : items) {
			item.updateStringRes(sli);
		}
		
		this.revalidate();
		this.repaint();
	}


	@Override
	public void update(Observable o, Object obj) {
		updateItems(model.getPropertyList());
	}
	
	public void rejectNameChange(PropertyItem prop) {
		controller.changeName(prop, prop.getDescription().getName());
		for (ListItem li : items) {
			if (prop.equals(li.getPropertyItem())) {
				//li.getNameField().setBackground(Color.RED);
				//li.getNameField().setForeground(Color.RED);
				updateItems(model.getPropertyList());
				//li.getNameField().setBackground(Color.WHITE);
			}
		}
	}
	
	public void stopReacting() {
		setReactsToInput(false);
	}

	public void resumeReacting() {
		setReactsToInput(true);
	}

	private void setReactsToInput(boolean reacts) {
		reactsToInput = reacts;
		for (ListItem item : items) item.setReactsToInput(reacts);
	}
	
	public ListItem getNextToPresent() {
		return nextToPresent;
	}
	public void setNextToPresent(ListItem nextToPresent) {
		this.nextToPresent = nextToPresent;
	}

	@Override
	public void setTitle(String s) {
		super.setTitle(s);
	}

	/**
	 * Adds the given string to the window title, used for displaying name of currently loaded PropertyList object
	 * @param propListName name of the currently loaded PropertyList object
	 */
	public void setWindowTitle(String propListName) {
		this.currentlyLoadedPropertyListName = propListName;
		this.setTitle(title + " " + propListName);
	}
}
