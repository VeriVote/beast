/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
