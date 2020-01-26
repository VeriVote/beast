package edu.pse.beast.saverloader;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.saverloader.staticsaverloaders.TimeOutSaverLoader;

/**
 * JUnit Testclass for saverloader.StaticSaverLoaders.TimeOutSaverLoader.
 *
 * @author Nikolai Schnell
 */
public class TimeOutSaverLoaderTest {
    /** The Constant THREE_POINT_TWO. */
    private static final long THREE_POINT_TWO = (long) 3.2;
    /** The Constant A_LONG_WHILE. */
    private static final int A_LONG_WHILE = 10800000;

    /** The time out instance. */
    private static TimeOut timeOut;

    @BeforeClass
    public static void setUpClass() {
        timeOut = new TimeOut(TimeUnit.HOURS, THREE_POINT_TWO);
    }

    /**
     * Tests the TimeOutSaverLoader by creating a saveString from a TimeOut object,
     * then recreating that object from the saveString and checking its integrity.
     */
    @Test
    public void testSaverLoader() {
        final String saveString = TimeOutSaverLoader.createSaveString(timeOut);
        final TimeOut recreatedTimeOut = TimeOutSaverLoader.createFromSaveString(saveString);
        assertEquals(recreatedTimeOut.getDuration(), A_LONG_WHILE);
        assertEquals(recreatedTimeOut.getOrigUnit(), TimeUnit.HOURS);
    }
}
