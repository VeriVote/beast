package edu.pse.beast.zzz.electionsimulator.view;

//import java.awt.Dimension;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.Iterator;
//
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JMenuBar;
//import javax.swing.JScrollBar;
//import javax.swing.JTextField;
//import javax.swing.JToolBar;
//
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//import edu.pse.beast.electionsimulator.ElectionSimulation;
//import edu.pse.beast.electionsimulator.model.ElectionSimulationModel;
//import edu.pse.beast.stringresource.PropertyListStringResProvider;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.stringresource.StringResourceLoader;
//
//public class ElectionSimulationWindow extends JFrame implements ActionListener {
//    private static final long serialVersionUID = 1L;
//    public final JButton addCandidate;
//    public final JButton addVoter;
//
//    public final JButton removeCandidate;
//    public final JButton removeVoter;
//
//    public final JButton newButton;
//    public final JButton saveButton;
//    public final JButton saveAsButton;
//    public final JButton loadDataButton;
//    public final JButton undoButton;
//    public final JButton redoButton;
//    public final JButton startStopButton;
//
//    private String[] modes = {
//        "outcome (c)",
//        "outcome  (cbmc)" ,
//        "outcome (c) + margin(cbmc)",
//        "outcome (cbmc) + margin(cbmc)"
//    };
//
//    //create the combo box
//    private final JComboBox<String> modeListBox;
//
//    public final JScrollBar horizontalScroll;
//    public final JScrollBar verticalScroll;
//
//    private JMenuBar menuBar;
//    private JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
//
//    private String title = "C-Editor";
//    private String saveChanges;
//    private String save;
//    private String yesOption;
//    private String noOption;
//    private String cancelOption;
//
//    private StringResourceLoader srl;
//
//    private ElectionSimulation controller;
//
//    private ElectionSimulationModel model;
//
//  /**
//   *
//   */
//  public ElectionSimulationWindow(ElectionTypeContainer container, ElectionSimulation controller,
//                                  ElectionSimulationModel model) {
//    this(new StringLoaderInterface("en"), container, controller, model);
//  }
//
//  /**
//   * @param sli
//   */
//  public ElectionSimulationWindow(StringLoaderInterface sli, ElectionTypeContainer container,
//                                  ElectionSimulation controller, ElectionSimulationModel model) {
//      this.model = model;
//      this.controller = controller;
//      PropertyListStringResProvider provider = sli.getPropertyListStringResProvider();
//      this.srl = provider.getOtherStringRes();
//      this.setVisible(false);
//
//      //the buttons that are on the input plane
//      addCandidate = new JButton("+C");
//      addVoter = new JButton("+V");
//      removeCandidate = new JButton("-C");
//      removeVoter = new JButton("-V");
//
//      //these buttons will end up in the toolbar
//      newButton = new JButton("new");
//      loadDataButton = new JButton("load");
//      saveButton = new JButton("save");
//      saveAsButton = new JButton("saveAs");
//      undoButton = new JButton("undo");
//      redoButton = new JButton("redo");
//
//      modeListBox = new JComboBox<String>(modes);
//
//      startStopButton = new JButton("start");
//      modeListBox.setSelectedIndex(3);
//      modeListBox.addActionListener(this);
//
//      horizontalScroll =
//      new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0,
//                     model.getAmountCandidates());
//      verticalScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, model.getAmountVoters());
//
//      //  menuBar = new JMenuBar();
//      //  menuFile = new JMenu();
//      //  menuFile.setText("Menu");
//      //  menuEdit = new JMenu();
//      //  menuEdit.setText("Bearbeiten");
//      //
//    init();
//  }
//
//  private void init() {
//    this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
//    this.getContentPane().addComponentListener(controller);
//
//    this.setLayout(null);
//    this.setBounds(0, 0, model.getStartWidth(), model.getStartHeight());
//    toolBar.setBounds(0, 0, this.getWidth(), model.getBorderMarginSmall());
//    toolBar.setFloatable( false);
//
//    newButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    loadDataButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    saveButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    saveAsButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    undoButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    redoButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    startStopButton.setSize(model.getButtonWidth(), model.getButtonHeight());
//    modeListBox.setMaximumSize(new Dimension(model.getButtonWidth() * 4,
//                                               (int) Math.ceil(model.getButtonHeight() / 2)));
//    toolBar.add(newButton);
//    toolBar.add(loadDataButton);
//    toolBar.add(saveButton);
//    toolBar.add(saveAsButton);
//    toolBar.add(undoButton);
//    toolBar.add(redoButton);
//    toolBar.add(startStopButton);
//    toolBar.add(modeListBox);
//
//    this.add(toolBar);
//
//    // fieldsPerWidth =
//    //     (this.getWidth() - 2 * (borderMarginSmall))
//    //         / (2 *  model.getWidthMultiplier());
//    // fieldsPerHeight =
//    //     (this.getHeight() - 2 * (borderMarginSmall * 2))
//    //         / (2 * heightMultiplier);
//
//    // addCandidate = new JButton("+C");
//    // addVoter = new JButton("+V");
//    // removeCandidate = new JButton("-C");
//    // removeVoter = new JButton("-V");
//    // horizontalScroll =
//    //     new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0,
//    //                    model.getAmountCandidates());
//    // verticalScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, amountVoters);
//
//    model.getCandidates().add(new JTextField("C1"));
//    model.getVoters().add(new JTextField("V1"));
//    this.setResizable(true);
//    update();
//
//    addCandidate.addActionListener(controller);
//    removeCandidate.addActionListener(controller);
//    addVoter.addActionListener(controller);
//    removeVoter.addActionListener(controller);
//
//    newButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent arg0) {
//        controller.reset();
//      }
//    });
//
//    loadDataButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                controller.loadData();
//            }
//        });
//
//    saveButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent arg0) {
//        controller.save();
//      }
//    });
//
//    saveAsButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent arg0) {
//        controller.saveAs();
//      }
//    });
//
//    undoButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent arg0) {
//        controller.undo();
//      }
//    });
//
//    redoButton.addActionListener(new ActionListener() {
//      @Override
//      public void actionPerformed(ActionEvent arg0) {
//        controller.redo();
//      }
//    });
//
////    startStopButton.addActionListener(new ActionListener() {
////      @Override
////      public void actionPerformed(ActionEvent arg0) {
////        controller.startStop();
////      }
////    });
//
//    this.addMouseWheelListener(controller);
//    horizontalScroll.addAdjustmentListener(controller);
//    verticalScroll.addAdjustmentListener(controller);
//
////    try {
////      Thread.sleep(500);
////    } catch (InterruptedException e) {
////      e.printStackTrace();
////    }
//    //model.updateRows();
//  }
//
//  public void update() {
//    for (int i = 0; i < model.getAmountVoters(); i++) {
//      this.remove(model.getRows().get(i));
//      this.remove(horizontalScroll);
//      this.remove(verticalScroll);
//    }
//    for (Iterator<JTextField> iterator = model.getCandidates().iterator(); iterator.hasNext();) {
//      JTextField cField = (JTextField) iterator.next();
//      this.remove(cField);
//    }
//    for (Iterator<JTextField> iterator = model.getVoters().iterator(); iterator.hasNext();) {
//      JTextField vField = (JTextField) iterator.next();
//      this.remove(vField);
//    }
//      if (model.getAmountCandidates() > model.getFieldsPerWidth()) {
//          horizontalScroll.setBounds(
//              (int) (model.getBorderMarginSmall()
//                     + (model.getElementWidth() * 3 - model.getScrollBarWidth() / 2)),
//              model.getBorderMarginSmall() * 2,
//              Math.min(model.getAmountCandidates() *  model.getWidthMultiplier(),
//                       (this.getWidth() - 2 * (model.getBorderMarginSmall()
//                                               + (model.getElementWidth() * 3
//                                               - model.getScrollBarWidth() / 2)))),
//              model.getScrollBarWidth());
//          horizontalScroll.setValues(model.getHorizontalOffset(), 1, 0,
//                                     (model.getAmountCandidates() - 1)
//                                         * model.getElementWidth() * 2);
//          this.add(horizontalScroll);
//    } else {
//        model.setHorizontalOffset(0);
//    }
//
//      if (model.getAmountVoters() > model.getFieldsPerHeight()) {
//          verticalScroll.setBounds(
//              (int) (model.getBorderMarginSmall()
//                     + (model.getElementWidth() * 2 - model.getScrollBarWidth() / 2)),
//              model.getBorderMarginSmall() * 2 + model.getElementHeight(),
//              model.getScrollBarWidth(),
//              Math.min(model.getAmountVoters() * model.getHeightMultiplier(),
//                       (this.getHeight() - 2 * (model.getBorderMarginSmall() * 2
//                                                + model.getElementHeight()))));
//          verticalScroll.setValues(model.getVerticalOffset(), 1, 0,
//                                   (model.getAmountVoters() -1) * model.getElementHeight() * 2);
//          this.add(verticalScroll);
//    } else {
//          model.setVerticalOffset(0);
//    }
//
//      addCandidate.setBounds(
//          ((model.getAmountCandidates() + 2) *  model.getWidthMultiplier()
//              - model.getHorizontalOffset()),
//          model.getBorderMarginSmall(), model.getElementWidth(),
//          model.getElementHeight());
//      this.add(addCandidate);
//
//      addVoter.setBounds(
//          model.getBorderMarginSmall(),
//          (int) ((model.getAmountVoters() + 1) * model.getHeightMultiplier()
//                 + model.getBorderMarginSmall() + model.getElementHeight())
//          - model.getVerticalOffset(),
//          model.getElementWidth(),
//          model.getElementHeight());
//      this.add(addVoter);
//
//      removeCandidate.setBounds(model.getBorderMarginSmall() + model.getElementWidth(),
//                                model.getBorderMarginSmall(),
//                                model.getElementWidth(),
//                                model.getElementHeight());
//      this.add(removeCandidate);
//      removeVoter.setBounds(model.getBorderMarginSmall(),
//                            model.getBorderMarginSmall() + model.getElementHeight(),
//                            model.getElementWidth(),
//                            model.getElementHeight());
//      this.add(removeVoter);
//
//      for (int i = 0; i < model.getAmountVoters(); i++) {
//          model.getRows().get(i).setBounds(
//              (int) (model.getBorderMarginSmall() + model.getElementWidth() * 3),
//              model.getBorderMarginSmall()
//              + ((i + 1) * model.getHeightMultiplier()
//              + model.getElementHeight()
//              - model.getVerticalOffset()),
//              model.getWidthMultiplier() * model.getAmountCandidates(),
//              model.getElementHeight());
//      if (model.getRows().get(i).getY()
//              >= model.getBorderMarginSmall()
//                 + model.getHeightMultiplier()
//                 + model.getElementHeight()) {
//              model.getRows().get(i).setOffset(model.getHorizontalOffset());
//              this.add(model.getRows().get(i));
//          }
//      }
//
//      for (int i = 0; i < model.getVoters().size(); i++) {
//          model.getVoters().get(i).setBounds(model.getBorderMarginSmall(),
//                  (int) ((i + 1) * model.getHeightMultiplier()
//                         + model.getBorderMarginSmall()
//                         + model.getElementHeight()
//                         - model.getVerticalOffset()),
//                  model.getElementWidth(),
//                  model.getElementHeight());
//          if (model.getVoters().get(i).getY()
//              >= (model.getHeightMultiplier()
//                  + model.getBorderMarginSmall()
//                  + model.getElementHeight())) {
//        this.add(model.getVoters().get(i));
//      }
//    }
//
//      for (int i = 0; i < model.getCandidates().size(); i++) {
//          model.getCandidates().get(i).setBounds(
//              ((i + 2) *  model.getWidthMultiplier())
//              - model.getHorizontalOffset(),
//              model.getBorderMarginSmall(),
//              model.getElementWidth(),
//              model.getElementHeight());
//          if (model.getCandidates().get(i).getX() >= (2 *  model.getWidthMultiplier())) {
//              this.add(model.getCandidates().get(i));
//          }
//      }
//      this.repaint();
//  }
//
//  @Override
//  public void actionPerformed(ActionEvent arg0) {
//    controller.setMode((String) modeListBox.getSelectedItem());
//  }
//
////    public void componentResized(ComponentEvent e) {
////        fieldsPerWidth =
////            (int) ((this.getWidth() - 2 * (borderMarginSmall))
////                       / (1.25 *  model.getWidthMultiplier()));
////        fieldsPerHeight =
////            (int) ((this.getHeight() - 2 * (borderMarginSmall))
////                       / (1.25 * heightMultiplier));
////
////        updateRows();
////        update();
////  }
//
////  public void actionPerformed(ActionEvent e) {
////    if (e.getSource() == addCandidate) {
////      amountCandidates++;
////      for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
////        RowOfValues row = (RowOfValues) iterator.next();
////        row.addColumn();
////        row.repaint();
////      }
////      candidates.add(new JTextField("C" + model.getAmountCandidates()));
////    } else if (e.getSource() == removeCandidate) {
////      if (amountCandidates > 1) {
////        amountCandidates--;
////        for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
////          RowOfValues row = (RowOfValues) iterator.next();
////          row.removeColumn();
////        }
////        this.remove(candidates.get(candidates.size() - 1));
////        candidates.remove(candidates.size() - 1);
////        horizontalOffset =
////            Math.min(horizontalOffset,
////                     (amountCandidates -1) * model.getElementWidth() * 2);
////      }
////        } else if (e.getSource() == addVoter) {
////            amountVoters++;
////            rows.add(new RowOfValues(this, container, model.getAmountCandidates(),
//                                       model.getElementWidth(), model.getElementHeight(),
//                                       model.getWidthMultiplier()));
////            for (Iterator<RowOfValues> iterator = rows.iterator(); iterator.hasNext();) {
////                RowOfValues row = (RowOfValues) iterator.next();
////                row.repaint();
////            }
////            voters.add(new JTextField("V" + amountVoters));
////
////    } else if (e.getSource() == removeVoter) {
////      if (amountVoters > 1) {
////        amountVoters--;
////        this.remove(rows.get(rows.size() - 1));
////        rows.remove(rows.size() - 1);
////        this.remove(voters.get(voters.size() - 1));
////        voters.remove(voters.size() - 1);
////        verticalOffset =
////            Math.min(verticalOffset,
////                    (amountVoters - 1) * model.getElementHeight() * 2);
////      }
////    }
////    update();
////  }
//
////  @Override
////  public void componentHidden(ComponentEvent arg0) {
////    // TODO Auto-generated method stub
////  }
////
////  @Override
////  public void componentMoved(ComponentEvent e) {
////    // TODO Auto-generated method stub
////  }
////
////  @Override
////  public void componentShown(ComponentEvent e) {
////    // TODO Auto-generated method stub
////  }
//
////  @Override
////  public void adjustmentValueChanged(AdjustmentEvent e) {
////    if (e.getSource() == horizontalScroll) {
////      horizontalOffset = horizontalScroll.getValue();
////    }
////
////    if (e.getSource() == verticalScroll) {
////      verticalOffset = verticalScroll.getValue();
////    }
////    update();
////  }
//
////  @Override
////  public void mouseWheelMoved(MouseWheelEvent event) {
////    int amount = event.getWheelRotation();
////    if(amount > 0 ) {
////      verticalOffset = Math.min(verticalOffset + amount * 5,
//                                        (amountVoters - 1) * model.getElementHeight() * 2);
////      update();
////    } else {
////      verticalOffset = Math.max(verticalOffset + amount * 5,  0);
////      update();
////    }
////  }
//}
