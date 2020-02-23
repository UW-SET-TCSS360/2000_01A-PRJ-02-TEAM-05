
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** 
 * This is a class that will generate random weather data for the Sensor suite
 * 
 * @author Jordan Overbo
 * @date 01-31-2020
 */
public class SensorSuiteSimulator {
	
	/**
	 * This holds the month temperature highs for creating 25 months of data.
	 */
	private static final int[] MONTH_HIGHS = {40, 50, 56, 67, 75, 86,
											  95, 95, 79, 65, 53, 40};
	
	/**
	 * Holds the cardinal directions for generating a wind direction.
	 */
	private static final String[] WIND_DIRECTIONS = {"N", "NE", "E", "SE",
													 "S", "SW", "W", "NW"};
	
	/**
	 * Holds the temperature data for the last 25 days.
	 */
	private final List<Integer> last25Days;
	
	/**
	 * Holds the temperature data for the last 25 months.
	 */
	private final List<Integer> last25Months;
	
	/**
	 * Holds the days high outside temperature.
	 */
	private final int todaysInsideHighTemp;
	
	/** 
	 * Holds the days low outside temperature.
	 */
	private final int todaysInsideLowTemp;
	
	/**
	 * Holds the days high outside temperature.
	 */
	private final int todaysOutsideHighTemp;
	
	/** 
	 * Holds the days low outside temperature.
	 */
	private final int todaysOutsideLowTemp;
	
	/**
	 * Holds the current inside temp.
	 */
	private int currentInsideTemp;
	
	/**
	 * Holds the current outside temp.
	 */
	private int currentOutsideTemp;
	
	/**
	 * Holds the current wind speed.
	 */
	private int currentWindSpeed;
	
	/**
	 * Holds the current rainfall total.
	 */
	private double rainfall;
	
	/**
	 * Holds the current barometric pressure.
	 */
	private double pressure;
	
	/**
	 * Holds the current inside humidity.
	 */
	private int insideHumidity;
	
	/**
	 * Holds the current outside humidity.
	 */
	private int outsideHumidity;
	
	/**
	 * The high outside humidity for the day.
	 */
	private final int highOutsideHumidity;
	
	/**
	 * The low outside humidity for the day.
	 */
	private final int lowOutsideHumidity;
	
	/**
	 * The high inside humidity for the day.
	 */
	private final int highInsideHumidity;
	
	/**
	 * The low inside humidity for the day.
	 */
	private final int lowInsideHumidity;
	
	/** 
	 * Holds the maximum wind speed for the area.
	 */
	private final int maxWind;
	
	/**
	 * Constructor that initializes the fields to reasonable values.
	 * 
	 * @param theLowInsideTemp for the day.
	 * @param theHighInsideTemp for the day.
	 * @param theLowOutsideTemp for the day.
	 * @param theHighOutsideTemp for the day.
	 * @param theMaxWind for the day.
	 * @param highOutsideHum for the day.
	 * @param lowOutsideHum for the day.
	 * @param highInsideHum for the day.
	 * @param lowInsideHum for the day.
	 */
	public SensorSuiteSimulator(int theLowInsideTemp, int theHighInsideTemp, int theLowOutsideTemp, 
								int theHighOutsideTemp, int theMaxWind, int highOutsideHum,
								int lowOutsideHum, int highInsideHum, int lowInsideHum) {
		
		todaysInsideLowTemp = theLowInsideTemp;
		todaysInsideHighTemp = theHighInsideTemp;
		todaysOutsideLowTemp = theLowOutsideTemp;
		todaysOutsideHighTemp = theHighOutsideTemp;
		currentInsideTemp = ThreadLocalRandom.current().nextInt(todaysInsideLowTemp, todaysInsideHighTemp);
		currentOutsideTemp = ThreadLocalRandom.current().nextInt(todaysOutsideLowTemp, todaysOutsideHighTemp);
		pressure = generateRandomBaroPressure(28.00);
		maxWind = theMaxWind;
		rainfall = 0;
		highOutsideHumidity = highOutsideHum;
		lowOutsideHumidity = lowOutsideHum;
		highInsideHumidity = highInsideHum;
		lowInsideHumidity = lowInsideHum;
		outsideHumidity = ThreadLocalRandom.current().nextInt(lowOutsideHumidity, highOutsideHumidity);
		insideHumidity = ThreadLocalRandom.current().nextInt(lowInsideHumidity, highInsideHumidity);
		last25Months = setLast25Months();
		last25Days = setLast25Days();
	}
	
