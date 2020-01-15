package edu.pse.beast.highlevel.javafx.resultpresenter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.CBMCOutput;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.ResultPresentationType;
import edu.pse.beast.propertychecker.Result;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * This class is responsible to show the result of Checks. It provides ways of
 * simply printing Text, drawing images with support for charting, and display
 * any JavaFX node.
 *
 * @author Lukas Stapelbroek
 *
 */
public final class ResultPresenterNEW {
    private static ResultPresenterNEW instance;
    private ResultPresentationType presentationType;
    private final ScrollPane resultScrollPane;
    private Result result = null;

    private ResultPresenterNEW() {
        this.resultScrollPane = GUIController.getController().getResultScrollPane();

        GUIController.getController().getZoomSlider().valueProperty()
                .addListener(new ChangeListener<>() {
                    @Override
                    public void changed(final ObservableValue<? extends Number> observable,
                                        final Number oldValue, final Number newValue) {
                        if (presentationType != null && presentationType.supportsZoom()) {
                            presentationType.zoomTo((double) newValue);
                        }
                    }
                });
    }

    private void getDefaultPresentation() {
        List<ResultPresentationType> types = getEligablePresentationTypes();
        for (Iterator<ResultPresentationType> iterator = types.iterator(); iterator.hasNext();) {
            ResultPresentationType type = (ResultPresentationType) iterator.next();
            if (!result.isValid()) {
                setPresentationType(new CBMCOutput());
            } else if (type.isDefault()) {
                setPresentationType(type);
            }

        }
    }

    /**
     * Removes all children from the result pane.
     */
    private void reset() {
        ResultImageRenderer.reset();
        GUIController.getController().setEligableTypes(new ArrayList<ResultPresentationType>());
        resultScrollPane.setContent(null);
        // resultPane.getChildren().clear();
    }

    public void setResult(final Result resultVal) {
        boolean changed = (this.result != resultVal);
        this.result = resultVal;
        if (changed) {
            getDefaultPresentation();
            showResult();
        }
    }

    private List<ResultPresentationType> getEligablePresentationTypes() {
        this.presentationType = null;
        List<ResultPresentationType> eligableTypes = new ArrayList<ResultPresentationType>();

        for (Iterator<ResultPresentationType> iterator =
                ResultPresentationType.getImplementations().iterator();
                iterator.hasNext();) {
            ResultPresentationType typeToCheck = (ResultPresentationType) iterator.next();
            if (typeToCheck.supports(result.getAnalysisType())) {
                eligableTypes.add(typeToCheck);
            }
        }
        this.setPresentationType(new CBMCOutput()); // TODO maybe find a better way to find the
                                                    // best presenter for every type
        GUIController.getController().setEligableTypes(eligableTypes);
        return eligableTypes;
    }

    public void setPresentationType(final ResultPresentationType presType) {
        GUIController.getController().setPresentationTypeText(presType.getName());
        boolean changed = (this.presentationType != presType);
        this.presentationType = presType;
        if (changed) {
            showResult();
        }
    }

    private void showResult() {
        reset();
        if (result == null) {
            return;
        }
        Node finishedResult = presentationType.presentResult(result);
        GUIController.getController().disableZoomSlider(!presentationType.supportsZoom());
        this.setResultNode(finishedResult);
    }

    /**
     * Give the caller complete freedom how they want to display the result. It
     * can be done in any way javafx permits.
     *
     * @param resultNode
     *            the Node which will be shown in the result window
     */
    public void setResultNode(final Node resultNode) {
        ResultImageRenderer.resetScrollBars();
        // resultPane.getChildren().add(resultNode);
        resultScrollPane.setContent(resultNode);
    }

    public static synchronized ResultPresenterNEW getInstance() {
        if (instance == null) {
            instance = new ResultPresenterNEW();
        }
        return instance;
    }
}
