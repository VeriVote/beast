package edu.pse.beast.saverloader.OptionSaverLoader;

import edu.pse.beast.options.LanguageOptions;
import edu.pse.beast.options.OptionElement;
import edu.pse.beast.options.Options;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
*
* @author Justin
*/
public class OptionsSaverLoaderInterface {
    public static LanguageOptions loadLangOpts(StringLoaderInterface sli) throws IOException {
        StringResourceLoader loader = new StringResourceLoader(loadStringResForResLoader("lang_opts"));
        return new LanguageOptions(sli, loader);
    }
    
    private static LinkedList<String> loadStringResForResLoader(String optName) throws IOException {
        File f = new File("core/option_saves/" + optName + ".txt");
        return FileLoader.loadFileAsString(f);
    }
    
    public static void saveOpt(Options opt) {
        ArrayList<String> saveString = new ArrayList<String>();
        saveOptRec(opt, saveString);
        FileSaver.writeStringLinesToFile(saveString, new File("core/option_saves/" + opt.getId() + ".txt"));
    }
    
    private static void saveOptRec(Options opt, ArrayList<String> saveString) {
        for(OptionElement elem : opt.getOptionElements()) {
            saveString.add(elem.getID() + " : " + elem.getChosenOption());
        }
        for(Options subOptions : opt.getSubOptions()) {
            saveString.add("new_option " + subOptions.getId());        
            saveOptRec(subOptions, saveString);
        }
    }
}