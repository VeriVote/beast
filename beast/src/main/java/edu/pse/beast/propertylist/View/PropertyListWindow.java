package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.event.*;
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

import edu.pse.beast.booleanexpeditor.UserActions.LoadPropsUserAction;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.propertylist.Model.ResultType;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 * This class is the view of PropertyList.
 * @author Justin
 */
@SuppressWarnings("serial")
public class PropertyListWindow extends JFrame implements DisplaysStringsToUser, Observer {

	private PLModelInterface model;
	private PLControllerInterface controller;
	private String title;
	private String currentlyLoadedPropertyListName;
	private StringLoaderInterface sli;
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
	private JButton addCreatedButton = new JButton();
	
	private final String pathToAdd = "/core/images/other/add.png";
	private final ImageIcon addIcon = new ImageIcon(SuperFolderFinder.getSuperFolder() + pathToAdd);

	/**
	 * Updates the view when focus is gained in this JFrame, needed to update
	 * naming of ListItems when Property was saved in BooleanExpEditor.
	 */
	private WindowAdapter windowAdapter = new WindowAdapter() {
		@Override
		public void windowGainedFocus(WindowEvent windowEvent) {
			super.windowGainedFocus(windowEvent);
			((PLModel) model).updateView();
		}
	};

	
	/**
	 * Constructor
	 * @param controller The PropertyList controller
	 * @param model The model of PropertyList
	 */
	public PropertyListWindow(PLControllerInterface controller, PLModelInterface model) {
		this.controller = controller;
		this.model = model;
		model.addObserver(this);
		init();
	}
	
	
	private void init() {
		this.setLayout(new BorderLayout());
		setBounds(700, 100, 500, 500);
		setTitle("PropertyList");

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
		
		JScrollPane jsp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);

		endpanel = new JPanel();
		getContentPane().add(endpanel, BorderLayout.SOUTH);

		addNewButton.setIcon(addIcon);
		addNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (reactsToInput)
					controller.addNewProperty();
			}

		});
		endpanel.add(addNewButton, BorderLayout.LINE_END);
		
		addCreatedButton.setIcon(addIcon);
		addCreatedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reactsToInput) {
					LoadPropsUserAction load = new LoadPropsUserAction(controller.getEditor(), controller);
					load.loadIntoPropertyList();
				}
			}
		});
		endpanel.add(addCreatedButton, BorderLayout.LINE_END);
		
		setResizable(false);
		this.addWindowListener(windowAdapter);
		this.addWindowFocusListener(windowAdapter);
	}

	
	/**
	 * Updates the view so that all changes (in the model) are visible.
	 */
	public void updateView() {
		panel.revalidate();
		this.validate();
		panel.repaint();
	}
	
	/**
	 * Resets the name attribute because the name change for a PropertyItem was rejected.
	 * @param prop The PropertyItem that couldn't be changed
	 */
	public void rejectNameChange(PropertyItem prop) {
		controller.changeName(prop, prop.getDescription().getName());
		for (ListItem li : items) {
			if (prop.equals(li.getPropertyItem())) {
				updateItems(model.getPropertyList());
			}
		}
	}

	/**
	 * Stops reacting to user input.
	 */
	public void stopReacting() {
		setReactsToInput(false);
		this.setEnabled(false);
	}

	/**
	 * Resumes reacting to user input.
	 */
	public void resumeReacting() {
		setReactsToInput(true);
		this.setEnabled(true);
	}

	
	@Override
	public void updateStringRes(StringLoaderInterface sli) {
		this.sli = sli;
		PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
		StringResourceLoader other = provider.getOtherStringRes();

		title = other.getStringFromID("title");
		this.addNewButton.setText(other.getStringFromID("newButton"));
		this.addCreatedButton.setText(other.getStringFromID("createdButton"));

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

	
	// private methods
	private void setReactsToInput(boolean reacts) {
		reactsToInput = reacts;
		for (ListItem item : items)
			item.setReactsToInput(reacts);
	}
	
	private void updateItems(ArrayList<PropertyItem> propertyList) {
		items = new ArrayList<ListItem>();
		panel.removeAll();
		panel.revalidate();
		this.validate();

		for (PropertyItem propertyItem : propertyList) {
			ListItem current = new ListItem(controller, model, propertyItem);
			if (propertyItem.getResultType() == ResultType.TESTED)
				this.setNextToPresent(current);
			current.updateStringRes(sli);
			items.add(current);
			panel.add(current, BorderLayout.CENTER);
			// propertyItem.getResultInterface().presentTo(current);
		}
		panel.revalidate();
		panel.repaint();
	}

	
	// getter and setter
	public ListItem getNextToPresent() {
		return nextToPresent;
	}

	public void setNextToPresent(ListItem nextToPresent) {
		this.nextToPresent = nextToPresent;
	}

	/**
	 * Adds the given string to the window title, used for displaying name of
	 * currently loaded PropertyList object
	 * 
	 * @param propListName
	 *            name of the currently loaded PropertyList object
	 */
	public void setWindowTitle(String propListName) {
		this.setCurrentlyLoadedPropertyListName(propListName);
		this.setTitle(title + " " + propListName + " - BEAST");
	}

	public String getCurrentlyLoadedPropertyListName() {
		return currentlyLoadedPropertyListName;
	}

	public void setCurrentlyLoadedPropertyListName(String currentlyLoadedPropertyListName) {
		this.currentlyLoadedPropertyListName = currentlyLoadedPropertyListName;
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
}
