package edu.pse.beast.propertylist.View;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.stringresource.PropertyListStringResProvider;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Represents the view for a PropertyItem.
 * @author Justin
 */
@SuppressWarnings("serial")
public class ListItem extends JPanel implements DisplaysStringsToUser {

    PLModel model;
    PropertyList controller;

    private boolean reactsToInput;
    private StringLoaderInterface sli;

    protected ResultPresenterWindow resWindow;
    private PropertyItem prop;

    protected JButton showResult = new JButton();
    protected JTextField name = new JTextField();
    protected JCheckBox testStatus = new JCheckBox();
    protected JCheckBox marginComputation = new JCheckBox();
    protected JButton changeButton = new JButton();
    protected JButton deleteButton = new JButton();
    
    private final String pathToEye = "/core/images/other/eye.png";
    private final ImageIcon eyeIcon = new ImageIcon(SuperFolderFinder.getSuperFolder() + pathToEye);
    
    private final String pathToWrench = "/core/images/other/wrench.png";
    private final ImageIcon wrenchIcon = new ImageIcon(SuperFolderFinder.getSuperFolder() + pathToWrench);
    
    private final String pathToXMark = "/core/images/other/x-mark.png";
    private final Icon xMarkIcon = new ImageIcon(SuperFolderFinder.getSuperFolder() + pathToXMark);

    /**
     * Constructor
     * @param controller The controller of PropertyList
     * @param model The model of PropertyList
     * @param prop The PropertyItem to be viewed
     */
    public ListItem(PropertyList controller, PLModel model, PropertyItem prop) {
        this.model = model;
        this.controller = controller;
        this.prop = prop;
        reactsToInput = true;
        sli = new StringLoaderInterface("en");
        resWindow = new ResultPresenterWindow(sli);
        init();
    } 
    
    private void init() {
        this.setMaximumSize(new Dimension(500, 2000));
        Dimension iconSize = new Dimension(40, 40);

        showResult.setPreferredSize(new Dimension(80, 40));
        showResult.setIcon(eyeIcon);
        showResult.setBackground(presentColor());
        showResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point pos = showResult.getLocationOnScreen();
                resWindow.setLocation((int) pos.getX() - 30, (int) pos.getY() - 35); // good alignment for the result window
                resWindow.getShowResult().setBackground(presentColor());
                passMessageToResultWindow();
                resWindow.setVisible(true);
            }
        });
        this.add(showResult, BorderLayout.LINE_START);

        name.setPreferredSize(new Dimension(200, 30));
        name.setText(prop.getDescription().getName());
        name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (reactsToInput) {
                    controller.changeName(prop, name.getText());
                }
            }
        });
        name.addFocusListener(new FocusListener() { // change name as well when focus was lost
			@Override
			public void focusGained(FocusEvent fe) {}

			@Override
			public void focusLost(FocusEvent fe) {
				controller.changeName(prop, name.getText());
			}
        });
        this.add(name, BorderLayout.LINE_START);

        testStatus.setText("Check");
        testStatus.setSelected(prop.getTestStatus());
        testStatus.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (reactsToInput) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        controller.setTestStatus(prop, true);
                    } else {
                        controller.setTestStatus(prop, false);
                    }
                }
            }
        });
        this.add(testStatus, BorderLayout.LINE_START);
        
        
        marginComputation.setText("margin");
        marginComputation.setSelected(prop.getMarginStatus());
        testStatus.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (reactsToInput) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        controller.setTestStatus(prop, true);
                    } else {
                        controller.setTestStatus(prop, false);
                    }
                }
            }
        });
        this.add(testStatus, BorderLayout.LINE_START);
        

        changeButton.setPreferredSize(iconSize);
        changeButton.setIcon(wrenchIcon);
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reactsToInput) {
                    controller.editProperty(prop);
                }
            }
        });
        this.add(changeButton, BorderLayout.LINE_START);

        deleteButton.setPreferredSize(iconSize);
        deleteButton.setIcon(xMarkIcon);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reactsToInput) {
                    controller.deleteProperty(prop);
                }
            }
        });
        this.add(deleteButton, BorderLayout.LINE_START);

    }
    
    
    @Override
    public void updateStringRes(StringLoaderInterface sli) {
        this.sli = sli;
        if (sli == null) return;
        PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
        StringResourceLoader other = provider.getOtherStringRes();

        this.testStatus.setText(other.getStringFromID("check"));
        this.revalidate();
        this.repaint();
    }

    
    // getter and setter
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
    
    // private methods
    private Color presentColor() {
        switch (prop.getResultType()) {
            case SUCCESS:
                return Color.GREEN;
            case TIMEOUT:
                return Color.ORANGE;
            case CANCEL:
                return Color.ORANGE;
            case FAILURE:
                return Color.MAGENTA;
            case FAILUREEXAMPLE:
                return Color.RED;
            case UNTESTED:
                return Color.GRAY;
            default:
                return deleteButton.getBackground();
        }
    }

    private void passMessageToResultWindow() {
        switch (prop.getResultType()) {
            case SUCCESS:
                resWindow.presentSuccess();
                break;
            case TIMEOUT:
                resWindow.presentTimeOut();
                break;
            case CANCEL:
                resWindow.presentCancel();
                break;
            case FAILURE:
                resWindow.presentFailure(prop.getError());
                break;
            case FAILUREEXAMPLE:
                resWindow.presentFailureExample(prop.getExample());
                break;
            case UNTESTED:
                resWindow.resetResult();
                break;
            default:
                break;
        }
    }

}
