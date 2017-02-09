/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.electiondescription;

/**
 *
 * @author Holger-Desktop
 */
public interface ElectionDescriptionChangeListener {
    public void inputChanged(ElectionTypeContainer input);
    public void outputChanged(ElectionTypeContainer output);
}
