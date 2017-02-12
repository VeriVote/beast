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
public final class OptionPresenter implements DisplaysStringsToUser, ActionListener {
    
    private StringResourceLoader srl;
    private ArrayList<Options> subOptions = new ArrayList<>();
    private ArrayList<ArrayList<OptionElement>> optionElems = new ArrayList<>();
    private  OptionPresenterFrame frame;
    public OptionPresenter(StringResourceLoader srl) {
        this.srl = srl;
    }
    
    /**
     * 
     * @param opt the options to be presented
     */
    public void presentOptionsToUser(Options opt) {
        frame = new OptionPresenterFrame(opt.getId(), srl.getStringFromID("ok_button"));
        for(Options subOptions : opt.getSubOptions()) {
            frame.getjTabbedPane1().addTab(srl.getStringFromID(subOptions.getId()), createPanelForOption(subOptions));
        }
        frame.getjButton1().addActionListener(this);
        frame.setVisible(true);        
    }
    
    private JPanel createPanelForOption(Options opt) {
        JPanel created = new JPanel();
        subOptions.add(opt);
        ArrayList<OptionElement> elems = new ArrayList<>();
        for(OptionElement elem : opt.getOptionElements()) {    
            elems.add(elem);
            JLabel label = new JLabel(srl.getStringFromID(elem.getID()));
            OptionElemComboBox combobox = new OptionElemComboBox(elem);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
            for(String s : elem.getChoosableOptions()) {
                model.addElement(s);
            }
            combobox.setModel(model);
            
            combobox.addItemListener((ie) -> {
                OptionElemComboBox src = (OptionElemComboBox) ie.getSource();
                src.getElem().handleSelection((String) ie.getItem());
            });
            
            created.add(label);
            created.add(combobox);
        }
        optionElems.add(elems);
        return created;
    }

    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        srl = stringResIF.getOptionStringResProvider().getOptionStringRes();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        frame.setVisible(false);
        for(Options opt : subOptions) {
            opt.reapply();
        }
    }

}
