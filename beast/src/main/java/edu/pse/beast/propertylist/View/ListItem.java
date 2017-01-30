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
	
	protected ResultButton showResult = new ResultButton();
	protected JTextField name = new JTextField();
	protected JCheckBox testStatus = new JCheckBox();
	protected JButton changeButton = new JButton();
	protected JButton deleteButton = new JButton();
	
	public ListItem() {
		descr = new PropertyItem();
		init();
	}
	
	public ListItem(PropertyItem descr) {
		this.descr = descr;
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
		testStatus.setSelected(descr.willBeTested());
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
