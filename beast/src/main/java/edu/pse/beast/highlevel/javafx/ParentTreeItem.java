package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.Result;

/**
 * The Class ParentTreeItem.
 *
 * @author Lukas Stapelbroek
 */
public class ParentTreeItem extends CustomTreeItem {

    /** The Constant THREE. */
    private static final int THREE = 3;

    /** The prop name. */
    private Label propName;

    /** The check all. */
    private CheckBox checkAll = new CheckBox("check all");

    /** The context menu. */
    private ContextMenu contextMenu = new ContextMenu();

    /** The delete item. */
    private MenuItem deleteItem = new MenuItem("Delete Property");

    /** The sub items. */
    private final List<ChildTreeItem> subItems = new ArrayList<ChildTreeItem>();

    /** The child tree items. */
    private final List<TreeItem<CustomTreeItem>> childTreeItems =
            new ArrayList<TreeItem<CustomTreeItem>>();

    /** The prop desc. */
    private final PreAndPostConditionsDescription propDesc;

    /** The disabled. */
    private boolean disabled;

    /** The initialized. */
    private boolean initialized = false;

    /** The check item. */
    private ChildTreeItem checkItem;

    /** The margin item. */
    private ChildTreeItem marginItem;

    /** The test item. */
    private ChildTreeItem testItem;

    /** The counter. */
    private int counter;

