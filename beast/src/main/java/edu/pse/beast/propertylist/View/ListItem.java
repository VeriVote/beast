package edu.pse.beast.propertylist.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.datatypes.FailureExample;
import edu.pse.beast.highlevel.ResultPresenterElement;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.propertylist.PLControllerInterface;
import edu.pse.beast.propertylist.PropertyItem;
import edu.pse.beast.propertylist.Model.PLModelInterface;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

/**
*
* @author Justin
*/
public class ListItem extends JPanel implements DisplaysStringsToUser, ResultPresenterElement {
	
	PLModelInterface model;
	PLControllerInterface controller;
	
	private boolean reactsToInput;
	
	private PropertyItem prop;
	
	protected ResultButton showResult = new ResultButton();
	protected JTextField name = new JTextField();
	protected JCheckBox testStatus = new JCheckBox();
	protected JButton changeButton = new JButton();
	protected JButton deleteButton = new JButton();
	
	protected JFrame result = new JFrame();
	
	public ListItem(PLControllerInterface controller, PLModelInterface model) {
		this.model = model;
		this.controller = controller;
		prop = new PropertyItem();
		reactsToInput = true;
		init();
	}
	
	public ListItem(PLControllerInterface controller, PLModelInterface model, PropertyItem prop) {
		this.model = model;
		this.controller = controller;
		this.prop = prop;
		reactsToInput = true;
		init();
	}
	
	public PropertyItem getPropertyItem() {
		return prop;
	}
	public JTextField getNameField() {
		return name;
	}
	public ListItem getItem() {
		return this;
	}
	public void setReactsToInput(boolean reactsToInput) {
		this.reactsToInput = reactsToInput;
	}
	
	private void init() {
		this.setMaximumSize(new Dimension(500,2000));
		Dimension iconSize = new Dimension(40,40);
		showResult.setPreferredSize(iconSize);
		showResult.setIcon(new ImageIcon(getClass().getResource("/images/other/eye.png")));
		showResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Point leftupper = showResult.getLocation(); // for creating jframe
			}
		});
		this.add(showResult, BorderLayout.LINE_START);
		
		name.setPreferredSize(new Dimension(200,30));
		name.setText(prop.getDescription().getName());
		name.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (reactsToInput) controller.changeName(prop, name.getText());
			}
		});
		this.add(name, BorderLayout.LINE_START);
		
		
		testStatus.setText("Check");
		testStatus.setSelected(prop.willBeTested());
		testStatus.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (reactsToInput) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					controller.setTestStatus(prop, true);
				}
				else {
					controller.setTestStatus(prop, false);
				}
				}
			}
		});
		this.add(testStatus, BorderLayout.LINE_START);
		
		
		changeButton.setPreferredSize(iconSize);
		changeButton.setIcon(new ImageIcon(getClass().getResource("/images/other/wrench.png")));
		changeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reactsToInput) controller.editProperty(prop);
			}
		});
		this.add(changeButton, BorderLayout.LINE_START);
		
		deleteButton.setPreferredSize(iconSize);
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/images/other/x-mark.png")));
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (reactsToInput) controller.deleteProperty(prop);
			}
		});
		this.add(deleteButton, BorderLayout.LINE_START);
		
	}
	
	
	@Override
	public void updateStringRes(StringLoaderInterface sli) {
		PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
		StringResourceLoader other = provider.getOtherStringRes();
		
		this.testStatus.setText(other.getStringFromID("check"));
		this.revalidate();
		this.repaint();
	}

	@Override
	public void presentTimeOut() {
		showResult.setBackground(Color.ORANGE);
		
	}

	@Override
	public void presentSuccess() {
		showResult.setBackground(Color.GREEN);
		
	}

	@Override
	public void presentFailure() {
		showResult.setBackground(Color.RED);
		
	}

	@Override
	public void presentFailureExample(FailureExample example) {
		// TODO Auto-generated method stub
		
	}


}
