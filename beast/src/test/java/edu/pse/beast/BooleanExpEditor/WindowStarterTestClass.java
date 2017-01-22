package edu.pse.beast.BooleanExpEditor;

import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import toBeImplemented.LanguageOptions;

/**
 * Class to test creation of BooleanExpEditor object and displaying of its BooleanExpEditorWindow object.
 * @author Nikolai
 */
public class WindowStarterTestClass {
    public static void main(String[] args) {
        BooleanExpEditorBuilder builder = new BooleanExpEditorBuilder();
        ObjectRefsForBuilder objectRefsForBuilder = new ObjectRefsForBuilder(new OptionsInterface(),
                new StringLoaderInterface("de"),
                new LanguageOptions(), new SaverLoaderInterface());
        BooleanExpEditor editor = builder.createBooleanExpEditorObject(objectRefsForBuilder);
        editor.showWindow();
    }
}