    /**
     * Instantiates a new parent tree item.
     *
     * @param propertyDesc
     *            the property desc
     * @param isSelected
     *            the is selected
     * @param treeItemReference
     *            the tree item reference
     * @param createChildren
     *            the create children
     */
    ParentTreeItem(final PreAndPostConditionsDescription propertyDesc,
                   final boolean isSelected,
                   final TreeItem<CustomTreeItem> treeItemReference,
                   final boolean createChildren) {
        contextMenu.getItems().add(deleteItem);
        this.setTreeItemReference(treeItemReference);
        this.propDesc = propertyDesc;
        this.setAlignment(Pos.CENTER_LEFT);
        propName = new Label(propertyDesc.getName());
        this.getChildren().add(propName);
        this.getChildren().add(new Separator(Orientation.VERTICAL));
        this.getChildren().add(checkAll);
        checkItem = new CheckChildTreeItem("Verification ", this,
                new TreeItem<CustomTreeItem>());
        marginItem = new MarginChildTreeItem("Margin", this,
                new TreeItem<CustomTreeItem>());
        testItem =
                new TestChildTreeItem("Test", this,
                                      new TreeItem<CustomTreeItem>());
        if (createChildren) {
            subItems.add(checkItem);
            subItems.add(marginItem);
            subItems.add(testItem);
            addChildrenToStage();
        } else {
            subItems.add(null);
            subItems.add(null);
            subItems.add(null);
        }
        checkAll.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> ov,
                                final Boolean oldValue, final Boolean newValue) {
                checkBoxChanged(newValue);
            }
        });
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                MouseButton button = event.getButton();
                if (button == MouseButton.PRIMARY) {
                    wasClicked(true);
                }
            }
        });

        this.deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                GUIController.getController().removeProperty(treeItemReference);
            }
        });

        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(final ContextMenuEvent event) {
                contextMenu.show(ParentTreeItem.this, event.getScreenX(),
                                 event.getScreenY());
            }
        });

        this.setSelected(isSelected);
        this.getTreeItemReference().setValue(this);

        //
        // this.getChildren().add(new Separator(Orientation.VERTICAL));
        // this.getChildren().add(removeButton);
    }

    /**
     * Adds the children to stage.
     */
    public void addChildrenToStage() {
        for (Iterator<ChildTreeItem> iterator = subItems.iterator();
                iterator.hasNext();) {
            CustomTreeItem item = iterator.next();
            // TreeItem<CustomTreeItem> reference = new
            // TreeItem<CustomTreeItem>(item);
            childTreeItems.add(item.getTreeItemReference());
        }
        this.getTreeItemReference().getChildren().addAll(childTreeItems);
        initialized = true;
    }

    /**
     * Was clicked.
     *
     * @param bringToFront
     *            the bring to front
     */
    public void wasClicked(final boolean bringToFront) {
        GUIController.getController()
            .setCurrentPropertyDescription(this,
                                           bringToFront);
    }

    /**
     * Check box changed.
     *
     * @param state
     *            the state
     */
    private void checkBoxChanged(final boolean state) {
        if (initialized) {
            for (Iterator<ChildTreeItem> iterator = subItems
                    .iterator(); iterator.hasNext();) {
                ChildTreeItem childTreeItem = iterator.next();
                if (!disabled) {
                    childTreeItem.setSelected(state);
                }
                if (state) {
                    checkAll.setText("uncheck all");
                } else {
                    checkAll.setText("check all");
                }
            }
        }
    }

    /**
     * Checks if is selected.
     *
     * @return true, if is selected
     */
    public boolean isSelected() {
        return checkAll.isSelected();
    }

    /**
     * Sets the selected.
     *
     * @param state
     *            the new selected
     */
    public void setSelected(final boolean state) {
        checkAll.setSelected(state);
        checkBoxChanged(state);
    }

    /**
     * Gets the sub items.
     *
     * @return the sub items
     */
    public List<ChildTreeItem> getSubItems() {
        return subItems;
    }

    /**
     * Child checkbox changed.
     */
    public void childCheckboxChanged() {
        boolean acc = true;
        for (Iterator<ChildTreeItem> iterator =
                subItems.iterator();
                iterator.hasNext();) {
            ChildTreeItem item = iterator.next();
            acc = acc && item.isSelected();
        }
        if (acc) {
            this.setSelected(acc);
        } else {
            this.disabled = true;
            this.setSelected(false);
            this.disabled = false;
        }
    }

    /**
     * Gets the check item.
     *
     * @return the check item
     */
    public ChildTreeItem getCheckItem() {
        return checkItem;
    }

    /**
     * Gets the margin item.
     *
     * @return the margin item
     */
    public ChildTreeItem getMarginItem() {
        return marginItem;
    }

    /**
     * Sets the check result.
     *
     * @param result
     *            the new check result
     */
    public void setCheckResult(final Result result) {
        checkItem.addResult(result);
    }

    /**
     * Sets the margin result.
     *
     * @param result
     *            the new margin result
     */
    public void setMarginResult(final Result result) {
        marginItem.addResult(result);
    }

    /**
     * Sets the check status.
     *
     * @param status
     *            the new check status
     */
    public void setCheckStatus(final AnalysisStatus status) {
        // checkItem.setStatus(status);
    }

    /**
     * Sets the margin status.
     *
     * @param status
     *            the new margin status
     */
    public void setMarginStatus(final AnalysisStatus status) {
        // marginItem.setStatus(status);
    }

    /**
     * Gets the pre and post properties.
     *
     * @return the pre and post properties
     */
    public PreAndPostConditionsDescription getPreAndPostProperties() {
        return propDesc;
    }

    /**
     * Notifies the parent, that at least one of the children's result changed,
     * so we have have to check all to update the GUI.
     */
    public void update() {
        for (Iterator<ChildTreeItem> iterator = subItems.iterator(); iterator
                .hasNext();) {
            ChildTreeItem child = iterator.next();
            child.update();
        }
    }

    /**
     * Checks if is child selected.
     *
     * @return true, if at least one of the children has to be checked
     */
    public boolean isChildSelected() {
        boolean selected = false;
        for (Iterator<ChildTreeItem> iterator = subItems.iterator();
                iterator.hasNext();) {
            ChildTreeItem childItem = iterator.next();
            selected = selected || childItem.isSelected();
        }
        return selected;
    }

    /**
     * Adds the errors.
     *
     * @param combinedErrors
     *            the combined errors
     */
    public void addErrors(final List<CodeError> combinedErrors) {
        String errorText = "";
        for (Iterator<CodeError> iterator = combinedErrors.iterator();
                iterator.hasNext();) {
            CodeError codeError = iterator.next();
            String error = codeError.getLine() + " : " + codeError.getMsg()
                            + "\n";
            errorText += error;
        }
        GUIController.setErrorText(errorText);
    }

    /**
     * Sets the text.
     *
     * @param text
     *            the new text
     */
    public void setText(final String text) {
        propName.setText(text);
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return propName.getText();
    }

    /**
     * Adds the child.
     *
     * @param values
     *            the values
     * @param index
     *            the index
     */
    public void addChild(final ChildTreeItemValues values, final int index) {
        ChildTreeItem child = null;
        switch (index) {
        case 0:
            child = new CheckChildTreeItem(values, this,
                    new TreeItem<CustomTreeItem>());
            break;
        case 1:
            child = new MarginChildTreeItem(values, this,
                    new TreeItem<CustomTreeItem>());
            break;
        case 2:
            child = new TestChildTreeItem(values, this,
                    new TreeItem<CustomTreeItem>());
            break;
        default:
            counter--;
            break;
        }
        subItems.set(index, child);
        counter++;
        if (counter == THREE) {
            addChildrenToStage();
        }
    }

    /**
     * Gets the counter.
     *
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }
}
