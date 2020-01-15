package edu.pse.beast.highlevel.javafx;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeViewSample extends Application {
    private static final String ROOT = "root.png";
    private static final String DEPARTMENT = "department.png";

    private final Node rootIcon =
            new ImageView(new Image(getClass().getResourceAsStream(ROOT)));
    private final Image depIcon =
            new Image(getClass().getResourceAsStream(DEPARTMENT));
    private List<Employee> employees =
            Arrays.<Employee>asList(
                    new Employee("Ethan Williams", "Sales Department"),
                    new Employee("Emma Jones", "Sales Department"),
                    new Employee("Michael Brown", "Sales Department"),
                    new Employee("Anna Black", "Sales Department"),
                    new Employee("Rodger York", "Sales Department"),
                    new Employee("Susan Collins", "Sales Department"),
                    new Employee("Mike Graham", "IT Support"),
                    new Employee("Judy Mayer", "IT Support"),
                    new Employee("Gregory Smith", "IT Support"),
                    new Employee("Jacob Smith", "Accounts Department"),
                    new Employee("Isabella Johnson", "Accounts Department"));
    private TreeItem<String> rootNode =
            new TreeItem<String>("MyCompany Human Resources", rootIcon);

    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        rootNode.setExpanded(true);
        for (Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<String>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(employee.getDepartment())) {
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> depNode =
                        new TreeItem<String>(employee.getDepartment(),
                                             new ImageView(depIcon));
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }

        stage.setTitle("Tree View Sample");
        VBox box = new VBox();
        final Scene scene = new Scene(box, 400, 300);
        scene.setFill(Color.LIGHTGRAY);

        TreeView<String> treeView = new TreeView<String>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(final TreeView<String> p) {
                return new TextFieldTreeCellImpl();
            }
        });

        box.getChildren().add(treeView);
        stage.setScene(scene);
        stage.show();
    }

    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        private TextField textField;

        TextFieldTreeCellImpl() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(final String item, final boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

                @Override
                public void handle(final KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static final class Employee {

        private final SimpleStringProperty name;
        private final SimpleStringProperty department;

        private Employee(final String nameString, final String departmentString) {
            this.name = new SimpleStringProperty(nameString);
            this.department = new SimpleStringProperty(departmentString);
        }

        public String getName() {
            return name.get();
        }

        public void setName(final String fName) {
            name.set(fName);
        }

        public String getDepartment() {
            return department.get();
        }

        public void setDepartment(final String fName) {
            department.set(fName);
        }
    }
}
