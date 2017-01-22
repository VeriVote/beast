package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;

/**
 * Controller/Model of the list of symbolic variables in BooleanExpressionWindow.
 * @author Nikolai
 */
public class SymbolicVarList implements DisplaysStringsToUser{
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
     * @return the JList containing the symbolic variables
     */
    public JList getSymbVarList() {
        return symbVarList;
    }

    /**
     * Update the language dependent displayed Strings in this class.
     * @param stringLoaderInterface the new stringLoaderInterface
     */
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
        //TODO implement
    }
}
