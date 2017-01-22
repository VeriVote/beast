package edu.pse.beast.options;

import java.util.List;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;

public class LanguageOptions extends Options {
    private final StringLoaderInterface sli;
    private final List<DisplaysStringsToUser> stringDisplays;
    private final LanguageOptionElement languages;

    /**
     * 
     * @param id the id
     * @param sli the string loader interface 
     * @param stringDisplays the display strings
     * @param languages the language
     */
    public LanguageOptions(String id, StringLoaderInterface sli, List<DisplaysStringsToUser> stringDisplays,
            LanguageOptionElement languages) {
        super(id);
        this.sli = sli;
        this.stringDisplays = stringDisplays;
        this.languages = languages;
    }

    /**
     * 
     * @return the string loader interface 
     */
    public StringLoaderInterface getSli() {
        return sli;
    }

    /**
     * 
     * @return the display strings
     */
    public List<DisplaysStringsToUser> getStringDisplays() {
        return stringDisplays;
    }

    /**
     * 
     * @return the language
     */
    public LanguageOptionElement getLanguages() {
        return languages;
    }

    @Override
    public void reapply() {
        // TODO Auto-generated method stub

    }
}
