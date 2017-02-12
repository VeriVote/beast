package edu.pse.beast.options;

import java.util.List;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import java.util.ArrayList;

public class LanguageOptions extends Options {
    private StringLoaderInterface sli;
    private List<DisplaysStringsToUser> stringDisplays = new ArrayList<>();
    private LanguageOptionElement langOptElem;
    /**
     * 
     * @param id the id
     * @param sli the string loader interface 
     * @param stringDisplays the display strings
     * @param languages the language
     */
    public LanguageOptions(StringLoaderInterface sli,
            StringResourceLoader stringResLoader) {
        super("lang_opts");
        this.sli = sli;
        String chosenLang = stringResLoader.getStringFromID("lang");
        String choosableLangs[] = stringResLoader.getStringFromID("choosable_lang").split(",");
        ArrayList<String> choosableLangsList = new ArrayList<>();
        for (int i = 0; i < choosableLangs.length; i++) {
            choosableLangsList.add(choosableLangs[i]);
        }
        langOptElem = new LanguageOptionElement(choosableLangsList, chosenLang);
        optElements.add(langOptElem);
    }
    
    public void addStringDisplayer(DisplaysStringsToUser dis) {
        stringDisplays.add(dis);
    }


    @Override
    protected void reapplySpecialized() {
        sli.setLanguage(langOptElem.chosenOption);
        for(DisplaysStringsToUser dis : stringDisplays) {
            dis.updateStringRes(sli);
        }
    }
}
