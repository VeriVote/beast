//package edu.pse.beast.electionsimulator;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.AdjustmentEvent;
//import java.awt.event.AdjustmentListener;
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
//import java.awt.event.MouseWheelEvent;
//import java.awt.event.MouseWheelListener;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.swing.JTextField;
//
//import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//import edu.pse.beast.electionsimulator.model.ElectionSimulationModel;
//import edu.pse.beast.electionsimulator.model.RowOfValues;
//import edu.pse.beast.highlevel.BEASTCommunicator;
//import edu.pse.beast.stringresource.StringLoaderInterface;
//import edu.pse.beast.types.InputType;
//import edu.pse.beast.types.OutputType;
//
//public class ElectionSimulation
//                implements Runnable, ActionListener,
//                           ComponentListener, AdjustmentListener,
//                           MouseWheelListener, ElectionDescriptionChangeListener {
//
//  private static ElectionTypeContainer container;
//
//  //private PSECentralObjectProvider centralObjectProvider;
//
//  private boolean react = false;
//
//  private boolean isOpen = false;
//
//  private static boolean initiated = false;
//
//  private StringLoaderInterface sli;
//
//  private static Modes currentMode = Modes.cbmc;
//
//  public enum Modes {
//    vanilla, cbmc
//  }
//
//  private static ElectionSimulationWindow view;
//  private static ElectionSimulationModel model;
//
//  public ElectionSimulation() {
//    this(new StringLoaderInterface("en"));
//  }
//
//  public ElectionSimulation(StringLoaderInterface sli) {
//    this.sli = sli;
//  }
//
//  public void init() {
//      // this.centralObjectProvider = centralObjectProvider;
//      // ElectionSimulation.container =
//      //     centralObjectProvider.getElectionDescriptionSource()
//      //     .getElectionDescription() .getContainer();
//      // ElectionSimulation.model = new ElectionSimulationModel(container);
//      // ElectionSimulation.view = new ElectionSimulationWindow(sli, container, this, model);
//      //
//      // centralObjectProvider.getCElectionEditor().addListener(this);
//      //
//      // this.start();
//      // react = true;
//      //
//      // initiated = true;
//  }
//
//  @Override
//  public void run() {
//    // TODO Auto-generated method stub
//  }
//
//  @Override
//  public void mouseWheelMoved(MouseWheelEvent event) {
//    if (react) {
//      int amount = event.getWheelRotation();
//      if (amount > 0) {
//        model.setVerticalOffset(Math.min(model.getVerticalOffset() + amount * 5,
//            (model.getAmountVoters() - 1) * model.getElementHeight() * 2));
//        view.update();
//      } else {
//        model.setVerticalOffset(Math.max(model.getVerticalOffset() + amount * 5, 0));
//        view.update();
//      }
//    }
//  }
//
//  @Override
//  public void adjustmentValueChanged(AdjustmentEvent e) {
//    if (react) {
//      if (e.getSource() == view.horizontalScroll) {
//        model.setHorizontalOffset(view.horizontalScroll.getValue());
//      }
//      if (e.getSource() == view.verticalScroll) {
//        model.setVerticalOffset(view.verticalScroll.getValue());
//      }
//      view.update();
//    }
//  }
//
//  @Override
//  public void componentHidden(ComponentEvent arg0) {
//    // TODO Auto-generated method stub
//  }
//
//  @Override
//  public void componentMoved(ComponentEvent arg0) {
//    // TODO Auto-generated method stub
//  }
//
//  @Override
//  public void componentResized(ComponentEvent arg0) {
//    if (react) {
//      model.setFieldsPerWidth((int) ((view.getWidth() - 2 * (model.getBorderMarginSmall()))
//          / (1.25 * model.getWidthMultiplier())));
//      model.setFieldsPerHeight((int) ((view.getHeight() - 2 * (model.getBorderMarginSmall()))
//          / (1.25 * model.getHeightMultiplier())));
//      updateRows();
//      view.update();
//    }
//  }
//
//  @Override
//  public void componentShown(ComponentEvent arg0) {
//    // TODO Auto-generated method stub
//  }
//
//  @Override
//  public void actionPerformed(ActionEvent e) {
//    if (react) { // if the main plane should react to inputs, these
//            // conditions get checked
//      if (e.getSource() == view.addCandidate) {
//        model.setAmountCandidates(model.getAmountCandidates() + 1);
//        for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
//          RowOfValues row = (RowOfValues) iterator.next();
//          while (row.getAmountCandidates() < model.getAmountCandidates()) {
//            row.addColumn();
//            row.repaint();
//          }
//        }
//
//        while (model.getCandidates().size() < model.getAmountCandidates()) {
//          model.getCandidates().add(new JTextField("C" + model.getAmountCandidates()));
//        }
//      } else if (e.getSource() == view.removeCandidate) {
//        if (model.getAmountCandidates() > 1) {
//          model.setAmountCandidates(model.getAmountCandidates() - 1);
//          for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
//            RowOfValues row = (RowOfValues) iterator.next();
//            while (row.getAmountCandidates() > model.getAmountCandidates()) {
//              row.removeColumn();
//            }
//          }
//          while (model.getCandidates().size() > model.getAmountCandidates()) {
//            view.remove(model.getCandidates().get(model.getCandidates().size() - 1));
//            model.getCandidates().remove(model.getCandidates().size() - 1);
//            model.setHorizontalOffset(Math.min(model.getHorizontalOffset(),
//                (model.getAmountCandidates() - 1) * model.getElementWidth() * 2));
//          }
//        }
//      } else if (e.getSource() == view.addVoter) {
//        model.setAmountVoters(model.getAmountVoters() + 1);
//
//        while (model.getRows().size() < model.getAmountVoters()) {
//          model.getRows().add(new RowOfValues(model, container, model.getAmountCandidates(),
//              model.getElementWidth(), model.getElementHeight(), model.getWidthMultiplier()));
//          model.getVoters().add(new JTextField("V" + model.getAmountVoters()));
//        }
//        for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
//          RowOfValues row = (RowOfValues) iterator.next();
//          row.repaint();
//        }
//
//      } else if (e.getSource() == view.removeVoter) {
//        if (model.getAmountVoters() > 1) {
//          model.setAmountVoters(model.getAmountVoters() - 1);
//          view.remove(model.getRows().get(model.getRows().size() - 1));
//          model.getRows().remove(model.getRows().size() - 1);
//          view.remove(model.getVoters().get(model.getVoters().size() - 1));
//          model.getVoters().remove(model.getVoters().size() - 1);
//          model.setVerticalOffset(Math.min(model.getVerticalOffset(),
//              (model.getAmountVoters() - 1) * model.getElementHeight() * 2));
//        }
//      }
//      view.update();
//    }
//  }
//
//  public synchronized void updateRows() {
//    if (react) {
//      for (Iterator<RowOfValues> iterator = model.getRows().iterator(); iterator.hasNext();) {
//        RowOfValues row = (RowOfValues) iterator.next();
//        row.update();
//      }
//    }
//  }
//
//  @Override
//  public void inputChanged(InputType input) {
//    electionTypeChanged();
//  }
//
//  @Override
//  public void outputChanged(OutputType output) {
//    electionTypeChanged();
//  }
//
//  /**
//   * gets called when one of the changed listener gets called here, we update the
//   * models which are used
//   */
//  private void electionTypeChanged() {
////    ElectionSimulation.container =
////        centralObjectProvider.getElectionDescriptionSource()
////        .getElectionDescription().getContainer();
////    model.changeContainer(container);
//  }
//
//  /**
//   * Displays the view.
//   */
//  public void start() {
//    java.awt.EventQueue.invokeLater(this);
//  }
//
//  // TODO
//  /**
//   * resets all fields and returns the view back to its original state
//   */
//  public void reset() {
//    // TODO Auto-generated method stub
//  }
//
//  public void save() {
//    // TODO Auto-generated method stub
//  }
//
//  public void saveAs() {
//    // TODO Auto-generated method stub
//  }
//
//  public void loadData() {
//    // TODO Auto-generated method stub
//  }
//
//  public void undo() {
//    // TODO Auto-generated method stub
//  }
//
//  public void redo() {
//    // TODO Auto-generated method stub
//  }
//
//  public void open() {
//      // if (!initiated) {
//      //     init((PSECentralObjectProvider) BEASTCommunicator.getCentralObjectProvider());
//      // }
//      isOpen = true;
//      view.setVisible(true);
//  }
//
//  public void close() {
//    isOpen = false;
//    view.setVisible(false);
//  }
//
//  public boolean isOpen() {
//    return isOpen;
//  }
//
//  // TODO schöner machen, vielleicht das enum mit der combobox verbinden, damit es
//  // automatisch geht
//  public void setMode(String selectedItem) {
//    switch (selectedItem) {
//    case "outcome (c)":
//      currentMode = Modes.vanilla;
//      break;
//    case "outcome  (cbmc)":
//      currentMode = Modes.cbmc;
//      break;
//    default:
//      break;
//    }
//  }
//
//  public static String[][] getVotingData() {
//    String[][] votingData = { { "0" }, { "0" } };
//    ;
//    if (initiated) {
//      votingData = new String[model.getAmountVoters()][model.getAmountCandidates()];
//      // read the data in a 2d array
//      for (int i = 0; i < model.getAmountVoters(); i++) {
//        for (int j = 0; j < model.getAmountCandidates(); j++) {
//          votingData[i][j] = model.getRows().get(i).getValues().get(j);
//        }
//      }
//    }
//    return votingData;
//  }
//
//  public static int getNumVoters() {
//    if (!initiated) {
//      return 1;
//    } else {
//      return model.getAmountVoters();
//    }
//  }
//
//  public static int getNumCandidates() {
//    if (!initiated) {
//      return 1;
//    } else {
//      return model.getAmountCandidates();
//    }
//  }
//
//  public static int getNumSeats() {
//    return BEASTCommunicator.getCentralObjectProvider()
//           .getParameterSrc().getParameter().getAmountSeats().get(0);
//  }
//
//  public static Modes getMode() {
//    return currentMode;
//  }
//
//  public static List<List<String>> getVotingDataListofList() {
//    List<List<String>> toReturn = new ArrayList<List<String>>();
//    String[][] data = getVotingData();
//    for (int i = 0; i < data.length; i++) {
//      List<String> tmp = new ArrayList<String>();
//      for (int j = 0; j < data[0].length; j++) {
//        tmp.add(data[i][j]);
//      }
//      toReturn.add(tmp);
//    }
//    return toReturn;
//  }
//
//  public static int getNumVotingPoints() {
//    return container.getInputType().getNumVotingPoints(getVotingData());
//  }
//
//  public static String getPartyName(int index) {
//    return model.getCandidates().get(index).getText();
//  }
//
//  public static String getVoterName(int index) {
//    return model.getVoters().get(index).getText();
//  }
//}