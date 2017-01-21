package edu.pse.beast.BooleanExpEditor;

import edu.pse.beast.booleanexpeditor.BooleanExpEditorWindow;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorWindowStarter;
import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * @author Nikolai
 */
public class WindowStarterTestClass {
    public static void main(String[] args) {
        BooleanExpEditorWindow window = new BooleanExpEditorWindow(new StringLoaderInterface("de"));
        BooleanExpEditorWindowStarter windowStarter = new BooleanExpEditorWindowStarter(window);
        windowStarter.showWindow();
    }
}
