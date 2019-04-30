package edu.pse.beast.highlevel.javafx.resultpresenter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultElements.ResultImageElement;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ResultImageRenderer {

    private static double scrollPosV;
    private static double scrollPosH;

    private static Boolean drawingBlocked = false;

    private static double currentScale = 1;

    private static List<ResultImageElement> elementList = new ArrayList<ResultImageElement>();

    private static ImageView view = new ImageView();

    private static Color backgroundColor = Color.white;

    private static double imageMinWidth = GUIController.getController().getResultBorderPane().getWidth();
    private static double imageMinHeight = GUIController.getController().getResultBorderPane().getHeight();

    private static final double imageMaxWidth = 10000;
    private static final double imageMaxHeight = 10000;

    private static double imageDesiredWidth = imageMinWidth;
    private static double imageDesiredHeight = imageMinHeight;

    // the next image the graphic will be drawn on
    private static BufferedImage image = new BufferedImage((int) imageDesiredWidth, (int) imageDesiredHeight,
            BufferedImage.TYPE_4BYTE_ABGR);

    static {

        GUIController.getController().getResultBorderPane().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
                    Number newSceneWidth) {
                imageMinWidth = GUIController.getController().getResultBorderPane().getWidth();
                updateImageSizeAndRedraw();
            }
        });

        GUIController.getController().getResultBorderPane().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
                    Number newSceneHeight) {
                imageMinHeight = GUIController.getController().getResultBorderPane().getHeight();
                updateImageSizeAndRedraw();
            }
        });

        GUIController.getController().getZoomSlider().valueProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                zoomTo((double) newValue);
            }
        });

        // TODO determine which mouse behavior would fit best
        view.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                double clickX = event.getX();
                double clickY = event.getY();

                for (Iterator<ResultImageElement> iterator = elementList.iterator(); iterator.hasNext();) {
                    ResultImageElement element = (ResultImageElement) iterator.next();
                    if (element.isInside(clickX, clickY)) {
                        MouseEvent tmp_event = (MouseEvent) event.clone();
                        element.isClicked(tmp_event);
                        tmp_event.consume();
                    }
                }
                event.consume();

                drawElements();
            }
        });
    }

    public synchronized static void reset() {
        elementList.clear();
        imageDesiredWidth = imageMinWidth;
        imageDesiredHeight = imageMinHeight;
    }

    /**
     * adds an element which will be printed on the next image.
     * 
     * @param element the element which will be added to the list
     */
    public synchronized static void addElement(ResultImageElement element) {
        imageDesiredWidth = Math.max(imageDesiredWidth, element.getxPosBottomRight());
        imageDesiredHeight = Math.max(imageDesiredHeight, element.getyPosBottomRight());

        elementList.add(element);
    }

    /**
     * draw all elements on the underlying image, and show this image in the image
     * view
     */
    public static void drawElements() {
        if (image.getWidth() != imageDesiredWidth || image.getHeight() != imageDesiredHeight) {
            updateImageSize();
        }

        GUIController.getController().getZoomSlider().setDisable(false);

        synchronized (drawingBlocked) {
            if (drawingBlocked) {
                return;
            } else {
                drawingBlocked = true;
            }
        }

        drawingBlocked = true;
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        // anti-aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        for (Iterator<ResultImageElement> iterator = elementList.iterator(); iterator.hasNext();) {
            ResultImageElement element = (ResultImageElement) iterator.next();
            element.drawElement((Graphics2D) graphics.create(), currentScale);
        }

        graphics.dispose();
        view.setImage(SwingFXUtils.toFXImage(image, null));

        synchronized (drawingBlocked) {
            drawingBlocked = false;
        }
    }

    public static ImageView getImageView() {
        return view;
    }

    private static void updateImageSize() {
        image = new BufferedImage((int) (Math.max(imageMinWidth, imageDesiredWidth) * currentScale),
                (int) (Math.max(imageMinHeight, imageDesiredHeight) * currentScale), BufferedImage.TYPE_4BYTE_ABGR);
    }

    private static void updateImageSizeAndRedraw() {
        updateImageSize();
        drawElements();
        setScrollBars();
    }

    private synchronized static void zoomTo(double zoomValue) {
        if (zoomValue < 0) {
            currentScale = 1 + (0.09 * zoomValue);
        } else {
            currentScale = 1 + (0.9 * zoomValue);
        }

        if (imageDesiredWidth * currentScale > imageMaxWidth) {
            currentScale = Math.max(1, imageMaxWidth / imageDesiredWidth);
        }

        if (imageDesiredHeight * currentScale > imageMaxHeight) {
            currentScale = Math.max(1, imageMaxHeight / imageDesiredHeight);
        }

        // preserve the previous scroll setting
        scrollPosV = GUIController.getController().getResultScrollPane().getVvalue();

        scrollPosH = GUIController.getController().getResultScrollPane().getHvalue();

        updateImageSizeAndRedraw();
    }

    private static void setScrollBars() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GUIController.getController().getResultScrollPane().setVvalue(scrollPosV);

                GUIController.getController().getResultScrollPane().setHvalue(scrollPosH);
            }
        });
    }
}
