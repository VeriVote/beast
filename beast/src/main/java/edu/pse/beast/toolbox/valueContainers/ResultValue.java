package edu.pse.beast.toolbox.valueContainers;

import java.util.List;

public interface ResultValue {
    public void addValueAtPos(List<Integer> indices, String value);
    
    public String getValueAtPos(List<Integer> indices);
}
