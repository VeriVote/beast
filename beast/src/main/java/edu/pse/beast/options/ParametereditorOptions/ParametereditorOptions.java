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
    private final DeleteCFilesElement deleteElem;
    private final ParameterEditor editor;
    private final PSECentralObjectProvider centralObjectProvider;
    private final CheckerOptionElement checkerOptElem;

    private static boolean deleteFiles = false;

    public ParametereditorOptions(StringResourceLoader loader, LanguageOptions langOpts, ParameterEditor editor,
            PSECentralObjectProvider centralObjectProvider) {
        super("param_opts");
        this.langOpts = langOpts;
        this.subOptions.add(langOpts);
        this.editor = editor;
        this.centralObjectProvider = centralObjectProvider;
        this.checkerOptElem = new CheckerOptionElement(CheckerFactoryFactory.getAvailableCheckerIDs(),
                loader.getIdForString("checker"));
        this.optElements.add(checkerOptElem);
        this.deleteElem = new DeleteCFilesElement(java.util.Arrays.asList("not_keep_files", "keep_files"),
                loader.getIdForString("keep_files"));
        this.optElements.add(deleteElem);
    }

    public ParametereditorOptions(LanguageOptions langOpts, ParameterEditor editor,
            PSECentralObjectProvider centralObjectProvider) {
        super("param_opts");
        this.langOpts = langOpts;
        this.subOptions.add(langOpts);
        this.editor = editor;
        this.centralObjectProvider = centralObjectProvider;
        this.checkerOptElem = new CheckerOptionElement(CheckerFactoryFactory.getAvailableCheckerIDs(), "cbmc");
        this.optElements.add(checkerOptElem);
        this.deleteElem = new DeleteCFilesElement(java.util.Arrays.asList("not_keep_files", "keep_files"),
                "keep_files");
        this.optElements.add(deleteElem);
    }

    @Override
    protected void reapplySpecialized() {
        OptionsSaverLoaderInterface.saveOpt(langOpts); // needed since lang opts
                                                       // are loaded seperately
        
        centralObjectProvider.setCheckerCommunicator(new PropertyChecker(checkerOptElem.getChosenOption()));
        
        //extract if the user wants to keep the files, or delete them
        String choosenOption = deleteElem.getChosenOption();

        if (choosenOption.equals("not_keep_files")) {
            deleteFiles = true;
        } else {
            deleteFiles = false;
        }
    }

    public static boolean deleteTmpFiles() {
        return deleteFiles;
    }

}