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
    private static final int CIRCLE_ANGLE = 360;
    private List<Tuple3<String, Double, Color>> resultValues;
    private double totalSize = 0;
    private double width = 0;
    private double height = 0;

    public PieChartElement(final double xPositionTopLeft,
                           final double yPositionTopLeft,
                           final double chartWidth,
                           final double chartHeight,
                           final List<Tuple3<String, Double, Color>> resultVals) {
        super(xPositionTopLeft, yPositionTopLeft,
                xPositionTopLeft + chartWidth,
                yPositionTopLeft + chartHeight);
        this.resultValues = resultVals;
        this.width = chartWidth;
        this.height = chartHeight;
        init();
    }

    private void init() {
        double tmpSize = 0;
        // iterate over the values, adding up their sizes, therefore getting the total
        // size of the pie chart;
        List<Tuple3<String, Double, Color>> remove
                = new LinkedList<Tuple3<String, Double, Color>>();
        for (Tuple3<String, Double, Color> value : resultValues) {
            if (value.second() == 0) {
                System.err.println("You are not allowed to have fields "
                                   + "with zero size in this chart");
                remove.add(value); //remove this element from the chart
            } else {
                tmpSize += Math.abs(value.second());
            }
        }
        resultValues.removeAll(remove);
        this.totalSize = tmpSize;
    }

    @Override
    public void isClicked(final MouseEvent event) {
        //do nothing so far
    }

    @Override
    public void drawElement(final Graphics2D graphics, final double scale) {
        if (totalSize == 0) {
            System.err.println("The pie chart was not given a size larger than zero!");
        } else {
            double currentAngle = 0;
            for (Iterator<Tuple3<String, Double, Color>> iterator = resultValues.iterator();
                    iterator.hasNext();) {
                Tuple3<String, Double, Color> value
                    = (Tuple3<String, Double, Color>) iterator.next();
                double neededAngle = CIRCLE_ANGLE * (value.second() / totalSize);
                if (!iterator.hasNext()) { // this is the last element, so we have to fill
                    // so we have to fill the rest of the circle
                    neededAngle = CIRCLE_ANGLE - currentAngle;
                }
                graphics.setColor(value.third());
                graphics.fill(new Arc2D.Double(super.getxPosTopLeft() * scale,
                                               super.getyPosTopLeft() * scale,
                                               width * scale, height * scale,
                                               currentAngle, neededAngle, Arc2D.PIE));
                currentAngle = currentAngle + neededAngle;
            }
        }
    }
}
