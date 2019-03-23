//package edu.pse.beast.options.booleanexpeditoroptions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.options.Options;
//
///**
// * Options subclass for the BooleanExpEditorOptions.
// */
//public class BooleanExpEditorOptions extends Options {
//    private final BooleanExpEditor editor;
//    private final BooleanExpCodeAreaOptions booleanExpCodeAreaOptions;
//
//    /**
//     * Constructor
//     * @param editor the editor
//     * @param booleanExpCodeAreaOptions the options
//     */
//    public BooleanExpEditorOptions(BooleanExpEditor editor,
//            BooleanExpCodeAreaOptions booleanExpCodeAreaOptions) {
//        super("booleanexpeditor_opts");
//        this.editor = editor;
//        this.booleanExpCodeAreaOptions = booleanExpCodeAreaOptions;
//        subOptions.add(booleanExpCodeAreaOptions);
//    }
//
//    /**
//     * Secondary Constructor that only needs a BooleanExpEditor object
//     * @param editor the editor
//     */
//    public BooleanExpEditorOptions(BooleanExpEditor editor) {
//        super("booleanexpeditor_opts");
//        this.editor = editor;
//        this.booleanExpCodeAreaOptions = new BooleanExpCodeAreaOptions(editor);
//        subOptions.add(booleanExpCodeAreaOptions);
//    }
//
//    /**
//     * Getter
//     * @return the options
//     */
//    public BooleanExpCodeAreaOptions getBooleanExpCodeAreaOptions() {
//        return booleanExpCodeAreaOptions;
//    }
//
//    /**
//     * Getter
//     * @return the editor
//     */
//    public BooleanExpEditor getBooleanExpEditor() {
//        return editor;
//    }
//
//    @Override
//    protected void reapplySpecialized() {
//    }
//}