package edu.pse.beast.highlevel;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditorBuilder;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.propertylist.PropertyListBuilder;
import toBeImplemented.LanguageOptions;
import edu.pse.beast.parametereditor.ParameterEditorWindowStarter;

/**
 * The MainClass creates an AbstractBeastFactory and with it a BEASTCommunicator
 * which starts all processes to run BEAST.
 *
 * @author Jonas
 */
public class MainClass {

    /**
     * Starts BEAST
     *
     * @param args
     */
    public static void main(String[] args) {
        OptionsInterface opt = new OptionsInterface();
        ObjectRefsForBuilder objRefsForBuilder = new ObjectRefsForBuilder(new toBeImplemented.OptionsInterface(),
                new StringLoaderInterface("de"),
                new toBeImplemented.LanguageOptions(), new SaverLoaderInterface());

        CElectionDescriptionEditorBuilder cElectionEditorBuilder = new CElectionDescriptionEditorBuilder();
        cElectionEditorBuilder.createCElectionDescriptionEditor(objRefsForBuilder);
        
        PropertyListBuilder propertyBuilder = new PropertyListBuilder();
        propertyBuilder.createPropertyList(objRefsForBuilder);
        
        BooleanExpEditorBuilder booleanBuilder = new BooleanExpEditorBuilder();
        
        BooleanExpEditor booleanEditor = booleanBuilder.createBooleanExpEditorObject(objRefsForBuilder);
        booleanEditor.showWindow();
        
        ParameterEditorWindowStarter paramsStarter = new ParameterEditorWindowStarter();
        paramsStarter.start();
            
    }
}
