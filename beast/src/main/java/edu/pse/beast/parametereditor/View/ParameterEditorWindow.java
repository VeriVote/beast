//package edu.pse.beast.parametereditor.View;
//
//import java.awt.BorderLayout;
//import java.awt.Component;
//
//import javax.swing.DefaultComboBoxModel;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JSpinner;
//
//import edu.pse.beast.highlevel.CheckStatusDisplay;
//import edu.pse.beast.highlevel.DisplaysStringsToUser;
//import edu.pse.beast.stringresource.ParameterEditorStringResProvider;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//import edu.pse.beast.toolbox.RepaintThread;
//import edu.pse.beast.toolbox.SuperFolderFinder;
//
///**
// * Window which takes all the user input for the parameters used by CBMC. It is
// * also the central window for BEAST which starts and stops checks.
// *
// * @author Jonas
// */
//public class ParameterEditorWindow extends javax.swing.JFrame
//        implements DisplaysStringsToUser, CheckStatusDisplay {
//
//    private static final long serialVersionUID = 1L;
//    private final AdvancedWindow advWindow = new AdvancedWindow();
//    private final AboutWindow versionWindow = new AboutWindow();
//    private boolean reacts;
//    private String title = "";
//    private String currentlyLoadedProjectName = "New Project";
//    private final ImageIcon loading;
//    private JLabel imageLabel;
//    private StringLoaderInterface stringResIF;
//
//    /**
//     * Creates new form ParameterEditorWindow
//     */
//    public ParameterEditorWindow() {
//        initComponents();
//        setReacts(true);
//        userFeedbackPanel.setLayout(new BorderLayout());
//        loading = new ImageIcon(SuperFolderFinder.getSuperFolder() + "/core/images/other/loader.gif");
//        imageLabel = new JLabel();
//        imageLabel.setHorizontalAlignment(JLabel.CENTER);
//        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        userFeedbackPanel.add(imageLabel);
//        Thread t = new Thread(new RepaintThread(this));
//        t.start();
//    }
//
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    // <editor-fold defaultstate="collapsed" desc="Generated
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        toolbar = new javax.swing.JToolBar();
//        voters = new javax.swing.JLabel();
//        voterMin = new javax.swing.JSpinner();
//        voterTo = new javax.swing.JLabel();
//        voterMax = new javax.swing.JSpinner();
//        candidates = new javax.swing.JLabel();
//        candMin = new javax.swing.JSpinner();
//        candTo = new javax.swing.JLabel();
//        candMax = new javax.swing.JSpinner();
//        seats = new javax.swing.JLabel();
//        seatMin = new javax.swing.JSpinner();
//        seatTo = new javax.swing.JLabel();
//        seatMax = new javax.swing.JSpinner();
//        timeout = new javax.swing.JLabel();
//        timeoutNum = new javax.swing.JSpinner();
//        timeoutUnit = new javax.swing.JComboBox<String>();
//        processes = new javax.swing.JLabel();
//        amountProcessesSpinner = new javax.swing.JSpinner();
//        advancedButton = new javax.swing.JButton();
//        aboutButton = new javax.swing.JToggleButton();
//        userFeedbackPanel = new java.awt.Panel();
//        statusLabel = new java.awt.Label();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setTitle("ParameterEditor");
//        setMaximumSize(new java.awt.Dimension(475, 347));
//        setMinimumSize(new java.awt.Dimension(475, 347));
//        setResizable(false);
//
//        toolbar.setFloatable(false);
//        toolbar.setRollover(true);
//
//        voters.setText("Wähler");
//
//        voterMin.setMaximumSize(new java.awt.Dimension(0, 10000));
//
//        voterTo.setText("bis");
//
//        voterMax.setMaximumSize(new java.awt.Dimension(0, 10000));
//
//        candidates.setText("Kandidaten");
//
//        candMin.setMaximumSize(new java.awt.Dimension(0, 10000));
//
//        candTo.setText("bis");
//
//        candMax.setMaximumSize(new java.awt.Dimension(0, 10000));
//
//        seats.setText("Sitze");
//
//        seatMin.setMaximumSize(new java.awt.Dimension(0, 10000));
//
//        seatTo.setText("bis");
//
//        seatMax.setMaximumSize(new java.awt.Dimension(0, 10000));
//
//        timeout.setText("Dauer");
//
//        timeoutNum.setMaximumSize(new java.awt.Dimension(0, 32767));
//
//        timeoutUnit.setModel(new javax.swing.DefaultComboBoxModel<String>(
//                new String[] { "Sekunden", "Minuten", "Stunden", "Tage" }
//                ));
//
//        processes.setText("Max. Prozesse");
//
//        amountProcessesSpinner.setMaximumSize(new java.awt.Dimension(0, 50));
//
//        advancedButton.setText("Erweitert...");
//        advancedButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                advancedButtonActionPerformed(evt);
//            }
//        });
//
//        aboutButton.setText("About");
//        aboutButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                aboutButtonActionPerformed(evt);
//            }
//        });
//
//        userFeedbackPanel.setPreferredSize(new java.awt.Dimension(50, 40));
//
//        javax.swing.GroupLayout userFeedbackPanelLayout = new javax.swing.GroupLayout(userFeedbackPanel);
//        userFeedbackPanel.setLayout(userFeedbackPanelLayout);
//        userFeedbackPanelLayout.setHorizontalGroup(
//            userFeedbackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 50, Short.MAX_VALUE)
//        );
//        userFeedbackPanelLayout.setVerticalGroup(
//            userFeedbackPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 40, Short.MAX_VALUE)
//        );
//
//        statusLabel.setMinimumSize(new java.awt.Dimension(400, 10));
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addGap(33, 33, 33)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
//                    .addGroup(layout.createSequentialGroup()
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                            .addComponent(seats, javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addComponent(candidates, javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addComponent(voters, javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addComponent(timeout, javax.swing.GroupLayout.Alignment.TRAILING)
//                            .addComponent(processes, javax.swing.GroupLayout.Alignment.TRAILING))
//                        .addGap(18, 18, 18)
//                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                            .addComponent(voterMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
//                            .addComponent(seatMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                            .addComponent(candMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                            .addComponent(timeoutNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                            .addComponent(amountProcessesSpinner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
//                    .addGroup(layout.createSequentialGroup()
//                        .addComponent(advancedButton)
//                        .addGap(0, 0, Short.MAX_VALUE)))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addComponent(aboutButton)
//                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                        .addGroup(layout.createSequentialGroup()
//                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                                .addComponent(voterTo, javax.swing.GroupLayout.Alignment.TRAILING)
//                                .addComponent(candTo, javax.swing.GroupLayout.Alignment.TRAILING)
//                                .addComponent(seatTo, javax.swing.GroupLayout.Alignment.TRAILING))
//                            .addGap(18, 18, 18)
//                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                                .addComponent(voterMax, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
//                                .addComponent(candMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                                .addComponent(seatMax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
//                        .addComponent(timeoutUnit, 0, 102, Short.MAX_VALUE)))
//                .addGap(151, 151, 151))
//            .addGroup(layout.createSequentialGroup()
//                .addComponent(userFeedbackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(2, 2, 2)
//                .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(18, 18, 18)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(voters)
//                    .addComponent(voterMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(voterTo)
//                    .addComponent(voterMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(candTo)
//                    .addComponent(candMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(candMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(candidates))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(seatMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(seatTo)
//                    .addComponent(seatMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(seats))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(timeout)
//                    .addComponent(timeoutNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(timeoutUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(amountProcessesSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(processes))
//                .addGap(18, 18, 18)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(advancedButton)
//                    .addComponent(aboutButton))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
//                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
//                    .addComponent(userFeedbackPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
//        );
//
//        voterMin.getAccessibleContext().setAccessibleName("");
//        statusLabel.getAccessibleContext().setAccessibleName("statusLabel");
//
//        pack();
//    }// </editor-fold>//GEN-END:initComponents
//
//    private void advancedButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_advancedButtonActionPerformed
//        if (reacts) {
//            advWindow.setVisible(true);
//        }
//    }// GEN-LAST:event_advancedButtonActionPerformed
//
//    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_aboutButtonActionPerformed
//        if (reacts) {
//            versionWindow.setVisible(true);
//            aboutButton.setSelected(false);;
//        }
//    }// GEN-LAST:event_aboutButtonActionPerformed
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting
//        // code (optional) ">
//        /*
//		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
//		 * default look and feel. For details see
//		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.
//		 * html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ParameterEditorWindow.class.getName())
//                    .log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ParameterEditorWindow.class.getName())
//                    .log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ParameterEditorWindow.class.getName())
//                    .log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ParameterEditorWindow.class.getName())
//                    .log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        // </editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ParameterEditorWindow().setVisible(true);
//            }
//        });
//    }
//
//    // Variables declaration - do not modify//GEN-BEGIN:variables
//    private javax.swing.JToggleButton aboutButton;
//    private javax.swing.JButton advancedButton;
//    private javax.swing.JSpinner amountProcessesSpinner;
//    private javax.swing.JSpinner candMax;
//    private javax.swing.JSpinner candMin;
//    private javax.swing.JLabel candTo;
//    private javax.swing.JLabel candidates;
//    private javax.swing.JLabel processes;
//    private javax.swing.JSpinner seatMax;
//    private javax.swing.JSpinner seatMin;
//    private javax.swing.JLabel seatTo;
//    private javax.swing.JLabel seats;
//    private java.awt.Label statusLabel;
//    private javax.swing.JLabel timeout;
//    private javax.swing.JSpinner timeoutNum;
//    private javax.swing.JComboBox<String> timeoutUnit;
//    private javax.swing.JToolBar toolbar;
//    private java.awt.Panel userFeedbackPanel;
//    private javax.swing.JSpinner voterMax;
//    private javax.swing.JSpinner voterMin;
//    private javax.swing.JLabel voterTo;
//    private javax.swing.JLabel voters;
//    // End of variables declaration//GEN-END:variables
//	/**
//     * Getter for minimum voters JSpinner
//     *
//     * @return minimum voters JSpinner
//     */
//    public JSpinner getVoterMin() {
//        return voterMin;
//    }
//
//    /**
//     * Getter for maximum voters JSpinner
//     *
//     * @return maximum voters JSpinner
//     */
//    public JSpinner getVoterMax() {
//        return voterMax;
//    }
//
//    /**
//     * Getter for minimum candidates JSpinner
//     *
//     * @return minimum candidates JSpinner
//     */
//    public JSpinner getCandMin() {
//        return candMin;
//    }
//
//    /**
//     * Getter for maximum candidates JSpinner
//     *
//     * @return maximum candidates JSpinner
//     */
//    public JSpinner getCandMax() {
//        return candMax;
//    }
//
//    /**
//     * Getter for minimum seats JSpinner
//     *
//     * @return minimum seats JSpinner
//     */
//    public JSpinner getSeatMin() {
//        return seatMin;
//    }
//
//    /**
//     * Getter for maximum seats JSpinner
//     *
//     * @return maximum seats JSpinner
//     */
//    public JSpinner getSeatMax() {
//        return seatMax;
//    }
//
//    /**
//     * Getter for timeout value JSpinner
//     *
//     * @return timeout value JSpinner
//     */
//    public JSpinner getTimeoutNum() {
//        return timeoutNum;
//    }
//
//    /**
//     * Getter for timeout unit JComboBox
//     *
//     * @return timeout unit JComboBox
//     */
//    public javax.swing.JComboBox<String> getTimeoutUnit() {
//        return timeoutUnit;
//    }
//
//    /**
//     * Getter for amount of processes JSpinner
//     *
//     * @return amount of processes JSpinner
//     */
//    public JSpinner getAmountProcessesSpinner() {
//        return amountProcessesSpinner;
//    }
//
//    /**
//     * Getter for the window for arguments for CBMC from the user
//     *
//     * @return AdvancedWindow
//     */
//    public AdvancedWindow getAdvancedWindow() {
//        return advWindow;
//    }
//
//    /**
//     * Getter for the toolbar
//     *
//     * @return toolbar
//     */
//    public javax.swing.JToolBar getToolbar() {
//        return toolbar;
//    }
//
//    @Override
//    public void updateStringRes(StringLoaderInterface stringResIF) {
//        reacts = false;
//        this.stringResIF = stringResIF;
//        ParameterEditorStringResProvider provider = stringResIF.getParameterEditorStringResProvider();
//        StringResourceLoader other = provider.getOtherStringRes();
//        title = other.getStringFromID("title");
//        setWindowTitle(currentlyLoadedProjectName);
//        voters.setText(other.getStringFromID("voters"));
//        candidates.setText(other.getStringFromID("candidates"));
//        seats.setText(other.getStringFromID("seats"));
//        timeout.setText(other.getStringFromID("timeout"));
//        processes.setText(other.getStringFromID("max_processes"));
//        voterTo.setText(other.getStringFromID("to"));
//        candTo.setText(other.getStringFromID("to"));
//        seatTo.setText(other.getStringFromID("to"));
//        advancedButton.setText(other.getStringFromID("advanced"));
//        aboutButton.setText(other.getStringFromID("about"));
//        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
//        model.addElement(other.getStringFromID("seconds"));
//        model.addElement(other.getStringFromID("minutes"));
//        model.addElement(other.getStringFromID("hours"));
//        model.addElement(other.getStringFromID("days"));
//        timeoutUnit.setModel(model);
//        advWindow.updateStringRes(stringResIF);
//        versionWindow.updateStringRes(stringResIF);
//        timeout.setText(other.getStringFromID("timeout"));
//        processes.setText(other.getStringFromID("max_processes"));
//        voterTo.setText(other.getStringFromID("to"));
//        candTo.setText(other.getStringFromID("to"));
//        seatTo.setText(other.getStringFromID("to"));
//        reacts = true;
//    }
//
//    /**
//     * Toggles whether this window reacts to user input with the exception of
//     * stopping checks (to not interrupt checks)
//     *
//     * @param reacts whether it reacts
//     */
//    public void setReacts(boolean reacts) {
//        this.reacts = reacts;
//        advWindow.setReacts(reacts);
//    }
//
//    /**
//     * Adds the given string to the window title, used for displaying name of
//     * currently loaded Project object
//     *
//     * @param projectName name of the currently loaded Project object
//     */
//    public void setWindowTitle(String projectName) {
//        this.currentlyLoadedProjectName = projectName;
//        this.setTitle(title + " " + projectName + " - BEAST");
//    }
//
//    /**
//     * Setter for version of BEAST
//     *
//     * @param version of BEAST
//     */
//    public void setVersion(String version) {
//        versionWindow.setVersion(version);
//    }
//
//    @Override
//    public void displayText(String stringIdForResources, boolean showLoader, String additionalText) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                if (showLoader) {
//                    imageLabel.setIcon(loading);
//                } else {
//                    imageLabel.setIcon(null);
//                }
//                if (!"".equals(stringIdForResources)) {
//                    statusLabel.setText(stringResIF.getParameterEditorStringResProvider().getOtherStringRes()
//                            .getStringFromID(stringIdForResources) + additionalText);
//                } else {
//                    statusLabel.setText("");
//                }
//            }
//        }).start();
//    }
//
//    @Override
//    public void signalThatAnalysisEnded() {
//        // this is a bugfix. It solves Issue #19 of the repo
//        this.setState(JFrame.ICONIFIED);
//        this.setState(JFrame.NORMAL);
//    }
//}
