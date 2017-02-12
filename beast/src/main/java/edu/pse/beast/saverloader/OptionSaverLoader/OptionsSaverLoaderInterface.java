package edu.pse.beast.saverloader.OptionSaverLoader;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.options.BooleanExpCodeAreaOptions;
import edu.pse.beast.options.BooleanExpEditorOptions;
import edu.pse.beast.options.CElectionCodeAreaOptions;
import edu.pse.beast.options.CElectionEditorOptions;
import edu.pse.beast.options.CodeAreaOptions;
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
import java.util.List;

/**
*
* @author Justin
*/
public class OptionsSaverLoaderInterface {
    public static LanguageOptions loadLangOpts(StringLoaderInterface sli) throws IOException {
        StringResourceLoader loader = new StringResourceLoader(loadStringResForResLoader("lang_opts"));
        return new LanguageOptions(sli, loader);
    }
    
    public static CElectionEditorOptions loadCEditorOpts(CElectionDescriptionEditor editor) throws IOException {
        LinkedList<String> saveString = loadStringResForResLoader("ceditor_opts");
        CElectionCodeAreaOptions ccodeAreaOpts = loadCElectionCodeAreaOps(editor.getCodeArea(), saveString);    
                
        return new CElectionEditorOptions(editor, ccodeAreaOpts);
    }
    
    private static CElectionCodeAreaOptions loadCElectionCodeAreaOps(CElectionCodeArea area, LinkedList<String> saveString) {
        CodeAreaOptions codeAreaOpts = loadCodeAreaOpts(area, saveString);
        CElectionCodeAreaOptions created = new CElectionCodeAreaOptions(area, codeAreaOpts);
        return created;
    }
    
    private static CodeAreaOptions loadCodeAreaOpts(CodeArea area, LinkedList<String> saveString) {
        LinkedList<String> onlySaveString = getSaveStringOnlyForOption("codearea_opts", saveString);
        StringResourceLoader loader = new StringResourceLoader(onlySaveString);
        return new CodeAreaOptions(area, loader);
    }    
    
    public static BooleanExpEditorOptions loadBooleanExpEditorOpts(BooleanExpEditor editor) throws IOException {
        LinkedList<String> saveString = loadStringResForResLoader("booleanexpeditor_ops");
        BooleanExpCodeAreaOptions opts = loadBooleanExpCodeAreaOpts(editor, saveString);
        BooleanExpEditorOptions created = new BooleanExpEditorOptions(editor, opts);
        return created;
    }
    
    private static BooleanExpCodeAreaOptions loadBooleanExpCodeAreaOpts(
            BooleanExpEditor editor,
            LinkedList<String> saveString) {
        CodeAreaOptions codeAreaOpts = loadCodeAreaOpts(editor.getPrePropCodeArea(), saveString);
        BooleanExpCodeAreaOptions created = new BooleanExpCodeAreaOptions(editor, codeAreaOpts);
        return created;
    }
    
    private static LinkedList<String> getSaveStringOnlyForOption(String id, LinkedList<String> saveString) {
        LinkedList<String> created = new LinkedList<>();
        int i = 0;
        
        for(; i < saveString.size() && saveString.get(i).contains("new_option " + id); ++i) {            
        }
        for(; i < saveString.size() - 1 && saveString.get(i + 1).contains("new_option "); ++i) {  
            created.add(saveString.get(i));
        } 
        return created;                
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