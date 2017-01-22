package edu.pse.beast.BooleanExpEditor;

import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 * Creates a BooleanExpEditor instance and displays it.
 * @author Nikolai
 */
public class WindowStarterTestClass {
    public static void main(String[] args) {
        BooleanExpEditorBuilder booleanExpEditorBuilder = new BooleanExpEditorBuilder();
        BooleanExpEditor editor = booleanExpEditorBuilder.createBooleanExpEditorObject(
                new ObjectRefsForBuilder(null,
                        new StringLoaderInterface("de"),
                        null,
                        null));
        editor.showWindow();
    }
}