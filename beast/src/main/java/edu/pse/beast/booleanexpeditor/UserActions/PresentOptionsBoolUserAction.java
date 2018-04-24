//package edu.pse.beast.booleanexpeditor.UserActions;
//
//import edu.pse.beast.options.OptionPresenter;
//import edu.pse.beast.options.BooleanExpEditorOptions.BooleanExpEditorOptions;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass presenting CodeArea options to the user.
// * @author NikolaiLMS
// */
//public class PresentOptionsBoolUserAction extends UserAction {
//
//    private BooleanExpEditorOptions options;
//    private OptionPresenter presenter;
//
//    /**
//     * Constructor
//     * @param options the BooleanExpEditorOptions object
//     * @param presenter the OptionPresenter object
//     */
//    public PresentOptionsBoolUserAction(BooleanExpEditorOptions options, OptionPresenter presenter) {
//        super("options");
//        this.options = options;
//        this.presenter = presenter;
//    }
//
//    @Override
//    public void perform() {
//        presenter.presentOptionsToUser(options);
//    }
//}
