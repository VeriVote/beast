package edu.pse.beast.codearea;

/**
 * The listener interface for receiving stoppedTypingContinuously events.
 * The class that is interested in processing a stoppedTypingContinuously
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addStoppedTypingContinuouslyListener</code> method. When
 * the stoppedTypingContinuously event occurs, that object's appropriate
 * method is invoked.
 *
 * @author Holger Klein
 */
public interface StoppedTypingContinuouslyListener {

    /**
     * Stopped typing continuously.
     *
     * @param newPos the new pos
     */
    void stoppedTypingContinuously(int newPos);
}
