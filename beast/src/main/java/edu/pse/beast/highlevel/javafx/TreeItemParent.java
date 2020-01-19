package edu.pse.beast.highlevel.javafx;

//import javafx.event.EventHandler;
//import javafx.scene.control.TreeCell;
//import javafx.scene.input.MouseEvent;
//
//public class TreeItemParent extends TreeCell<CustomTreeItem> implements TreeItemListener {
//  private TreeItemChild check  = new TreeItemChild("Check");
//  private TreeItemChild margin = new TreeItemChild("Margin");
//
//  public TreeItemParent(String name) {
//    this.setText(name);
//
//    this.getChildren().add(check);
//    this.getChildren().add(margin);
//
//    this.setOnMouseClicked(new EventHandler<MouseEvent>() {
//      @Override
//      public void handle(MouseEvent event) {
//        System.out.println("hier das öffnen window öffnen und mit text befüllen");
//      }
//    });
//  }
//
//  @Override
//  public void treeCellWasClicked(TreeItemChild child) {
//    switch (child.getAnalysisStatus()) {
//    case FAILURE:
//      System.out.println("HIER an das ergebnis window anschließen");
//      break;
//    case ERROR:
//      System.out.println("HIER auch an das ergebnis window anschließen");
//
//    default:
//      break;
//    }
//  }
//}
