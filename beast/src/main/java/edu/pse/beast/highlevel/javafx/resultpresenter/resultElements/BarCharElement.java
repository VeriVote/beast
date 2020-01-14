package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.pse.beast.toolbox.Tuple3;
import javafx.scene.input.MouseEvent;

public class BarCharElement extends ResultImageElement {
    private List<Tuple3<String, Double, Color>> resultValues;
    private double totalSize = 0;

    public BarCharElement(double xPosTopLeft, double yPosTopLeft,
                          double xPosBottomRight, double yPosBottomRight,
                          List<Tuple3<String, Double, Color>> resultValues) {
        super(xPosTopLeft, yPosTopLeft, xPosBottomRight, yPosBottomRight);
        init();
    }

    private void init() {
        double tmpSize = 0;
        // iterate over the values, adding up their sizes, therefore getting the
        // total size of the pie chart;
        List<Tuple3<String, Double, Color>> remove =
                new LinkedList<Tuple3<String, Double, Color>>();
        for (Tuple3<String, Double, Color> value: resultValues) {
            if (value.second == 0) {
                System.err.println("You are not allowed to have fields "
                                   + "with zero size in this chart");
                remove.add(value); // remove this element from the chart
            } else {
                tmpSize += Math.abs(value.second);
            }
        }
        resultValues.removeAll(remove);
        this.totalSize = tmpSize;
    }

    @Override
    public void isClicked(MouseEvent event) {
        // do nothing so far
    }

    @Override
    public void drawElement(Graphics2D graphics, double scale) {
        if (totalSize == 0) {
            System.err.println(
                    "The bar chart was not given a size larger than zero!");
        } else {
            for (Iterator<Tuple3<String, Double, Color>> iterator = resultValues.iterator();
                    iterator.hasNext();) {
                // TODO implement bar chart later
            }
        }
    }
}
