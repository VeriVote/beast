package edu.pse.beast.codearea.errorhandling;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.ToolTipManager;
import javax.swing.text.BadLocationException;

import edu.pse.beast.codearea.codeinput.JTextPaneToolbox;
import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.Tuple;

/**
 * This abstract class implements error displaying functionality common to all
 * specialized error display subclasses.
 *
 * @author Holger Klein
 */
public abstract class ErrorDisplayer
        implements DisplaysStringsToUser, MouseMotionListener {

    /** The Constant MIN_MOUSE_MOVE. */
    private static final int MIN_MOUSE_MOVE = 10;

    /** The Constant DISMISS_DELAY. */
    private static final int DISMISS_DELAY = 10000;

    /** The current string res loader. */
    private StringResourceLoader currentStringResLoader;

    /** The pane. */
    private JTextPane pane;

    /** The painter. */
    private SquigglePainter painter;

    /** The abs pos to msg. */
    private ArrayList<Tuple<Integer, Integer>> absPosToMsg;

    /** The msges. */
    private ArrayList<String> msges;

    /** The high lights. */
    private ArrayList<Object> highLights = new ArrayList<Object>();

    /** The error popup menu. */
    private ErrorPopupMenu errorPopupMenu;

    /**
     * Instantiates a new error displayer.
     *
     * @param textPane
     *            the text pane
     * @param currStrResLoader
     *            the curr str res loader
     */
    public ErrorDisplayer(final JTextPane textPane,
                          final StringResourceLoader currStrResLoader) {
        absPosToMsg = new ArrayList<Tuple<Integer, Integer>>();
        msges = new ArrayList<String>();
        ToolTipManager.sharedInstance().setInitialDelay(1);
        ToolTipManager.sharedInstance().setDismissDelay(DISMISS_DELAY);
        this.pane = textPane;
        textPane.addMouseMotionListener(this);
        this.painter = new SquigglePainter(Color.red);
        this.currentStringResLoader = currStrResLoader;
        errorPopupMenu = new ErrorPopupMenu(textPane);
    }

    /**
     * Gets the string resource loader.
     *
     * @return the string resource loader
     */
    protected StringResourceLoader getStringResourceLoader() {
        return this.currentStringResLoader;
    }

    /**
     * Gets the j text pane.
     *
     * @return the j text pane
     */
    protected JTextPane getJTextPane() {
        return this.pane;
    }

    /**
     * Sets the string resource loader.
     *
     * @param stringResLoader
     *            the new string resource loader
     */
    protected void setStringResourceLoader(final StringResourceLoader stringResLoader) {
        this.currentStringResLoader = stringResLoader;
    }

    /**
     * Removes all previously shown errors and thus gets ready to show the newly
     * found ones. This method must be overwritten by subclasses to do anything
     * useful.
     *
     * @param errors
     *            the errors to be presented
     */
    public void showErrors(final ArrayList<CodeError> errors) {
        absPosToMsg = new ArrayList<Tuple<Integer, Integer>>();
        msges = new ArrayList<String>();

        for (Object o : highLights) {
            pane.getHighlighter().removeHighlight(o);
        }
        pane.repaint();
    }

    /**
     * Show error.
     *
     * @param er
     *            the er
     * @param msg
     *            the msg
     */
    protected void showError(final CodeError er, final String msg) {
        int startpos = er.getStartPos();
        int endpos = er.getEndPos();
        if (startpos == endpos) {
            endpos++;
        }
        absPosToMsg.add(new Tuple<Integer, Integer>(startpos, endpos));
        msges.add(msg);
        try {
            highLights.add(pane.getHighlighter()
                    .addHighlight(startpos, endpos + 1, painter)
            );
        } catch (BadLocationException ex) {
            Logger.getLogger(ErrorDisplayer.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /*
     * private String getPosString(CodeError er) { String template =
     * "ERROR: ERRNO, LINE: LINO, CHAR: POSNO"; template =
     * template.replace("ERROR",
     * currentStringResLoader.getStringFromID("error")); template =
     * template.replace("LINE", currentStringResLoader.getStringFromID("line"));
     * template = template.replace("CHAR",
     * currentStringResLoader.getStringFromID("char")); template =
     * template.replace("LINO", String.valueOf(er.getLine())); template =
     * template.replace("POSNO", String.valueOf(er.getPosInLine())); template =
     * template.replace("ERRNO", String.valueOf(er.getErrorNumber())); return
     * template; }
     */

    @Override
    public final void mouseMoved(final MouseEvent e) {
        Point pt = new Point(e.getX(), e.getY());
        int pos = pane.viewToModel2D(pt);
        if (pos == JTextPaneToolbox.getText(pane).length()) {
            pane.setToolTipText(null);
            // errorPopupMenu.setVisible(false);
        } else if (MIN_MOUSE_MOVE <= Math.abs(errorPopupMenu.getLocation().x - pt.getX())
                || MIN_MOUSE_MOVE <= Math.abs(errorPopupMenu.getLocation().x - pt.getX())
                || !errorPopupMenu.isVisible()) {
            boolean showToolTip = false;
            for (int i = 0; i < absPosToMsg.size(); ++i) {
                if (absPosToMsg.get(i).first() <= pos
                        && pos <= absPosToMsg.get(i).second()) {
                    pane.setToolTipText(msges.get(i));
                    // errorPopupMenu.getErrorItem().setText(msges.get(i));
                    // errorPopupMenu.show(pane, e.getX(), e.getY() + 20);
                    showToolTip = true;
                }
            }
            if (!showToolTip) {
                pane.setToolTipText(null);
            }
            /*
             * if(errorPopupMenu.isVisible()) { errorPopupMenu.setVisible(false); }
             */
        }
    }

    @Override
    public void mouseDragged(final MouseEvent e) {
    }
}
