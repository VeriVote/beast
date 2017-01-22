package edu.pse.beast.options;

import toBeImplemented.CheckerList;
import toBeImplemented.LanguageOptions;

public class ParametereditorOptions extends Options {
    private final CheckerOptionElement checker;
    private final CheckerList availableCheckerList;
    private final LanguageOptions langOpts;

    /**
     * 
     * @param id the id
     * @param langOpts the language option
     * @param availableCheckerList the available checkers
     * @param checker the checkerids
     * 
     */
    public ParametereditorOptions(String id, CheckerOptionElement checker, CheckerList availableCheckerList,
            LanguageOptions langOpts) {
        super(id);
        this.checker = checker;
        this.availableCheckerList = availableCheckerList;
        this.langOpts = langOpts;
    }

    /**
     * 
     * @return the checkerids
     */
    public CheckerOptionElement getChecker() {
        return checker;
    }

    /**
     * 
     * @return the checkerids
     */
    public CheckerList getAvailableCheckerList() {
        return availableCheckerList;
    }

    /**
     * 
     * @return the language options
     */
    public LanguageOptions getLanguageOptions() {
        return langOpts;
    }

    @Override
    public void reapply() {
        // TODO Auto-generated method stub

    }

}