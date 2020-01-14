//package edu.pse.beast.electionsimulator.model;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.SwingUtilities;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//
//public class RowOfValues extends JPanel implements DocumentListener {
//
//  /**
//   *
//   */
//  private static final long serialVersionUID = 2852540931615771801L;
//  private int elementHeight;
//  private int elementWidth;
//
//  private int widthMultiplier;
//
//  private int offset = 0;
//  private ArrayList<String> values;
//  private ArrayList<JTextField> fields;
//
//  private ElectionTypeContainer container;
//
//  private int amountOfCandidates = 0;
//
//
//  private boolean locked = false;
//
//  public RowOfValues(ElectionSimulationModel parent,
//                     ElectionTypeContainer container,
//                     int amountOfCandidates,
//                     int elementWidth, int elementHeight,
//                     int widthMultiplier) {
//    this.container = container;
//    this.elementWidth = elementWidth;
//    this.elementHeight = elementHeight;
//    this.widthMultiplier = widthMultiplier;
//    values = new ArrayList<>(amountOfCandidates);
//    fields = new ArrayList<>(amountOfCandidates);
//    this.setLayout(null);
//    this.setVisible(true);
//
//    for (int i = 0; i < amountOfCandidates; i++) {
//      addColumn();
//    }
//  }
//
//  public void addColumn() {
//    amountOfCandidates++;
//    values.add("0");
//
//    JTextField toAdd = new JTextField("0");
//    toAdd.getDocument().addDocumentListener(this);
//    toAdd.setSize(elementWidth, elementHeight);
//
//    fields.add(toAdd);
//    update();
//  }
//
//  public void removeColumn() {
//    amountOfCandidates--;
//    this.remove(fields.get(fields.size() - 1));
//    fields.remove(fields.size() - 1);
//    update();
//  }
//
//  public void update() {
//    for (Iterator<JTextField> iterator = fields.iterator(); iterator.hasNext();) {
//      JTextField field = (JTextField) iterator.next();
//      this.remove(field);
//    }
//
//    this.setSize(widthMultiplier * (amountOfCandidates), elementHeight * 2);
//
//    for (int i = 0; i < amountOfCandidates; i++) {
//      fields.get(i).setBounds(widthMultiplier * i - offset, 0, elementWidth, elementHeight);
//      this.add(fields.get(i));
//    }
//
//    this.setVisible(true);
//    this.repaint();
//  }
//
//  private void checkAndInsertValue(String newValue, int position) {
//    String vettedValue = container.getInputType().vetValue(newValue, container, this);
//
//    values.set(position, vettedValue);
//    // update all values that will get shown
//    for (int i = 0; i < values.size(); i++) {
//      try {
//          fields.get(i).setText("" + values.get(i));
//      } catch (Exception e) {
//        System.err.println(e);
//      }
//    }
//  }
//
//  @Override
//  public void changedUpdate(DocumentEvent arg0) {
//    allEventUpdates(arg0);
//  }
//
//  @Override
//  public void insertUpdate(DocumentEvent arg0) {
//    allEventUpdates(arg0);
//  }
//
//  @Override
//  public void removeUpdate(DocumentEvent arg0) {
//    allEventUpdates(arg0);
//  }
//
//  public void setOffset(int offset) {
//    this.offset = offset;
//    update();
//  }
//
//  private void allEventUpdates(DocumentEvent event) {
//    if (locked) {
//      return;
//    }
//    for (int i = 0; i < fields.size(); i++) {
//      if (fields.get(i).getDocument() == event.getDocument()) {
//        final int finalI = i;
//        int value;
//        try {
//          value = Integer.parseInt(fields.get(i).getText());
//        } catch (Exception e) {
//          value = 0;
//        }
//
//        final int finalV = value;
//        Runnable callValueChecker = new Runnable() {
//          @Override
//          public void run() {
//            locked = true;
//            checkAndInsertValue("" + finalV, finalI);
//            locked = false;
//          }
//        };
//        SwingUtilities.invokeLater(callValueChecker);
//      }
//    }
//  }
//
//  //getter and setter
//  public ArrayList<String> getValues() {
//    return values;
//  }
//
//  public void setValues(ArrayList<String> values) {
//    this.values = values;
//  }
//
//  public ArrayList<JTextField> getFields() {
//    return fields;
//  }
//
//  public void setFields(ArrayList<JTextField> fields) {
//    this.fields = fields;
//  }
//
//  public void setContainer(ElectionTypeContainer container) {
//    this.container = container;
//    for (int i = 0; i < values.size(); i++) {
//      checkAndInsertValue("0", i);
//    }
//  }
//
//  public int getAmountCandidates() {
//    return amountOfCandidates;
//  }
//
//  public int getAmountVoters() {
//    return getAmountVoters();
//  }
//}
