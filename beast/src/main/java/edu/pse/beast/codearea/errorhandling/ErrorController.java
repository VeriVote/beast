//package edu.pse.beast.codearea.errorhandling;
//
//import java.util.ArrayList;
//
//import javax.swing.JTextPane;
//
///**
// * This class facillitates communication between the various error finders
// * and error displays. Whenever the concurrently running error finder find 
// * a new error, it receives a message. It then asks the injected error
// * displayer to display the codeerror on the pane.
// * @author Holger Klein
// */
//public class ErrorController {
//    private ErrorFinderList errorFinderList;
//    private JTextPane pane;
//    private ErrorDisplayer displayer;
//    private boolean changed = false;
//    private int currentCaretPos;
//    private ErrorFinderThread t;
//    
//    public ErrorController(JTextPane pane,
//            ErrorDisplayer displayer) {
//        this.pane = pane;
//        errorFinderList = new ErrorFinderList();
//        this.displayer = displayer;
//        t = new ErrorFinderThread(errorFinderList, pane, this);
//    }
//    
//    /**
//     * stops the concurrently running errorfinder
//     */
//    public void stopThread() {
//        t.stop();
//    }
//    
//    public void addErrorFinder(ErrorFinder finder) {
//        errorFinderList.add(finder);
//    }
//
//    public ErrorDisplayer getDisplayer() {
//        return displayer;
//    }
//
//    public ErrorFinderList getErrorFinderList() {
//        return errorFinderList;
//    }
//
//    /**
//     * this function is called by error finder classes if they find new errors
//     * @param lastFoundErrors the list of newly found errors
//     */
//    public void foundNewErrors(ArrayList<CodeError> lastFoundErrors) {
//        displayer.showErrors(lastFoundErrors);
//    }
//
//    public void pauseErrorFinding() {
//        t.pauseChecking();
//    }
//
//    public void resumeErrorFinding() {
//        t.resumeChecking();
//    }
// }
