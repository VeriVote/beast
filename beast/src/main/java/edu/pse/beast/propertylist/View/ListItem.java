package edu.pse.beast.propertylist.View;

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
		this.add(showResult);
		this.add(name);
		this.add(testStatus);
		this.add(changeButton);
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
