package edu.pse.beast.parametereditor;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jonas
 */
public class ArgumentHandler implements ChangeListener {
    private final JTextField input;
    private final JButton ok;
    
    public ArgumentHandler(JTextField input, JButton ok) {
        this.input = input;
        this.ok = ok;
    }
    public String getArgument() {
        return input.getText();
    }
    public void setArgument(String arg) {
        input.setText(arg);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setArgument(getArgument());
    }
}
