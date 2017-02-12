package edu.pse.beast.options.ParametereditorOptions;

import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.options.Options;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertychecker.CheckerFactoryFactory;
import edu.pse.beast.propertychecker.PropertyChecker;
import edu.pse.beast.saverloader.OptionSaverLoader.OptionsSaverLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

public class ParametereditorOptions extends Options {
    private final LanguageOptions langOpts;
    private final ParameterEditor editor;
    private final PSECentralObjectProvider centralObjectProvider;
    private final CheckerOptionElement checkerOptElem;
    
    public ParametereditorOptions(
            StringResourceLoader loader,
            LanguageOptions langOpts,
            ParameterEditor editor, 
            PSECentralObjectProvider centralObjectProvider) {
        super("param_opts");
        this.langOpts = langOpts;
        subOptions.add(langOpts);        
        this.editor = editor;
        this.centralObjectProvider = centralObjectProvider;
        this.checkerOptElem = new CheckerOptionElement(
                CheckerFactoryFactory.getAvailableCheckerIDs(),
                loader.getIdForString("checker"));
        this.optElements.add(checkerOptElem);
    }
    
    public ParametereditorOptions(
            LanguageOptions langOpts,
            ParameterEditor editor, 
            PSECentralObjectProvider centralObjectProvider) {
        super("param_opts");
        this.langOpts = langOpts;
        subOptions.add(langOpts);        
        this.editor = editor;
        this.centralObjectProvider = centralObjectProvider;
        this.checkerOptElem = new CheckerOptionElement(CheckerFactoryFactory.getAvailableCheckerIDs(), "cbmc");
        this.optElements.add(checkerOptElem);
    }

    @Override
    protected void reapplySpecialized() {
        OptionsSaverLoaderInterface.saveOpt(langOpts); //needed since lang opts are loaded seperately
        centralObjectProvider.setCheckerCommunicator(new PropertyChecker(checkerOptElem.getChosenOption()));        
    }

}