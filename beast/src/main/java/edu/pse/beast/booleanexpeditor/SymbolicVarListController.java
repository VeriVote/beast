package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.View.BooleanExpEditorWindow;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controller of the symbolic variable JList and its related JButtons in BooleanExpressionWindow.
 * @author Nikolai
 */
public class SymbolicVarListController implements DisplaysStringsToUser {
    private final JList jList;
    private final DefaultListModel jListModel;
    private final SymbolicVariableList symbolicVariableList;
    private StringLoaderInterface stringLoaderInterface;
    private String voterString;
    private String candidateString;
    private String seatString;
    private String typeString;
    private String newVariableString;
    private String errorString;
    private String nameNotMatchingError;
    private String alreadyExistsError;
    private BooleanExpEditorWindow booleanExpEditorWindow;

    /**
     * Constructor
     * @param jList the JList element for the symbolic Variables
     * @param addVarButton the Button to add a variable to the list
     * @param removeVarButton the JButton to remove a variable from the list
     * @param stringLoaderInterface the interface to load needed strings
     * @param booleanExpEditorWindow the BooleanExpEditorWindow object (view)
     * @param symbolicVariableList the SymbolicVariableList
     */
    SymbolicVarListController(JList jList, JButton addVarButton, JButton removeVarButton,
                              StringLoaderInterface stringLoaderInterface, SymbolicVariableList symbolicVariableList,
                              BooleanExpEditorWindow booleanExpEditorWindow) {
        this.jList = jList;
        this.stringLoaderInterface = stringLoaderInterface;
        this.symbolicVariableList = symbolicVariableList;
        this.jListModel = (DefaultListModel) jList.getModel();
        this.booleanExpEditorWindow = booleanExpEditorWindow;
        updateStringRes(stringLoaderInterface);
        addVarButton.addActionListener(new AddVarActionListener());
        removeVarButton.addActionListener(new RemoveVarActionListener());
    }

    /**
     * Loads new List<SymbolicVariable> object into this controller and updates the view.
     * @param symbVarList the new List<SymbolicVariable> element
     */
    public void setSymbVarList(List<SymbolicVariable> symbVarList) {
        symbolicVariableList.getSymbolicVariables().clear();
        symbolicVariableList.getSymbolicVariables().addAll(symbVarList);
        updateJlist();
    }

    private void updateJlist() {
        jListModel.clear();
        for (SymbolicVariable symbolicVariable : symbolicVariableList.getSymbolicVariables()) {
                jListModel.addElement(stringLoaderInterface.getBooleanExpEditorStringResProvider().
                        getBooleanExpEditorSymbVarListRes().getStringFromID(symbolicVariable.getInternalTypeContainer().
                        getInternalType().toString()) + " " + symbolicVariable.getId());
        }
    }

    /**
     * Update the language dependent displayed Strings in this class.
     * @param stringLoaderInterface the new stringLoaderInterface
     */
    public void updateStringRes(StringLoaderInterface stringLoaderInterface) {
        this.stringLoaderInterface = stringLoaderInterface;
        this.voterString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("VOTER");
        this.candidateString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("CANDIDATE");
        this.seatString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("SEAT");
        this.newVariableString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("newVariable");
        this.typeString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("type");
        this.errorString = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("errorString");
        this.nameNotMatchingError = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("nameNotMatchingError");
        this.alreadyExistsError = stringLoaderInterface.getBooleanExpEditorStringResProvider().
                getBooleanExpEditorSymbVarListRes().getStringFromID("alreadyExistsError");
        updateJlist();
    }

    /**
     * Getter for the SymbolicVariabelList object
     * @return symbolicVariableList
     */
    public SymbolicVariableList getSymbolicVariableList() {
        return symbolicVariableList;
    }


    private class RemoveVarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            int selectedIndex = jList.getSelectedIndex();
            if (selectedIndex >= 0) {
                symbolicVariableList.removeSymbolicVariable(selectedIndex);
                jListModel.remove(selectedIndex);
            }
        }
    }

    private class AddVarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ButtonGroup buttonGroup = new ButtonGroup();
            JRadioButton voterButton = new JRadioButton(voterString);
            voterButton.setSelected(true);
            JRadioButton candidatesButton = new JRadioButton(candidateString);
            JRadioButton seatsButton = new JRadioButton(seatString);
            buttonGroup.add(voterButton);
            buttonGroup.add(candidatesButton);
            buttonGroup.add(seatsButton);
            JTextField name = new JTextField();
            name.grabFocus();
            Object[] message = {
                    typeString + ":",
                    voterButton, candidatesButton, seatsButton,
                    "Name:", name
            };

            int option = JOptionPane.showConfirmDialog(booleanExpEditorWindow, message, newVariableString,
                    JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String errorCause = "";
                boolean validname = true;
                if (!name.getText().matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    validname = false;
                    errorCause = nameNotMatchingError;
                } else if (!SymbolicVarListController.this.symbolicVariableList.isVarIDAllowed(name.getText())) {
                    validname = false;
                    errorCause = alreadyExistsError;
                }
                if (validname) {
                    if (voterButton.isSelected()) {
                        InternalTypeContainer intTypeCont = new InternalTypeContainer(InternalTypeRep.VOTER);
                        SymbolicVarListController.this.symbolicVariableList.addSymbolicVariable(name.getText(),
                                intTypeCont);
                        jListModel.addElement(voterString + " " + name.getText());
                    } else if (candidatesButton.isSelected()) {
                        InternalTypeContainer intTypeCont = new InternalTypeContainer(InternalTypeRep.CANDIDATE);
                        SymbolicVarListController.this.symbolicVariableList.addSymbolicVariable(name.getText(),
                                intTypeCont);
                        jListModel.addElement(candidateString + " " + name.getText());
                    } else if (seatsButton.isSelected()) {
                        InternalTypeContainer intTypeCont = new InternalTypeContainer(InternalTypeRep.SEAT);
                        SymbolicVarListController.this.symbolicVariableList.addSymbolicVariable(name.getText(),
                                intTypeCont);
                        jListModel.addElement(seatString + " " + name.getText());

                    }
                } else {
                    Object errorMessage = errorString + "\n (" + errorCause + ")";
                    JOptionPane.showMessageDialog(booleanExpEditorWindow, errorMessage, "", JOptionPane.OK_OPTION);
                }
            }
        }
    }
}
