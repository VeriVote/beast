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
    /** The Constant LANG_OPTS. */
    private static final String LANG_OPTS = "lang_opts";
    /** The Constant LANG_ID. */
    private static final String LANG_ID = "lang";

    /** The Constant ENGLISH. */
    private static final String ENGLISH = "en";
    /** The Constant GERMAN. */
    private static final String GERMAN = "de";

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
        super(LANG_OPTS);
        this.sli = slInterf;
        final String chosenLang = stringResLoader.getStringFromID(LANG_ID);
        final List<String> availableLangsList = Arrays.asList(GERMAN, ENGLISH);
        langOptElem = new LanguageOptionElement(availableLangsList, chosenLang);
        addOptionElement(langOptElem);
    }

    /**
     * Instantiates a new language options.
     *
     * @param slInterf
     *            the sl interf
     */
    public LanguageOptions(final StringLoaderInterface slInterf) {
        super(LANG_OPTS);
        this.sli = slInterf;
        final ArrayList<String> availableLangsList = new ArrayList<String>();
        availableLangsList.add(GERMAN);
        langOptElem = new LanguageOptionElement(availableLangsList, GERMAN);
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
