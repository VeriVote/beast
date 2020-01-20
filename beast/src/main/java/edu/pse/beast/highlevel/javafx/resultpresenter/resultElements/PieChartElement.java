package edu.pse.beast.highlevel.javafx.resultpresenter.resultElements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.input.MouseEvent;

import edu.pse.beast.toolbox.Tuple3;

/**
 * The Class PieChartElement.
 *
 * @author Lukas Stapelbroek
 */
public final class PieChartElement extends ResultImageElement {

    /** The Constant CIRCLE_ANGLE. */
    private static final int CIRCLE_ANGLE = 360;

    /** The result values. */
    private List<Tuple3<String, Double, Color>> resultValues;

    /** The total size. */
    private double totalSize;

    /** The width. */
    private double width;

    /** The height. */
    private double height;

    /**
     * Instantiates a new pie chart element.
     *
     * @param xPositionTopLeft
     *            the x position top left
     * @param yPositionTopLeft
     *            the y position top left
     * @param chartWidth
     *            the chart width
     * @param chartHeight
     *            the chart height
     * @param resultVals
     *            the result vals
     */
    public PieChartElement(final double xPositionTopLeft,
                           final double yPositionTopLeft, final double chartWidth,
                           final double chartHeight,
                           final List<Tuple3<String, Double, Color>> resultVals) {
        super(xPositionTopLeft, yPositionTopLeft, xPositionTopLeft + chartWidth,
              yPositionTopLeft + chartHeight);
        this.resultValues = resultVals;
        this.width = chartWidth;
        this.height = chartHeight;
        init();
    }

    /**
     * Inits the.
     */
    private void init() {
        double tmpSize = 0;
        // iterate over the values, adding up their sizes, therefore getting the
        // total
        // size of the pie chart;
        List<Tuple3<String, Double, Color>> remove =
                new LinkedList<Tuple3<String, Double, Color>>();
        for (Tuple3<String, Double, Color> value : resultValues) {
            if (value.second() == 0) {
                System.err.println("You are not allowed to have fields "
                                    + "with zero size in this chart");
                remove.add(value); // remove this element from the chart
            } else {
                tmpSize += Math.abs(value.second());
            }
        }
        resultValues.removeAll(remove);
        this.totalSize = tmpSize;
    }

    @Override
    public void isClicked(final MouseEvent event) {
        // do nothing so far
    }

    @Override
    public void drawElement(final Graphics2D graphics, final double scale) {
        if (totalSize == 0) {
            System.err.println(
                    "The pie chart was not given a size larger than zero!");
        } else {
            double currentAngle = 0;
            for (Iterator<Tuple3<String, Double, Color>> iterator =
                    resultValues.iterator();
                    iterator.hasNext();) {
                Tuple3<String, Double, Color> value =
                        (Tuple3<String, Double, Color>) iterator.next();
                double neededAngle = CIRCLE_ANGLE
                        * (value.second() / totalSize);
                if (!iterator.hasNext()) {
                    // this is the last element, so we
                    // have to fill the rest of the circle
                    neededAngle = CIRCLE_ANGLE - currentAngle;
                }
                graphics.setColor(value.third());
                graphics.fill(new Arc2D.Double(super.getxPosTopLeft() * scale,
                                               super.getyPosTopLeft() * scale,
                                               width * scale,
                                               height * scale, currentAngle,
                                               neededAngle, Arc2D.PIE));
                currentAngle += neededAngle;
            }
        }
    }
}
