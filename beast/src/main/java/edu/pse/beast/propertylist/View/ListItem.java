package edu.pse.beast.propertylist.View;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.FailureExample;
import edu.pse.beast.propertylist.PropertyItem;

/**
*
* @author Justin
*/
public class ListItem extends JPanel{
	
	private PropertyItem descr;
	
	protected ResultButton showResult;
	protected JTextField name;
	protected JCheckBox testStatus;
	protected JButton changeButton;
	protected JButton deleteButton;
	
	public ListItem() {
		showResult = new ResultButton();
		name = new JTextField();
		testStatus = new JCheckBox();
		changeButton = new JButton();
		deleteButton = new JButton();
		
		descr = new PropertyItem();
		
		init();
	}
	
	private void init() {
		Dimension iconSize = new Dimension(40,40);
		showResult.setPreferredSize(iconSize);
		showResult.setIcon(new ImageIcon(getClass().getResource("/images/other/eye.png")));
		this.add(showResult);
		name.setText(descr.getDescription().getName());
		this.add(name);
		testStatus.setText("Check");
		this.add(testStatus);
		changeButton.setPreferredSize(iconSize);
		changeButton.setIcon(new ImageIcon(getClass().getResource("/images/other/wrench.png")));
		this.add(changeButton);
		deleteButton.setPreferredSize(iconSize);
		deleteButton.setIcon(new ImageIcon(getClass().getResource("/images/other/x-mark.png")));
		this.add(deleteButton);
	}
	
	public void presentResultOf() {
		//TODO eigentliches Argument: Result res
	}
	
	public void presentTimeout() {
		//TODO
	}
	
	public void presentSuccess() {
		//TODO
	}
	
	public void presentFailure() {
		//TODO
	}
	
	public void presentFailureExample(FailureExample ex) {
		//TODO
	}

}
