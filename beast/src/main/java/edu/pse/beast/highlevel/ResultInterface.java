package edu.pse.beast.highlevel;

public interface ResultInterface {
    
    /**
     * 
     * @return if the result is in a state that it can be presented
     */
    boolean readyToPresent();
    
    /**
     * Presents the result of the check.
     * Every class that extends this class has to implement it for itself.
     * @param presenter the presentable where it is supposed to be presented on
     */
    void presentTo(ResultPresenterElement presenter);
}
