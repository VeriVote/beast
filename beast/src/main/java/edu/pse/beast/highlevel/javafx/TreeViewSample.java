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

/**
 * The Class TreeViewSample.
 *
 * @author Lukas Stapelbroek
 */
public final class TreeViewSample extends Application {
    /** The Constant ROOT. */
    private static final String ROOT = "root.png";

    /** The Constant DEPARTMENT. */
    private static final String DEPARTMENT = "department.png";

    /** The root icon. */
    private final Node rootIcon =
            new ImageView(new Image(getClass().getResourceAsStream(ROOT)));

    /** The dep icon. */
    private final Image depIcon =
            new Image(getClass().getResourceAsStream(DEPARTMENT));

    /** The employees. */
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
                    new Employee("Isabella Johnson", "Accounts Department")
                    );

    /** The root node. */
    private TreeItem<String> rootNode =
            new TreeItem<String>("MyCompany Human Resources", rootIcon);

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        rootNode.setExpanded(true);
        for (final Employee employee : employees) {
            TreeItem<String> empLeaf = new TreeItem<String>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue()
                        .contentEquals(employee.getDepartment())) {
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
        treeView.setCellFactory(
                new Callback<TreeView<String>, TreeCell<String>>() {
                    @Override
                    public TreeCell<String> call(final TreeView<String> p) {
                        return new TextFieldTreeCellImpl();
                    }
                });

        box.getChildren().add(treeView);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The Class TextFieldTreeCellImpl.
     */
    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        /** The text field. */
        private TextField textField;

        /**
         * Instantiates a new text field tree cell impl.
         */
        TextFieldTreeCellImpl() { }

        /**
         * Start edit.
         */
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

        /**
         * Cancel edit.
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        /**
         * Update item.
         *
         * @param item
         *            the item
         * @param empty
         *            the empty
         */
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

        /**
         * Creates the text field.
         */
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

        /**
         * Gets the string.
         *
         * @return the string
         */
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    /**
     * The Class Employee.
     */
    public static final class Employee {

        /** The name. */
        private final SimpleStringProperty name;

        /** The department. */
        private final SimpleStringProperty department;

        /**
         * Instantiates a new employee.
         *
         * @param nameString
         *            the name string
         * @param departmentString
         *            the department string
         */
        private Employee(final String nameString,
                         final String departmentString) {
            this.name = new SimpleStringProperty(nameString);
            this.department = new SimpleStringProperty(departmentString);
        }

        /**
         * Gets the name.
         *
         * @return the name
         */
        public String getName() {
            return name.get();
        }

        /**
         * Sets the name.
         *
         * @param fName
         *            the new name
         */
        public void setName(final String fName) {
            name.set(fName);
        }

        /**
         * Gets the department.
         *
         * @return the department
         */
        public String getDepartment() {
            return department.get();
        }

        /**
         * Sets the department.
         *
         * @param fName
         *            the new department
         */
        public void setDepartment(final String fName) {
            department.set(fName);
        }
    }
}
