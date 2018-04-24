//package edu.pse.beast.options.BooleanExpEditorOptions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.options.Options;
//import edu.pse.beast.options.CodeAreaOptions.CodeAreaOptions;
//
///**
// * Options subclass for the BooleanExpCodeAreaOptions.
// */
//public class BooleanExpCodeAreaOptions extends Options {
//    private BooleanExpEditor editor;
//    private final CodeAreaOptions codeAreaOptions;
//    
//    /**
//     * Constructor
//     * @param editor BooleanExpEditor
//     * @param codeAreaOptions the options
//     */
//    public BooleanExpCodeAreaOptions(BooleanExpEditor editor,
//            CodeAreaOptions codeAreaOptions) {
//        super("booleanexpcodearea_opts");
//        this.editor = editor;
//        this.codeAreaOptions = codeAreaOptions;
//        subOptions.add(codeAreaOptions);
//    }
//
//    BooleanExpCodeAreaOptions(BooleanExpEditor editor) {
//        super("booleanexpcodearea_opts");
//        this.editor = editor;
//        this.codeAreaOptions = new CodeAreaOptions(editor.getPreConditionCodeArea());
//        subOptions.add(codeAreaOptions);
//    }
//
//    @Override
//    protected void reapplySpecialized() {
//        codeAreaOptions.setCodeArea(editor.getPreConditionCodeArea());
//        codeAreaOptions.reapply();
//        codeAreaOptions.setCodeArea(editor.getPostConditionCodeArea());
//        codeAreaOptions.reapply();
//    }
//}
