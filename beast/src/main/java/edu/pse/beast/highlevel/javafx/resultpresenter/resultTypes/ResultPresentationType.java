package edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import edu.pse.beast.highlevel.javafx.AnalysisType;
import edu.pse.beast.highlevel.javafx.resultpresenter.ResultPresenterNEW;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;

public abstract class ResultPresentationType {
    static final int STANDARD_SIZE = 10;

    private MenuItem menuItem = null;
    public abstract Node presentResult(Result result);
    public abstract String getName();
    public abstract String getToolTipDescription();

    /**
     *
     * @return true, if the Node given by this Type support zooming
     */
    public abstract boolean supportsZoom();

    /**
     *
     * @return all implementations of this class
     */
    public static List<ResultPresentationType> getImplementations() {
        ServiceLoader<ResultPresentationType> loader =
                ServiceLoader.load(ResultPresentationType.class);
        List<ResultPresentationType> implementations = new ArrayList<ResultPresentationType>();
        for (Iterator<ResultPresentationType> iterator = loader.iterator(); iterator.hasNext();) {
            ResultPresentationType implementation = iterator.next();
            implementations.add(implementation);
        }
        return implementations;
    }

    public MenuItem getMenuItem() {
        if (menuItem != null) {
            return menuItem;
        } else {
            CustomMenuItem item = new CustomMenuItem(new Label(getName()));
            Tooltip tip = new Tooltip(getToolTipDescription());
            Tooltip.install(item.getContent(), tip);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    ResultPresenterNEW.getInstance()
                            .setPresentationType(ResultPresentationType.this);
                }
            });
            this.menuItem = item;
            return item;
        }
    }

    public abstract void zoomTo(double zoomValue);

    public abstract boolean supports(AnalysisType analysisType);

    /**
     * Extracts the value of single value objects. It is used to extract the size
     * of each vote.
     *
     * @param toExtract
     *            has be of the form: ValueWrapper -> ResultValueSingle<Integer>
     * @return
     */
    public Map<Integer, Long> getAllSizes(final List<ResultValueWrapper> toExtract) {
        HashMap<Integer, Long> toReturn = new HashMap<Integer, Long>();
        for (Iterator<ResultValueWrapper> iterator = toExtract.iterator(); iterator.hasNext();) {
            ResultValueWrapper currentWrapper = (ResultValueWrapper) iterator.next();
            int index = currentWrapper.getMainIndex();
            CBMCResultValueSingle singleValue =
                    (CBMCResultValueSingle) currentWrapper.getResultValue();
            toReturn.put(index, (Long) singleValue.getNumberValue());
        }
        return toReturn;
    }

    /**
     * Indicates if this class should be normally used first to display a result.
     *
     * @return
     */
    public abstract boolean isDefault();
}
