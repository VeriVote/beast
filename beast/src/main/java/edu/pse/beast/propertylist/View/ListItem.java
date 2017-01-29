package edu.pse.beast.propertylist.View;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.FailureExample;
import edu.pse.beast.propertychecker.Result;

/**
*
* @author Justin
*/
public class ListItem extends JPanel{
	
	private PostAndPrePropertiesDescription descr;
	
	protected JButton showResult;
	protected JTextField name;
	protected JCheckBox testStatus;
	protected JButton changeButton;
	protected JButton deleteButton;
	
	public ListItem() {
		showResult = new JButton();
		name = new JTextField();
		testStatus = new JCheckBox();
		changeButton = new JButton();
		deleteButton = new JButton();
		
		init();
	}
	
	private void init() {
		this.add(showResult);
		this.add(name);
		this.add(testStatus);
		this.add(changeButton);
		this.add(deleteButton);
	}
	
	public void presentResultOf(Result res) {
		//TODO
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
