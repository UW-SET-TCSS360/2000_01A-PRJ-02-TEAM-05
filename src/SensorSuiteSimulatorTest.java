import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;



class SensorSuiteSimulatorTest {
	
	private SensorSuiteSimulator test;
    /**
     * @throws java.lang.Exception
     */
    @Before
    void setUp() throws Exception {
        test = new SensorSuiteSimulator(68, 71, 47, 57, 10, 90, 70, 50, 40);
    }

    /**
     * Test method for the constructor.
     */
    @Test
    public void testSimulatorConstructor() {
        SensorSuiteSimulator s = new SensorSuiteSimulator(1,1,1,1,1,1,1,1,1);
        assertEquals("Constructor test failed", 1, s.getCurrentInsideHum());

    }
    
    /**
     * Test method for all the getters
     */
    @Test
	public void testGetters() {
        SensorSuiteSimulator s = new SensorSuiteSimulator(1,1,1,1,1,1,1,1,1);
        assertEquals("Current inside hum test failed", 1, s.getCurrentInsideHum());
        assertEquals("Current outside hum test failed", 1, s.getCurrentOutsideHum());
        
        assertEquals("Current inside temp test failed", 1, s.getCurrentInsideTemp());
        assertEquals("Current outside temp test failed", 1, s.getCurrentOutsideTemp());
        
        assertEquals("Inside low temp test failed", 1, s.getInsideLow());
        assertEquals("Inside high temp test failed", 1, s.getInsideHigh());
        
        assertEquals("Current wind speed test failed", 1, s.getCurrentWindSpeed());
        

    }
    /**
     * Test method for current pressure
     */
    @Test
    void testCurrentPressure() {
        double temp = test.getCurrentPressure();
        assertTrue(temp <= 31 && temp >= 26);
    }


    /**
     * Test method for rain total
     */
    @Test
    void testCurrentRainTotal() {
        double temp = test.getCurrentRainTotal();
        assertTrue(temp >= 0);
    }

}
