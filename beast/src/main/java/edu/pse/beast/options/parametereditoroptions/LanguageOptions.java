package edu.pse.beast.options.parametereditoroptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.options.Options;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;

/**
 * Options subclass for the language options.
 */
public class LanguageOptions extends Options {
    private StringLoaderInterface sli;
    private List<DisplaysStringsToUser> stringDisplays = new ArrayList<>();
    private LanguageOptionElement langOptElem;

    /**
     * Constructor
     *
     * @param sli             the string loader interface
     * @param stringResLoader StringResourceLoader
     */
    public LanguageOptions(StringLoaderInterface sli, StringResourceLoader stringResLoader) {
        super("lang_opts");
        this.sli = sli;
        String chosenLang = stringResLoader.getStringFromID("lang");
        List<String> choosableLangsList = Arrays.asList("de", "en");
        langOptElem = new LanguageOptionElement(choosableLangsList, chosenLang);
        addOptionElement(langOptElem);
    }

    public LanguageOptions(StringLoaderInterface sli) {
        super("lang_opts");
        this.sli = sli;
        ArrayList<String> choosableLangsList = new ArrayList<>();
        choosableLangsList.add("de");
        langOptElem = new LanguageOptionElement(choosableLangsList, "de");
        addOptionElement(langOptElem);
    }

    /**
     * Method with which Builder classes can add classes implementing
     * DisplaysStringsToUser to the stringDisplays list.
     *
     * @param dis the class implementing DisplaysStringsToUser
     */
    public void addStringDisplayer(DisplaysStringsToUser dis) {
        stringDisplays.add(dis);
    }

    @Override
    protected void reapplySpecialized() {
        sli.setLanguage(langOptElem.getChosenOption());
        for (DisplaysStringsToUser dis : stringDisplays) {
            dis.updateStringRes(sli);
        }
    }
}