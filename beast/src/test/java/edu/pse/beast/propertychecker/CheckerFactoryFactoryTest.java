package edu.pse.beast.propertychecker;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CheckerFactoryFactoryTest {

    @Before
    public void setUp() {
        
    }

    @Test
    public void createSuccess() {
        PropertyChecker checker = CheckerFactoryFactory.createPropertyChecker("cbmc");
        assertNotNull(checker);
    }

    @Test
    public void createFailure() {
        assertNull(CheckerFactoryFactory.createPropertyChecker("nicht_cbmc"));
    }
    
    @Test
    public void resultSuccess() {
        List<Result> result = CheckerFactoryFactory.getMatchingResult("cbmc", 1);
        assertNotNull(result);
        assertTrue(result.get(0) instanceof CBMCResult);
    }
    
    @Test
    public void resultFailure() {
        PropertyChecker checker = (PropertyChecker) CheckerFactoryFactory.getMatchingResult("nicht_cbmc", 1);
        assertNull(checker);
    }
    
    @Test
    public void createCheckerfactorySuccess() {
        CheckerFactory factory = CheckerFactoryFactory.getCheckerFactory("cbmc", null, null, null, null, null, false);
        
        assertNotNull(factory);
        
        assertTrue(factory instanceof CBMCProcessFactory);
    }
    
    @Test
    public void createCheckerfactoryFailure() {
        CheckerFactory factory = CheckerFactoryFactory.getCheckerFactory("nicht_cbmc", null, null, null, null, null, false);
        
        assertNull(factory);
    }
}
