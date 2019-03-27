//package edu.pse.beast.options;
//
//import java.awt.BorderLayout;
//import java.awt.GridLayout;
//
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTabbedPane;
//
//import edu.pse.beast.saverloader.optionsaverloader.OptionsSaverLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//
///**
// * A JFrame subclass in with which OptionElement objects can be presented to the user.
// * @author Holger Klein
// */
//public class OptionPresenterFrame extends javax.swing.JFrame {
//
//    private static final long serialVersionUID = 1L;
//    private final StringResourceLoader srl;
//    private final Options opt;
//    /**
//     * Creates new form OptionPresenterFrame
//     * @param opt Options the options to be presented to the user
//     * @param srl StringResourceLoader the string resource loader used by the whole system
//     */
//    public OptionPresenterFrame(Options opt, StringResourceLoader srl) {
//        initComponents();
//        this.opt = opt;
//        this.srl = srl;
//        setTitle(srl.getStringFromID(opt.getId()));
//        jButton1.setText(srl.getStringFromID("ok_button"));
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        initializeApplyButtonPressFunctionality(opt);
//        showOptionsRec(opt);
//    }
//
//    /**
//     * if the user presses the apply button, the form closes,
//     * then saves and re-applies the given option.
//     * options.reapply() is only called for this top level function,
//     * meaning it has to recursively call it
//     * on its sub-options.
//     *
//     * @param opt this option is going to get re-applied and its settings
//                  saved if the user presses the apply button.
//     */
//    private void initializeApplyButtonPressFunctionality(Options opt) {
//        jButton1.addActionListener((ae) -> {
//            this.setVisible(false);
//            opt.reapply();
//            OptionsSaverLoaderInterface.saveOpt(opt);
//            this.dispose();
//        });
//    }
//
//    /**
//     * displays the options recursively. For every option and contained
//     * sub-options, it creates a new panel if the option contains one or more
//     * option item. For each option item, it shows the user the string connected
//     * to the option items id as well as all the choosable options.
//     * Example: Code area options. If it has option item font size, it shows
//     * a panel containing a label saying "font size" in whatever language is
//     * currently chosen and has a drop-down list next to it displaying the
//     * possible font sizes (e.g. 5 through 50).
//     * @param opt this is the top level option which is displayed.
//     *            All contained sub-options are then displayed recursively.
//     */
//    private void showOptionsRec(Options opt) {
//        JPanel panel = new JPanel(new GridLayout(opt.getOptionElements().size(), 2, 5, 5));
//        addAllOptionElementsToPanel(opt, panel);
//        JPanel containingPanel = new JPanel(new BorderLayout());
//        containingPanel.add(panel, BorderLayout.NORTH);
//        if (optionContainsOptionElements(opt))
//            jTabbedPane1.addTab(srl.getStringFromID(opt.getId()), containingPanel);
//        for (Options subOpt : opt.getSubOptions()) {
//            showOptionsRec(subOpt);
//        }
//    }
//
//    /**
//     * goes through every optionelement contained in the passed option parameter and adds
//     * its name and all choosable options in a combobox to the passed panel
//     * @param opt the option containing the option elements which should be added to the panel
//     * @param panel the panel onto which the option element information should be added
//     */
//    private void addAllOptionElementsToPanel(Options opt, JPanel panel) {
//        for (OptionElement elem : opt.getOptionElements()) {
//            JLabel label = new JLabel(srl.getStringFromID(elem.getID()));
//            OptionElemComboBox combobox = createOptionElemComboBox(elem);
//            panel.add(label);
//            panel.add(combobox);
//        }
//    }
//
//    private boolean optionContainsOptionElements(Options opt) {
//        return !opt.getOptionElements().isEmpty();
//    }
//
//    /**
//     * creates a combobox displaying all choosable options to the user
//     * @param elem the optionelement supplying the choosable option string
//     * @return a OptionelemBomboBox which displays all choosable options for
//     * this optionelement as strings
//     */
//    private OptionElemComboBox createOptionElemComboBox(OptionElement elem) {
//        OptionElemComboBox combobox = new OptionElemComboBox(elem);
//        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//        addChoosableOptionsToCombobox(elem, model);
//        selectChosenOptionInCombobox(elem, model);
//        combobox.setModel(model);
//        addHooksMakingElemHandleNewSelection(combobox);
//        return combobox;
//    }
//
//    /**
//     * this method makes the optionelement contained in the passed combo box
//     * handle if a new item is selected
//     * @param combobox the combo box containing the option element which
//     *        should handle a new selection
//     */
//    private void addHooksMakingElemHandleNewSelection(OptionElemComboBox combobox) {
//        combobox.addItemListener((ie) -> {
//            if (srl.getIdForString((String) ie.getItem()) != null) {
//                ((OptionElemComboBox) ie.getSource()).getElem().handleSelection(
//                        srl.getIdForString((String) ie.getItem()));
//            } else {
//                ((OptionElemComboBox) ie.getSource()).getElem()
//                .handleSelection((String) ie.getItem());
//            }
//        });
//    }
//
//    private void selectChosenOptionInCombobox(OptionElement elem,
//                                              DefaultComboBoxModel<String> model) {
//        if (!srl.containsId(elem.chosenOption) ) {
//            model.setSelectedItem(elem.chosenOption);
//        } else {
//            model.setSelectedItem(srl.getStringFromID(elem.chosenOption));
//        }
//    }
//
//    private void addChoosableOptionsToCombobox(OptionElement elem,
//                                               DefaultComboBoxModel<String> model) {
//        for (String s : elem.getChoosableOptions()) {
//            if (!srl.containsId(s) ) {
//                model.addElement(s);
//            } else {
//                model.addElement(srl.getStringFromID(s));
//            }
//        }
//    }
//
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        jTabbedPane1 = new javax.swing.JTabbedPane();
//        jButton1 = new javax.swing.JButton();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//
//        jButton1.setText("jButton1");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jTabbedPane1)
//            .addGroup(layout.createSequentialGroup()
//                .addComponent(jButton1)
//                .addGap(0, 338, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
//                              305, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
//                                 javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jButton1))
//        );
//
//        pack();
//    } // </editor-fold>//GEN-END:initComponents
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JButton jButton1;
//    private javax.swing.JTabbedPane jTabbedPane1;
//    // End of variables declaration//GEN-END:variables
//    /**
//     * Getter for JTabbedPane
//     * @return JTabbedPane
//     */
//    public JTabbedPane getjTabbedPane1() {
//        return jTabbedPane1;
//    }
//
//    /**
//     * Getter for JButton
//     * @return JButton
//     */
//    public JButton getjButton1() {
//        return jButton1;
//    }
//}