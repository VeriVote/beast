//package edu.pse.beast.codearea.autocompletion;
//
//import java.util.ArrayList;
//
//import javax.swing.JTextPane;
//
//import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
//
///**
// *This class implements the runnable interface to continuously scan the given
// * JTExtPane for words the user wrote in it and supplying these words to 
// * the given autocompletioncontroller so the controller can present these
// * options to the user
// * @author Holger-Desktop
// */
//public class FindWordsConcurrently implements Runnable {
//    private final ArrayList<String> words = new ArrayList<>();
//    private final JTextPane pane;
//    private volatile boolean run = true;
//    private final AutocompletionController controller;
//    public FindWordsConcurrently(JTextPane pane, AutocompletionController controller) {
//        this.pane = pane;
//        this.controller = controller;
//    }
//    
//    public void stop() {
//        run = false;
//    }
//
//    public ArrayList<String> getWords() {
//        return words;
//    }
//        
//    @Override
//    public void run() {
//        while(run) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException ex) {
//                
//            }
//            String code = JTextPaneToolbox.getText(pane);
//            String w = "";
//            for(int i = 0; i < code.length(); i++) {
//                w = "";
//                try {
//                    for(; Character.isLetterOrDigit(code.charAt(i)); i++) {
//                        w += code.charAt(i);
//                    }
//                } catch (StringIndexOutOfBoundsException e) {
//                    break;
//                }
//                if(w.length() != 0) {
//                    controller.addAutocompletionString(w);
//                }
//            }
//        }
//    }
//    
//}
