package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.Result;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class ChildTreeItem extends CustomTreeItem {

    private Label propName;
    private CheckBox checkBox = new CheckBox();
    private final ParentTreeItem parent;
    // private ImageView statusIcon = new ImageView();
    private boolean disabled = false;

    protected ArrayList<ResultTreeItem> results =
            new ArrayList<ResultTreeItem>();

    private final List<TreeItem<CustomTreeItem>> resultTreeItems =
            new ArrayList<TreeItem<CustomTreeItem>>();

    ChildTreeItem(ChildTreeItemValues values, ParentTreeItem parent,
                  TreeItem<CustomTreeItem> treeItemReference) {
        this.parent = parent;
        this.checkBox.setSelected(values.checkBoxStatus);
        this.propName = new Label(values.propertyName);
        this.disabled = values.disabled;
        this.setTreeItemReference(treeItemReference);
        treeItemReference.setValue(this);
        for (Iterator<Result> iterator = values.results.iterator(); iterator.hasNext();) {
            Result result = (Result) iterator.next();
            addResult(result);
        }
        init();
    }

    ChildTreeItem(String name, ParentTreeItem parent,
                  TreeItem<CustomTreeItem> treeItemReference) {
        this.parent = parent;
        propName = new Label(name);
        this.setTreeItemReference(treeItemReference);
        treeItemReference.setValue(this);
        init();
    }

    private void init() {
        this.setAlignment(Pos.CENTER_LEFT);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean oldValue,
                                Boolean newValue) {
                if (!disabled) {
                    parent.childCheckboxChanged();
                }
            }
        });
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wasClicked();
            }
        });
        this.getChildren().add(checkBox);
        this.getChildren().add(new Separator(Orientation.VERTICAL));
        this.getChildren().add(propName);
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    private void wasClicked() {
        parent.wasClicked(false);
        if (results.size() > 0) {
            results.get(results.size() - 1).wasClicked();
        }
    }

    /**
     * sets the value of the checkbox, while disabling the listener for the checkbox
     * beforehand, and re-enabling it afterwards
     *
     * @param state new checkbox value
     */
    public void setSelected(boolean state) {
        this.disabled = true;
        checkBox.setSelected(state);
        this.disabled = false;
    }

    public void addResult(Result result) {
        ResultTreeItem resultItem = new ResultTreeItem(result, this);
        results.add(resultItem);
        TreeItem<CustomTreeItem> reference = new TreeItem<CustomTreeItem>(resultItem);
        this.getTreeItemReference().getChildren().add(reference);
        // this.getChildren().add(resultItem);
        addChildrenToStage();
        this.update();
    }
    //
    // public void setStatus(AnalysisStatus status) {
    // this.status = status;
    // checkBox.setText(status.toString());
    // }

    // public Result getResult() {
    // return result;
    // }

    /**
     * notifies the child object that its result object changed, so it has to update
     * its gui
     */
    public void update() {
    }

    public abstract void resetResult(Result result);

    public abstract AnalysisType getAnalysisType();

    public PreAndPostConditionsDescription getPreAndPostProperties() {
        return parent.getPreAndPostProperties();
    }

    // public void setPresentable() {
    //
    // if (result != null && result.isFinished()) {
    // if (!result.isValid()) {
    // this.setBackground(new Background(new BackgroundFill(Color.YELLOW,
    // CornerRadii.EMPTY, Insets.EMPTY)));
    // } else {
    // if (result.isSuccess()) {
    // this.setBackground(
    // new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY,
    // Insets.EMPTY)));
    // } else {
    // this.setBackground(new Background(new BackgroundFill(Color.RED,
    // CornerRadii.EMPTY, Insets.EMPTY)));
    // }
    // }
    // }
    //
    // }
    //
    public void resetPresentable() {
        this.setBackground(
                new Background(
                        new BackgroundFill(Color.WHITE,
                                           CornerRadii.EMPTY,
                                           Insets.EMPTY)));
    }

    public ChildTreeItemValues getValues() {
        ArrayList<Result> tmpList = new ArrayList<Result>();
        for (Iterator<ResultTreeItem> iterator = results.iterator();
                iterator.hasNext();) {
            ResultTreeItem result = (ResultTreeItem) iterator.next();
            tmpList.add(result.getResult());
        }
        return new ChildTreeItemValues(propName.getText(),
                                       checkBox.isSelected(),
                                       false, tmpList);
    }

    public void addChildrenToStage() {
        resultTreeItems.clear();
        TreeItem<CustomTreeItem> item2 = this.getTreeItemReference();
        item2.isExpanded();
        ObservableList<TreeItem<CustomTreeItem>> children2 = item2.getChildren();
        children2.size();
        this.getTreeItemReference().getChildren().clear();
        for (Iterator<ResultTreeItem> iterator = results.iterator();
                iterator.hasNext();) {
            ResultTreeItem item = (ResultTreeItem) iterator.next();
            item.setPresentable();
            resultTreeItems.add(new TreeItem<CustomTreeItem>(item));
        }
        this.getTreeItemReference().getChildren().addAll(resultTreeItems);
    }

    public void deleteResult(ResultTreeItem resultTreeItem) {
        results.remove(resultTreeItem);
        addChildrenToStage();
    }
}