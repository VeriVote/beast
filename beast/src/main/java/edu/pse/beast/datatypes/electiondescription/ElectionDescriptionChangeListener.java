package edu.pse.beast.datatypes.electiondescription;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Holger-Desktop
 */
public interface ElectionDescriptionChangeListener {
    public void inputChanged(InputType input);
    public void outputChanged(OutputType output);
}
