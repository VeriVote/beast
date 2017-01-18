/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.options;

import java.util.List;

/**
 *
 * @author Lukas
 */
public abstract class Options{
	
	private final String id;
	
	public Options(String id) {
		this.id = id;
	}
	
    public String getId() {
    	return id;
    }
	
	public abstract List<OptionElement> getOptionElements();
	
	public abstract List<Options> getSubOptions();
	
	public abstract void reapply();
	
	
	
}
