//package edu.pse.beast.parametereditor;
//
//import javax.swing.JButton;
//import javax.swing.JTextField;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//
///**
// * The ArgumentHandler deals with the user input of additional arguments for CBMC.
// * @author Jonas
// */
//public class ArgumentHandler implements ChangeListener {
//    private final JTextField input;
//    private final JButton ok;
//    private boolean hasChanged;
//    /**
//     * Constructor
//     * @param input JTextField which contains the user input
//     * @param ok JButton that signifies that the user wants the input to be stored
//     */
//    public ArgumentHandler(JTextField input, JButton ok) {
//        this.input = input;
//        this.ok = ok;
//        setHasChanged(false);
//    }
//    /**
//     * Getter for the CBMC argument the user put in
//     * @return CBMC argument as String
//     */
//    public String getArgument() {
//        return input.getText();
//    }
//    /**
//     * Setter for the CBMC argument
//     * @param arg new CBMC argument
//     */
//    public void setArgument(String arg) {
//        input.setText(arg);
//        setHasChanged(true);
//    }
//
//    @Override
//    public void stateChanged(ChangeEvent e) {
//        setArgument(getArgument());
//    }
//    /**
//     * Returns whether the arguments were changed since last time saving.
//     * @return hasChanged
//     */
//    protected boolean hasChanged() {
//        return hasChanged;
//    }
//    /**
//     * Sets whether the arguments were changed since last time saving.
//     * @param hasChanged whether they changed
//     */
//    protected void setHasChanged(boolean hasChanged) {
//        this.hasChanged = hasChanged;
//    }
//}
