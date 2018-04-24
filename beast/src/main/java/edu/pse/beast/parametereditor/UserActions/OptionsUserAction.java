//package edu.pse.beast.parametereditor.UserActions;
//
//import edu.pse.beast.options.OptionPresenter;
//import edu.pse.beast.options.ParametereditorOptions.ParametereditorOptions;
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction for changing options
// * @author Jonas
// */
//public class OptionsUserAction extends UserAction {
//    private final ParametereditorOptions opts;
//    private final ParameterEditor paramEditor;
//    private final OptionPresenter presenter;
//    /**
//     * Constructor
//     * @param opts ParametereditorOptions
//     * @param presenter OptionPresenter
//     * @param paramEditor ParameterEditor
//     */
//    public OptionsUserAction(
//            ParametereditorOptions opts,
//            ParameterEditor paramEditor,
//            OptionPresenter presenter) {
//        super("options");
//        this.opts = opts;
//        this.paramEditor = paramEditor;
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void perform() {
//        if (paramEditor.getReacts()) {
//            presenter.presentOptionsToUser(opts);
//        }
//    }
//}
