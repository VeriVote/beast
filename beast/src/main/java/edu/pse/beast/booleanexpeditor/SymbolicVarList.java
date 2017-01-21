package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;

/** TODO implement methods
 * @author Nikolai
 */
public class SymbolicVarList {
    private JList symbVarList;
    private JButton addVarButton;
    private JButton removeVarButton;
    private StringLoaderInterface stringLoaderInterface;
    /**
     * Constructor
     * @param symbVarList the JList element for the symbolic Variables
     * @param addVarButton the Button to add a variable to the list
     * @param removeVarButton the JButton to remove a variable from the list
     * @param stringLoaderInterface the interface to load needed strings
     */
    public SymbolicVarList(JList symbVarList, JButton addVarButton, JButton removeVarButton,
                           StringLoaderInterface stringLoaderInterface){
        this.symbVarList = symbVarList;
        this.addVarButton = addVarButton;
        this.removeVarButton = removeVarButton;
        this.stringLoaderInterface = stringLoaderInterface;
    }

    /**
     * Getter
     * @return the List of symbolic variables
     */
    public JList getSymbVarList() {
        return symbVarList;
    }
}
