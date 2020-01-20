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
 *
 * @author Lukas Stapelbroek
 */
public final class LanguageOptions extends Options {
    /** The sli. */
    private StringLoaderInterface sli;

    /** The string displays. */
    private List<DisplaysStringsToUser> stringDisplays =
            new ArrayList<DisplaysStringsToUser>();

    /** The lang opt elem. */
    private LanguageOptionElement langOptElem;

    /**
     * Constructor.
     *
     * @param slInterf
     *            the string loader interface
     * @param stringResLoader
     *            StringResourceLoader
     */
    public LanguageOptions(final StringLoaderInterface slInterf,
                           final StringResourceLoader stringResLoader) {
        super("lang_opts");
        this.sli = slInterf;
        String chosenLang = stringResLoader.getStringFromID("lang");
        List<String> choosableLangsList = Arrays.asList("de", "en");
        langOptElem = new LanguageOptionElement(choosableLangsList, chosenLang);
        addOptionElement(langOptElem);
    }

    /**
     * Instantiates a new language options.
     *
     * @param slInterf
     *            the sl interf
     */
    public LanguageOptions(final StringLoaderInterface slInterf) {
        super("lang_opts");
        this.sli = slInterf;
        ArrayList<String> availableLangsList = new ArrayList<String>();
        availableLangsList.add("de");
        langOptElem = new LanguageOptionElement(availableLangsList, "de");
        addOptionElement(langOptElem);
    }

    /**
     * Method with which Builder classes can add classes implementing
     * DisplaysStringsToUser to the stringDisplays list.
     *
     * @param dis
     *            the class implementing DisplaysStringsToUser
     */
    public void addStringDisplayer(final DisplaysStringsToUser dis) {
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
