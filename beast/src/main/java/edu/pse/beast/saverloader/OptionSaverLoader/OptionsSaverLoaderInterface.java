//package edu.pse.beast.saverloader.OptionSaverLoader;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedList;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
//import edu.pse.beast.codearea.CodeArea;
//import edu.pse.beast.highlevel.PSECentralObjectProvider;
//import edu.pse.beast.options.OptionElement;
//import edu.pse.beast.options.Options;
//import edu.pse.beast.options.BooleanExpEditorOptions.BooleanExpCodeAreaOptions;
//import edu.pse.beast.options.BooleanExpEditorOptions.BooleanExpEditorOptions;
//import edu.pse.beast.options.CEditorOptions.CElectionCodeAreaOptions;
//import edu.pse.beast.options.CEditorOptions.CElectionEditorOptions;
//import edu.pse.beast.options.CodeAreaOptions.CodeAreaOptions;
//import edu.pse.beast.options.ParametereditorOptions.LanguageOptions;
//import edu.pse.beast.options.ParametereditorOptions.ParametereditorOptions;
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.FileLoader;
//import edu.pse.beast.toolbox.FileSaver;
//import edu.pse.beast.toolbox.SuperFolderFinder;
// TODO rewrite for the new GUI
///**
//*
//* @author Holger
//*/
//public class OptionsSaverLoaderInterface {
//    public static LanguageOptions loadLangOpts(StringLoaderInterface sli) throws IOException {
//        StringResourceLoader loader = new StringResourceLoader(
//                getSaveStringOnlyForOption("lang_opts",
//                loadStringResForResLoader("lang_opts")));
//        return new LanguageOptions(sli, loader);
//    }
//    
//    public static CElectionEditorOptions loadCEditorOpts(CElectionDescriptionEditor editor) throws IOException {
//        LinkedList<String> saveString = loadStringResForResLoader("ceditor_opts");
//        CElectionCodeAreaOptions ccodeAreaOpts = loadCElectionCodeAreaOps(editor.getCodeArea(), saveString);    
//                
//        return new CElectionEditorOptions(editor, ccodeAreaOpts);
//    }
//    
//    private static CElectionCodeAreaOptions loadCElectionCodeAreaOps(CElectionCodeArea area, LinkedList<String> saveString) {
//        CodeAreaOptions codeAreaOpts = loadCodeAreaOpts(area, saveString);
//        CElectionCodeAreaOptions created = new CElectionCodeAreaOptions(area, codeAreaOpts);
//        return created;
//    }
//    
//    private static CodeAreaOptions loadCodeAreaOpts(CodeArea area, LinkedList<String> saveString) {
//        LinkedList<String> onlySaveString = getSaveStringOnlyForOption("codearea_opts", saveString);
//        StringResourceLoader loader = new StringResourceLoader(onlySaveString);
//        return new CodeAreaOptions(area, loader);
//    }    
//    
//    public static BooleanExpEditorOptions loadBooleanExpEditorOpts(BooleanExpEditor editor) throws IOException {
//        LinkedList<String> saveString = loadStringResForResLoader("booleanexpeditor_opts");
//        BooleanExpCodeAreaOptions opts = loadBooleanExpCodeAreaOpts(editor, saveString);
//        BooleanExpEditorOptions created = new BooleanExpEditorOptions(editor, opts);
//        return created;
//    }
//    
//    private static BooleanExpCodeAreaOptions loadBooleanExpCodeAreaOpts(
//            BooleanExpEditor editor,
//            LinkedList<String> saveString) {
//        CodeAreaOptions codeAreaOpts = loadCodeAreaOpts(editor.getPreConditionCodeArea(), saveString);
//        BooleanExpCodeAreaOptions created = new BooleanExpCodeAreaOptions(editor, codeAreaOpts);
//        return created;
//    }
//    
//    
//    public static ParametereditorOptions loadParameterEditorOpts(
//            LanguageOptions langOpts, ParameterEditor editor,
//            PSECentralObjectProvider centralObjectProvider) throws IOException {
//        LinkedList<String> saveString = loadStringResForResLoader("param_opts");
//        LinkedList<String> onlySaveString = getSaveStringOnlyForOption("param_opts", saveString);
//        StringResourceLoader loader = new StringResourceLoader(onlySaveString);
//        return new ParametereditorOptions(loader, langOpts, editor, centralObjectProvider);
//    }
//    
//    private static LinkedList<String> getSaveStringOnlyForOption(String id, LinkedList<String> saveString) {
//        LinkedList<String> created = new LinkedList<>();
//        int i = 0;
//        
//        for(; i < saveString.size() && !saveString.get(i).contains("new_option " + id); ++i) {            
//        }
//        ++i;
//        for(; i < saveString.size() && !saveString.get(i).contains("new_option "); ++i) {  
//            created.add(saveString.get(i));
//        } 
//        return created;                
//    }
//    
//    private static LinkedList<String> loadStringResForResLoader(String optName) throws IOException {
//        File f = new File(SuperFolderFinder.getSuperFolder() + "/core/option_saves/" + optName + ".txt");
//        return FileLoader.loadFileAsString(f);
//    }
//    
//    public static void saveOpt(Options opt) {
//        ArrayList<String> saveString = new ArrayList<>();
//        saveOptRec(opt, saveString);
//        FileSaver.writeStringLinesToFile(saveString, new File(SuperFolderFinder.getSuperFolder() + "/core/option_saves/" + opt.getId() + ".txt"));
//    }
//    
//    private static void saveOptRec(Options opt, ArrayList<String> saveString) {
//        saveString.add("new_option " + opt.getId());
//        for(OptionElement elem : opt.getOptionElements()) {
//            saveString.add(elem.getID() + " : " + elem.getChosenOption());
//        }
//        for(Options subOptions : opt.getSubOptions()) {                    
//            saveOptRec(subOptions, saveString);
//        }
//    }
//
//
//}