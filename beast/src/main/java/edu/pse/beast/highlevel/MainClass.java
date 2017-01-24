package edu.pse.beast.highlevel;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.BooleanExpEditorBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditorBuilder;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.options.OptionsInterface;
import edu.pse.beast.propertylist.PropertyListBuilder;
import edu.pse.beast.parametereditor.ParameterEditorWindowStarter;
import edu.pse.beast.saverloader.FileLoader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

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
    public static void main(String[] args) throws URISyntaxException, IOException {
        
        AbstractBeastFactory fac = new PSEBeastFactory();
        
        BEASTCommunicator communicator = new BEASTCommunicator(fac);
          
    }
}
