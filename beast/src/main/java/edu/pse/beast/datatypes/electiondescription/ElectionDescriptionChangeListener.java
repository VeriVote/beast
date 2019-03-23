package edu.pse.beast.datatypes.electiondescription;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 *
 * @author Holger-Desktop
 */
public interface ElectionDescriptionChangeListener {
    void inputChanged(InputType input);

    void outputChanged(OutputType output);
}