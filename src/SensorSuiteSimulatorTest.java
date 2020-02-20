import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests relied on the constructor's author being sane- 
 * first two ints are low - high, last two are high - low bounds. 
 * Fixed.
 * @author Geoff
 *
 */
public class SensorSuiteSimulatorTest {
	
	private SensorSuiteSimulator test;
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        test = new SensorSuiteSimulator(68, 71, 47, 57, 10, 90, 70, 50, 40);
    }

    /**
     * Test method for the constructor.
     */
    @Test
    public void testSimulatorConstructor() {
        SensorSuiteSimulator s = new SensorSuiteSimulator(1,2,1,2,1,2,1,2,1);
        assertEquals("Constructor test failed", 1, s.getCurrentInsideHum());

    }
    
    /**
     * Test method for all the getters
     */
    @Test
	public void testGetters() {
        SensorSuiteSimulator s = new SensorSuiteSimulator(1,3,1,3,1,3,1,3,1);
        
        int temp = s.getCurrentInsideHum();
        
        assertTrue(temp >= 1 && temp <= 3);
        
        temp = s.getCurrentOutsideHum();
        
        assertTrue(temp >= 1 && temp <= 3);
       
        temp = s.getCurrentInsideTemp();
        
        assertTrue(temp >= 1 && temp <= 3);
        
        temp = s.getCurrentOutsideTemp();
        
        assertTrue(temp >= 1 && temp <= 3);
                
        temp = s.getInsideLow();
        
        assertTrue(temp >= 1 && temp <= 3);
        
        temp = s.getInsideHigh();
        
        assertTrue(temp >= 1 && temp <= 3);
        
        temp = s.getCurrentWindSpeed();
        
        assertTrue(temp >= 0);
              

    }
    /**
     * Test method for current pressure
     */
    @Test
    public void testCurrentPressure() {
        double temp = test.getCurrentPressure();
        assertTrue(temp <= 31 && temp >= 26);
    }


    /**
     * Test method for rain total
     */
    @Test
    public void testCurrentRainTotal() {
        double temp = test.getCurrentRainTotal();
        assertTrue(temp >= 0);
    }

}