	/**
	 * Helper methods to set the weather temps for the last 25 months.
	 * 
	 * @return the list holding the weather data for the last 25 months.
	 */
	private List<Integer> setLast25Months() {
		List<Integer> temp = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			int theTemp = MONTH_HIGHS[i % 12];
			temp.add(ThreadLocalRandom.current().nextInt(theTemp - 30, theTemp));
		}
		return temp;
	}

	/**
	 * Helper methods to set the weather temps for the last 25 days.
	 * 
	 * @return the list holding the weather data for the last 25 days.
	 */
	private List<Integer> setLast25Days() {
		List<Integer> temp = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			temp.add(ThreadLocalRandom.current().nextInt(todaysOutsideLowTemp, todaysOutsideHighTemp));
		}
		return temp;
	}
	
	/**
	 * gets and returns the new outside temp.
	 * 
	 * @return the new outside temp.
	 */
	public int getCurrentOutsideTemp(){
		currentOutsideTemp = generateRandomTemp(currentOutsideTemp, todaysOutsideLowTemp, todaysOutsideHighTemp);
		return currentOutsideTemp;
	}
	
	/**
	 * gets and returns the new inside temp.
	 * 
	 * @return the new inside temp.
	 */
	public int getCurrentInsideTemp() {
		currentInsideTemp = generateRandomTemp(currentInsideTemp, todaysInsideLowTemp, todaysInsideHighTemp);
		return currentInsideTemp;
	}
	
	/**
	 * Generates a random temperature 
	 * 
	 * @param currentTemp at the time of generation
	 * @return the new temperature.
	 */
	private int generateRandomTemp(int currentTemp, int lowRange, int highRange) {
		int temp = ThreadLocalRandom.current().nextInt(currentTemp - 2, currentTemp + 3);
		while(temp > highRange || temp < lowRange) {
			temp = ThreadLocalRandom.current().nextInt(currentTemp - 2, currentTemp + 3);
		}
		
		return temp;
	}
	
	/**
	 * Returns the inside high temp.
	 * 
	 * @return the inside high temp.
	 */
	public int getInsideHigh(){
		return todaysInsideHighTemp;
	}
	
	/**
	 * Returns the inside low temp.
	 * 
	 * @return the inside low temp.
	 */
	public int getInsideLow(){
		return todaysInsideLowTemp;
	}
	
	/**
	 * Returns the outside high temp.
	 * 
	 * @return the outside high temp.
	 */
	public int getOutsideHigh(){
		return todaysOutsideHighTemp;
	}
	
	/**
	 * Returns the outside low temp.
	 * 
	 * @return the outside low temp.
	 */
	public int getOutsideLow(){
		return todaysOutsideLowTemp;
	}
	
	/**
	 * gets and returns the new wind speed
	 * 
	 * @return the new wind speed.
	 */
	public int getCurrentWindSpeed() {
		currentWindSpeed = generateRandomWindSpeed(currentWindSpeed);
		return currentWindSpeed;
	}
	/**
	 * Generates a random wind speed close to the current wind speed but between 0 and the max 
	 * wind speed.
	 * 
	 * @param currentWindSpeed at the time of generation.
	 * @return the new wind speed.
	 */
	private int generateRandomWindSpeed(int theCurrentWindSpeed) {
		int wind = ThreadLocalRandom.current().nextInt(theCurrentWindSpeed - 1, theCurrentWindSpeed + 2);
		while(wind > maxWind || wind < 0) {
			wind = ThreadLocalRandom.current().nextInt(theCurrentWindSpeed - 1, theCurrentWindSpeed + 2);
		}
		
		return wind;
	}
	
	/**
	 * Randomly returns a wind direction using the constant array of wind directions.
	 * 
	 * @return the new wind direction.
	 */
	public String getCurrentWindDirection() {	
		return WIND_DIRECTIONS[ThreadLocalRandom.current().nextInt(0, 8)];
	}
	
	/** 
	 * Returns the list containing weather data for the last 25 days.
	 * 
	 * @return the list containing weather data for the last 25 days.
	 */
	public List<Integer> getLast25Days(){	
		return last25Days;
	}
	
	/** 
	 * Returns the list containing weather data for the last 25 months.
	 * 
	 * @return the list containing weather data for the last 25 months.
	 */
	public List<Integer> getLast25Months(){		
		return last25Months;
	}
	
	/**
	 * Calls a method and randomly updates the rainfall by a small increment.
	 * 
	 * @return the new current rainfall total.
	 */
	public double getCurrentRainTotal() {
		rainfall += generatePrecipitation();
		return new BigDecimal(rainfall).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * Generates a precipitation amount and adds it to the total.
	 * 
	 * @param rainfall total
	 * @return the new rainfall total.
	 */
	public double generatePrecipitation() {
		double precipitation = ThreadLocalRandom.current().nextDouble(0, .01);
		return precipitation;	
	}	
	
	/**
	 * Returns the current barometric pressure.
	 * 
	 * @return the new barometric pressure.
	 */
	public double getCurrentPressure() {
		pressure = generateRandomBaroPressure(pressure);
		return new BigDecimal(pressure).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}
	
	/**
	 * Generates a random barometric pressure in a normal range.
	 * 
	 * @param currentPressure at the time of generation.
	 * @return the new barometric pressure.
	 */
	private double generateRandomBaroPressure(double currentPressure) {
		double temp = ThreadLocalRandom.current().nextDouble( currentPressure -  .2, currentPressure + .2);
		while(temp > 31 || temp < 26) {
			temp = ThreadLocalRandom.current().nextDouble(currentPressure - .2, currentPressure + .2);
		}
		
		return temp;
	}
	
	/**
	 * Returns the current inside humidity.
	 * 
	 * @return the new inside humidity.
	 */
	public int getCurrentInsideHum() {
		insideHumidity = generateRandomHumidity(insideHumidity, lowInsideHumidity, highInsideHumidity);
		return insideHumidity;
	}
	
	/**
	 * Returns the current outside humidity.
	 * 
	 * @return the new outside humidity.
	 */
	public int getCurrentOutsideHum() {
		outsideHumidity = generateRandomHumidity(outsideHumidity, lowOutsideHumidity, highOutsideHumidity);
		return outsideHumidity;
	}
	
	/**
	 * Generates and returns a random humidity in the given range.
	 * 
	 * @param currentHumidity at the time
	 * @param low possible humidity
	 * @param high possible humidity
	 * @return the new humidity
	 */
	public int generateRandomHumidity(int currentHumidity, int low, int high) {
		int temp = ThreadLocalRandom.current().nextInt(currentHumidity - 1, currentHumidity + 1);
		while(temp > high || temp < low) {
			temp = ThreadLocalRandom.current().nextInt(currentHumidity - 1, currentHumidity + 1);
		}
		
		return temp;
	}
	
	/**
	 * Returns the current wind chill according to the formula at
	 * https://www.mentalfloss.com/article/26730/how-wind-chill-calculated
	 * 
	 */
	public int getWindChill() {
		
		int temp = getCurrentOutsideTemp();
		int wind = getCurrentWindSpeed();
		
		return (int) (35.74 + 0.6215*temp
					- 35.75*(Math.pow(wind, 0.16)) +
					0.4275*temp*(Math.pow(wind, 0.16)));
	}
}
