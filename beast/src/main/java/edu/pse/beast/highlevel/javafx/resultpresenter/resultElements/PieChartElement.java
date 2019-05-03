package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.pse.beast.toolbox.Tuple3;
import javafx.scene.input.MouseEvent;

public class PieChartElement extends ResultImageElement {
    private List<Tuple3<String, Double, Color>> resultValues;
    private double totalSize = 0;
    private double width = 0;
    private double height = 0;

    public PieChartElement(double xPosTopLeft, double yPosTopLeft, double width, double height,
            List<Tuple3<String, Double, Color>> resultValues) {
        super(xPosTopLeft, yPosTopLeft, xPosTopLeft + width, yPosTopLeft + height);
        this.resultValues = resultValues;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        double tmpSize = 0;
        // iterate over the values, adding up their sizes, therefore getting the total
        // size of the pie chart;
        List<Tuple3<String, Double, Color>> remove
                = new LinkedList<Tuple3<String, Double, Color>>();
        for (Tuple3<String, Double, Color> value : resultValues) {
            if (value.second == 0) {
                System.err.println("You are not allowed to have fields "
                                   + "with zero size in this chart");
                remove.add(value); //remove this element from the chart
            } else {
                tmpSize += Math.abs(value.second);
            }
        }
        resultValues.removeAll(remove);
        this.totalSize = tmpSize;
    }

    @Override
    public void isClicked(MouseEvent event) {
        //do nothing so far
    }

    @Override
    public void drawElement(Graphics2D graphics, double scale) {
        if (totalSize == 0) {
            System.err.println("The pie chart was not given a size larger than zero!");
        } else {
            double currentAngle = 0;
            for (Iterator<Tuple3<String, Double, Color>> iterator = resultValues.iterator();
                    iterator.hasNext();) {
                Tuple3<String, Double, Color> value
                    = (Tuple3<String, Double, Color>) iterator.next();
                double neededAngle = 360 * (value.second / totalSize);
                if (!iterator.hasNext()) { // this is the last element, so we have to fill
                    neededAngle = 360 - currentAngle; //so we have to fill the rest of the circle
                }
                graphics.setColor(value.third);
                graphics.fill(new Arc2D.Double(super.getxPosTopLeft() * scale,
                                               super.getyPosTopLeft() * scale,
                                               width * scale, height * scale,
                                               currentAngle, neededAngle, Arc2D.PIE));
                currentAngle = currentAngle + neededAngle;
            }
        }
    }
}
