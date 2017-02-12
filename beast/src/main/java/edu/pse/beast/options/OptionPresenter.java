package edu.pse.beast.options;

import edu.pse.beast.codearea.ActionAdder.ActionlistListener;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Lukas
 *
 */
public final class OptionPresenter implements DisplaysStringsToUser {
    
    private StringResourceLoader srl;
   
    public OptionPresenter(StringResourceLoader srl) {
        this.srl = srl;
    }
    
    /**
     * 
     * @param opt the options to be presented
     */
    public void presentOptionsToUser(Options opt) {
        OptionPresenterFrame frame = new OptionPresenterFrame(
                opt, srl);        
        frame.setVisible(true);        
    }    

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        this.srl = stringResIF.getOptionStringResProvider().getOptionStringRes();
    }
    

}
