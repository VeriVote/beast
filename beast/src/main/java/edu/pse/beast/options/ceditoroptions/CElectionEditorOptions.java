//package edu.pse.beast.options.ceditoroptions;
//
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.options.Options;
//
///**
// * Options subclass for the CElectionEditorOptions.
// */
//public class CElectionEditorOptions extends Options {
//    private  CElectionDescriptionEditor editor;
//    private final CElectionCodeAreaOptions cElectionCodeAreaOptions;
//
//    /**
//     * Constructor
//     * @param editor the editor
//     * @param cElectionCodeAreaOptions the options
//     */
//    public CElectionEditorOptions(
//            CElectionDescriptionEditor editor,
//            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
//        super("ceditor_opts");
//        this.editor = editor;
//        this.cElectionCodeAreaOptions = cElectionCodeAreaOptions;
//        subOptions.add(cElectionCodeAreaOptions);
//    }
//
//    public CElectionEditorOptions(CElectionDescriptionEditor editor) {
//        super("ceditor_opts");
//        this.editor = editor;
//        this.cElectionCodeAreaOptions = new CElectionCodeAreaOptions(editor.getCodeArea());
//        subOptions.add(cElectionCodeAreaOptions);
//    }
//
//    @Override
//    protected void reapplySpecialized() {
//        cElectionCodeAreaOptions.setCodeArea(editor.getCodeArea());
//    }
//}
