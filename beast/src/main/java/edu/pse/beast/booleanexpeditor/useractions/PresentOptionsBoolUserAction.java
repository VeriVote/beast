//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.options.OptionPresenter;
//import edu.pse.beast.options.booleanexpeditoroptions.BooleanExpEditorOptions;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass presenting CodeArea options to the user.
// * @author Nikolai Schnell
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
//    public PresentOptionsBoolUserAction(BooleanExpEditorOptions options,
//                                        OptionPresenter presenter) {
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