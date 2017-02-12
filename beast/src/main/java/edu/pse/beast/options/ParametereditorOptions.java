package edu.pse.beast.options;

import edu.pse.beast.saverloader.OptionSaverLoader.OptionsSaverLoaderInterface;

public class ParametereditorOptions extends Options {
    private final LanguageOptions langOpts;

    /**
     * 
     * @param id the id
     * @param langOpts the language option
     * @param availableCheckerList the available checkers
     * @param checker the checkerids
     * 
     */
    public ParametereditorOptions(LanguageOptions langOpts) {
        super("param_opts");
        this.langOpts = langOpts;
        subOptions.add(langOpts);
    }

    @Override
    protected void reapplySpecialized() {
        OptionsSaverLoaderInterface.saveOpt(langOpts);
    }

}