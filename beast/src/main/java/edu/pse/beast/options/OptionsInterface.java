//package edu.pse.beast.options;
//
//import java.io.IOException;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.highlevel.PSECentralObjectProvider;
//import edu.pse.beast.options.booleanexpeditoroptions.BooleanExpEditorOptions;
//import edu.pse.beast.options.ceditoroptions.CElectionEditorOptions;
//import edu.pse.beast.options.parametereditoroptions.LanguageOptions;
//import edu.pse.beast.options.parametereditoroptions.ParametereditorOptions;
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.saverloader.optionsaverloader.OptionsSaverLoaderInterface;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.toolbox.ObjectRefsForBuilder;
//
///**
// * Class providing access to different OptionElement subclasses and an
// * OptionPresenter instance.
// */
//public class OptionsInterface {
//    private OptionPresenter presenter;
//    private LanguageOptions languageOptions;
//    private BooleanExpEditorOptions booleanExpEditorOptions;
//    private CElectionEditorOptions cElectionEditorOptions;
//    private ParametereditorOptions parametereditorOptions;
//  //  private DeleteCFilesOptions deleteFilesOptions;
//
//    /**
//     * Getter for the BooleanExpEditorOptions
//     *
//     * @param editor
//     *            BooleanExpEditor
//     * @param refs
//     *            ObjectRefsForBuilder
//     * @return BooleanExpEditorOptions
//     */
//    public BooleanExpEditorOptions
//                getBooleanExpEditorOptions(BooleanExpEditor editor,
//                                           ObjectRefsForBuilder refs) {
//        if (booleanExpEditorOptions == null) {
//            try {
//                booleanExpEditorOptions =
//                    OptionsSaverLoaderInterface.loadBooleanExpEditorOpts(editor);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                booleanExpEditorOptions = new BooleanExpEditorOptions(editor);
//            }
//        }
//        return booleanExpEditorOptions;
//    }
//
//    /**
//     * Getter for the CElectionEditorOptions
//     *
//     * @param editor
//     *            the CElectionEditor
//     * @return CElectionEditorOptions
//     */
//    public CElectionEditorOptions getCElectionEditorOptions(CElectionDescriptionEditor editor) {
//        if (cElectionEditorOptions == null) {
//            try {
//                cElectionEditorOptions = OptionsSaverLoaderInterface.loadCEditorOpts(editor);
//            } catch (IOException ex) {
//                cElectionEditorOptions = new CElectionEditorOptions(editor);
//            }
//        }
//        return cElectionEditorOptions;
//    }
//
//    /**
//     * Getter for the ParametereditorOptions
//     *
//     * @param langOpts
//     *            LanguageOptions
//     * @param editor
//     *            ParameterEditor
//     * @param centralObjectProvider
//     *            PSECentralObjectProvider
//     * @return ParametereditorOptions
//     */
//    public ParametereditorOptions
//                getParameterEditorOptions(LanguageOptions langOpts,
//                                          ParameterEditor editor
//                                          /*PSECentralObjectProvider centralObjectProvider*/) {
//        if (parametereditorOptions == null) {
//            try {
//                parametereditorOptions =
//                    OptionsSaverLoaderInterface
//                        .loadParameterEditorOpts(langOpts, editor,
//                                                 centralObjectProvider);
//            } catch (IOException e) {
//            }
//        }
//        return parametereditorOptions;
//    }
//
//    /**
//     * Getter for the LanguageOptions
//     *
//     * @param stringIf
//     *            StringLoaderInterface
//     * @return LanguageOptions
//     */
//    public LanguageOptions getLanguageOptions(StringLoaderInterface stringIf) {
//        if (languageOptions == null) {
//            try {
//                languageOptions = OptionsSaverLoaderInterface.loadLangOpts(stringIf);
//            } catch (IOException ex) {
//                languageOptions = new LanguageOptions(stringIf);
//            }
//        }
//        return languageOptions;
//    }
//
//    /**
//     * Getter for the OptionPresenter
//     *
//     * @param refs
//     *            ObjectRefsForBuilder
//     * @return OptionPresenter
//     */
//    public OptionPresenter getOptionPresenter(ObjectRefsForBuilder refs) {
//        if (presenter == null) {
//            presenter =
//                new OptionPresenter(refs.getStringIF()
//                                    .getOptionStringResProvider().getOptionStringRes());
//            refs.getLanguageOpts().addStringDisplayer(presenter);
//        }
//        return presenter;
//    }
//}
