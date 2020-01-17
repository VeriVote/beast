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

/**
 * The Class ResultImageRenderer.
 *
 * @author Lukas Stapelbroek
 */
public final class ResultImageRenderer {

    /** The Constant ZOOM_IN_RATIO. */
    private static final double ZOOM_IN_RATIO = 0.9;

    /** The Constant ZOOM_OUT_RATIO. */
    private static final double ZOOM_OUT_RATIO = 0.09;

    /** The Constant IMAGE_MAX_WIDTH. */
    private static final double IMAGE_MAX_WIDTH = 10000;

    /** The Constant IMAGE_MAX_HEIGHT. */
    private static final double IMAGE_MAX_HEIGHT = 10000;

    /** The scroll pos V. */
    private static double scrollPosV;

    /** The scroll pos H. */
    private static double scrollPosH;

    /** The drawing blocked. */
    private static Boolean drawingBlocked = false;

    /** The current scale. */
    private static double currentScale = 1;

    /** The element list. */
    private static List<ResultImageElement> elementList =
            new ArrayList<ResultImageElement>();

    /** The view. */
    private static ImageView view = new ImageView();

    /** The background color. */
    private static Color backgroundColor = Color.white;

    /** The image min width. */
    private static double imageMinWidth =
            GUIController.getController()
            .getResultBorderPane().getWidth();

    /** The image min height. */
    private static double imageMinHeight =
            GUIController.getController()
            .getResultBorderPane().getHeight();

    /** The image desired width. */
    private static double imageDesiredWidth = imageMinWidth;

    /** The image desired height. */
    private static double imageDesiredHeight = imageMinHeight;

    /** The image. */
    // the next image the graphic will be drawn on
    private static BufferedImage image =
            new BufferedImage(
                    (int) imageDesiredWidth,
                    (int) imageDesiredHeight,
                    BufferedImage.TYPE_4BYTE_ABGR
                    );

    /**
     * Instantiates a new result image renderer.
     */
    private ResultImageRenderer() { }

    static {
        GUIController.getController().getResultBorderPane().widthProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(final ObservableValue<? extends Number> observableValue,
                                        final Number oldSceneWidth,
                                        final Number newSceneWidth) {
                        imageMinWidth = GUIController.getController()
                                .getResultBorderPane().getWidth();
                        updateImageSizeAndRedraw();
                    }
                });

        GUIController.getController().getResultBorderPane().heightProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(final ObservableValue<? extends Number> observableValue,
                                        final Number oldSceneHeight,
                                        final Number newSceneHeight) {
                        imageMinHeight = GUIController.getController()
                                .getResultBorderPane().getHeight();
                        updateImageSizeAndRedraw();
                    }
                });

        GUIController.getController().getZoomSlider().valueProperty()
                .addListener(new ChangeListener<>() {
                    @Override
                    public void changed(final ObservableValue<? extends Number> observable,
                                        final Number oldValue,
                                        final Number newValue) {
                        zoomTo((double) newValue);
                    }
                });

        // TODO determine which mouse behavior would fit best
        view.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        double clickX = event.getX();
                        double clickY = event.getY();
                        for (ResultImageElement element : elementList) {
                            if (element.isInside(clickX, clickY)) {
                                MouseEvent tmpEvent =
                                        (MouseEvent) event.clone();
                                element.isClicked(tmpEvent);
                                tmpEvent.consume();
                            }
                        }
                        event.consume();
                        drawElements();
                    }
                });
    }

    /**
     * Reset.
     */
    public static synchronized void reset() {
        elementList.clear();
        imageDesiredWidth = imageMinWidth;
        imageDesiredHeight = imageMinHeight;
    }

    /**
     * adds an element which will be printed on the next image.
     *
     * @param element
     *            the element which will be added to the list
     */
    public static synchronized void addElement(
            final ResultImageElement element) {
        imageDesiredWidth =
                Math.max(imageDesiredWidth, element.getxPosBottomRight());
        imageDesiredHeight =
                Math.max(imageDesiredHeight, element.getyPosBottomRight());
        elementList.add(element);
    }

    /**
     * Draw all elements on the underlying image, and show this image in the
     * image view.
     */
    public static void drawElements() {
        if (image.getWidth() != imageDesiredWidth
                || image.getHeight() != imageDesiredHeight) {
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

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        // anti-aliasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);

        for (Iterator<ResultImageElement> iterator =
                elementList.iterator();
                iterator.hasNext();) {
            ResultImageElement element = iterator.next();
            element.drawElement((Graphics2D) graphics.create(), currentScale);
        }
        graphics.dispose();
        view.setImage(SwingFXUtils.toFXImage(image, null));
        synchronized (drawingBlocked) {
            drawingBlocked = false;
        }
    }

    /**
     * Gets the image view.
     *
     * @return the image view
     */
    public static ImageView getImageView() {
        return view;
    }

    /**
     * Update image size.
     */
    private static void updateImageSize() {
        image = new BufferedImage(
                (int) (Math.max(imageMinWidth, imageDesiredWidth)
                        * currentScale),
                (int) (Math.max(imageMinHeight, imageDesiredHeight)
                        * currentScale),
                BufferedImage.TYPE_4BYTE_ABGR);
    }

    /**
     * Update image size and redraw.
     */
    private static void updateImageSizeAndRedraw() {
        drawElements();
        setScrollBars();
    }

    /**
     * Zoom to.
     *
     * @param zoomValue
     *            the zoom value
     */
    private static synchronized void zoomTo(final double zoomValue) {
        if (zoomValue < 0) {
            currentScale = 1 + (ZOOM_OUT_RATIO * zoomValue);
        } else {
            currentScale = 1 + (ZOOM_IN_RATIO * zoomValue);
        }
        if (imageDesiredWidth * currentScale > IMAGE_MAX_WIDTH) {
            currentScale = Math.max(1, IMAGE_MAX_WIDTH / imageDesiredWidth);
        }
        if (imageDesiredHeight * currentScale > IMAGE_MAX_HEIGHT) {
            currentScale = Math.max(1, IMAGE_MAX_HEIGHT / imageDesiredHeight);
        }

        // preserve the previous scroll setting
        scrollPosV = GUIController.getController().getResultScrollPane()
                .getVvalue();
        scrollPosH = GUIController.getController().getResultScrollPane()
                .getHvalue();
        updateImageSizeAndRedraw();
    }

    /**
     * Sets the scroll bars.
     */
    private static void setScrollBars() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GUIController.getController().getResultScrollPane()
                        .setVvalue(scrollPosV);
                GUIController.getController().getResultScrollPane()
                        .setHvalue(scrollPosH);
            }
        });
    }

    /**
     * Reset scroll bars.
     */
    public static void resetScrollBars() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GUIController.getController().getResultScrollPane()
                        .setVvalue(0);
                GUIController.getController().getResultScrollPane()
                        .setHvalue(0);
            }
        });
    }
}
