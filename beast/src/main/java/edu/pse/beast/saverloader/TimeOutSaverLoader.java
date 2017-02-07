package edu.pse.beast.saverloader;
import java.util.concurrent.TimeUnit;

import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.TimeOut;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;

import java.util.Arrays;

/**
 * @author NikolaiLMS
 */
public class TimeOutSaverLoader {
    public static String createSaveString(TimeOut timeOut) {
        String amount = "<duration>\n" + timeOut.getDuration() + "\n</duration>\n";
        String timeunit = "<timeunit>\n" + timeOut.getOrigUnit().name() + "\n</timeunit>\n";
        return amount + timeunit;
    }

    public static TimeOut createFromSaveString(String s) {
        String [] split = s.split("\n</duration>\n");
        Long duration = Long.parseLong(split[0].replace("<duration>\n", ""));
        split = split[1].split("\n</timeunit>\n");
        TimeUnit timeUnit = TimeUnit.MINUTES;
        String timeunit = split[0].replace("<timeunit>\n", "");
        switch(timeunit) {
            case "MINUTES":
                timeUnit = TimeUnit.MINUTES;
                duration = TimeUnit.MILLISECONDS.toMinutes(duration);
                break;
            case "SECONDS":
                timeUnit = TimeUnit.SECONDS;
                duration = TimeUnit.MILLISECONDS.toSeconds(duration);
                break;
            case "HOURS":
                timeUnit = TimeUnit.HOURS;
                duration = TimeUnit.MILLISECONDS.toHours(duration);
                break;
            case "DAYS":
                timeUnit = TimeUnit.DAYS;
                duration = TimeUnit.MILLISECONDS.toDays(duration);
                break;
        }
        return new TimeOut(timeUnit, duration);
    }
}
