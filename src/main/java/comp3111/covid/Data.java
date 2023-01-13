package comp3111.covid;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.csv.*;
import edu.duke.*;
/**
 * The data class that is used to store most of the information used
 * @author Justin Ding
 *
 */

public class Data {
	private ArrayList<String> locationName;
	private HashMap<String, ConfirmedCase> confirmedCases;
	private HashMap<String, ConfirmedDeath> confirmedDeaths;
	private HashMap<String, RateOfVaccination> rateOfVaccinations;
	private HashMap<String, String> locationDecode;	// Key: location name, Value: location code
	
	public Data() {
		super();
		this.locationName = new ArrayList<String>();
		this.confirmedCases = new HashMap<String, ConfirmedCase>();
		this.confirmedDeaths = new HashMap<String, ConfirmedDeath>();
		this.rateOfVaccinations = new HashMap<String, RateOfVaccination>();
		this.locationDecode = new HashMap<String, String>();
	}
	
	public Data(ArrayList<String> locationName, HashMap<String, ConfirmedCase> confirmedCases,
			HashMap<String, ConfirmedDeath> confirmedDeaths, HashMap<String, RateOfVaccination> rateOfVaccinations, HashMap<String, String> locationDecode) {
		super();
		this.locationName = locationName;
		this.confirmedCases = confirmedCases;
		this.confirmedDeaths = confirmedDeaths;
		this.rateOfVaccinations = rateOfVaccinations;
		this.locationDecode = locationDecode;
	}
	/**
	 * add a location name to the Array list locationName 
	 * @param locationName
	 */
	public void addLocationName(String locationName) {
		this.locationName.add(locationName);
	}
	/**
	 * add a new confirmedCase according to the location name to the HashMap confirmedCases
	 * @param locationName
	 * @param confirmedCase
	 */
	public void addConfirmedCases(String locationName, ConfirmedCase confirmedCase) {
		this.confirmedCases.put(locationName, confirmedCase);
	}
	/**
	 * add a new confirmedDeath according to the location name to the HashMap confirmedDeaths
	 * @param locationName
	 * @param confirmedDeath
	 */
	public void addConfirmedDeaths(String locationName, ConfirmedDeath confirmedDeath) {
		this.confirmedDeaths.put(locationName, confirmedDeath);
	}
	/**
	 * add a new rateOfVaccination according to the location name to the HashMap rateOfVaccinations
	 * @param locationName
	 * @param rateOfVaccination
	 */
	public void addRateOfVaccinations(String locationName, RateOfVaccination rateOfVaccination) {
		this.rateOfVaccinations.put(locationName, rateOfVaccination);
	}
	/**
	 * add a new location code according to the location name to the HashMap locationDecode
	 * @param locationName
	 * @param locationCode
	 */
	public void addLocationDecode(String locationName, String locationCode) {
		this.locationDecode.put(locationName, locationCode);
	}
	/**
	 * get the confirmedCase according to the location name given
	 * @param Loc
	 * @return
	 */
	public ConfirmedCase getConfirmedCaseLocSpecific(String Loc) {
		return confirmedCases.get(Loc);
	}
	/**
	 * get the confirmedDeath according to the location name given
	 * @param Loc
	 * @return
	 */
	public ConfirmedDeath getConfirmedDeathLocSpecific(String Loc) {
		return confirmedDeaths.get(Loc);
	}
	/**
	 * get the rateOfVaccination according to the location name given
	 * @param Loc
	 * @return
	 */
	public RateOfVaccination getRateOfVaccinationLocSpecific(String Loc) {
		return rateOfVaccinations.get(Loc);
	}
	
	/**
	 * get the ArrayList locationName
	 * @return
	 */

	public ArrayList<String> getLocationName() {
		return locationName;
	}
	

	/**
	 * set the ArrayList locationName to the given param
	 * @param locationName
	 */
	public void setLocationName(ArrayList<String> locationName) {
		this.locationName = locationName;
	}
	
	/**
	 * get the HashMap confirmedCases
	 * @return
	 */
	public HashMap<String, ConfirmedCase> getConfirmedCases() {
		return confirmedCases;
	}
	
	/**
	 * set the HashMap confirmedCases to the param given
	 * @param confirmedCases
	 */
	public void setConfirmedCases(HashMap<String, ConfirmedCase> confirmedCases) {
		this.confirmedCases = confirmedCases;
	}
	
	/**
	 * get the HashMap confirmedDeaths
	 * @return
	 */
	public HashMap<String, ConfirmedDeath> getConfirmedDeaths() {
		return confirmedDeaths;
	}
	
	/**
	 * set the HashMap confimedDeaths to the param given
	 * @param confirmedDeaths
	 */
	public void setConfirmedDeaths(HashMap<String, ConfirmedDeath> confirmedDeaths) {
		this.confirmedDeaths = confirmedDeaths;
	}
	
	/**
	 * get the HashMap rateOfVaccinations
	 * @return
	 */
	public HashMap<String, RateOfVaccination> getRateOfVaccinations() {
		return rateOfVaccinations;
	}
	
	/**
	 * set the HashMap rateOfVaccinations to the param given
	 * @param rateOfVaccinations
	 */
	public void setRateOfVaccinations(HashMap<String, RateOfVaccination> rateOfVaccinations) {
		this.rateOfVaccinations = rateOfVaccinations;
	}
	
	/**
	 * get the corresponding location code according to the location name given
	 * @param Loc
	 * @return
	 */
	public String getLocationCode(String Loc) {
		return locationDecode.get(Loc);
	}
	
	/**
	 * compute the rate of vaccination with the given data
	 * @param fullyVaccinated number of people fully vaccinated
	 * @param population total number of vaccination
	 * @return rate of vaccination in percentage
	 */
	
	public static Double computeRateOfVaccination(Long fullyVaccinated, Long population) {
		if(fullyVaccinated <0 || population < 0)
			return -1.0;
		if(population == 0)
			return 0.0;
		if(fullyVaccinated > population)
			return 100.0;
		return (double) fullyVaccinated / population * 100;
		
	}
	
	/**
	 * get the file parser of the given dataset
	 * @author Justin Ding
	 * @param dataset the path to the dataset
	 * @return CSVParser
	 */
	public static CSVParser getFileParser(String dataset) {
	     FileResource fr = new FileResource("dataset/" + dataset);
	     return fr.getCSVParser(true);
	}
	
	/**
	 * Preprocessing Data
	 * @author Justin Ding
	 * @param dataset
	 * @return CSVParser
	 */
	public void preprocess(String dataset) {
		//clear possible previous data
		
		this.locationName = new ArrayList<String>();
		this.confirmedCases = new HashMap<String, ConfirmedCase>();
		this.confirmedDeaths = new HashMap<String, ConfirmedDeath>();
		this.rateOfVaccinations = new HashMap<String, RateOfVaccination>();
		
		
		
		for (CSVRecord rec : getFileParser(dataset)) {
			boolean checkIfEquals = false;
			String currentLocation = rec.get("location");
			if(currentLocation != null) {
				if(!locationName.contains(currentLocation)) {
					locationName.add(currentLocation);
				}
				else {
					checkIfEquals = true;
				}
			}
			else {
				System.out.println("No location");
				continue;
			}
			
			if (!checkIfEquals) {
				confirmedCases.put(currentLocation, new ConfirmedCase(currentLocation));
				confirmedDeaths.put(currentLocation, new ConfirmedDeath(currentLocation));
				rateOfVaccinations.put(currentLocation, new RateOfVaccination(currentLocation));
			}
				//confirmedCases
			String totalCase = rec.get("total_cases");
			String totalCasePerMillion = rec.get("total_cases_per_million");
			@SuppressWarnings("deprecation")
			Date date = new Date(rec.get("date"));
			ConfirmedCase currentConfirmedCase = confirmedCases.get(currentLocation);
			if (!totalCase.equals("")) {
				currentConfirmedCase.addTotalCase(date, Long.valueOf(totalCase));
			}
			else {
				currentConfirmedCase.addTotalCase(date, (long) -1);
			}
				
			if(!totalCasePerMillion.equals("")) {
				currentConfirmedCase.addTotalCasePerMillion(date, Double.valueOf(totalCasePerMillion));
			}
			else {
				currentConfirmedCase.addTotalCasePerMillion(date, -1.0);
			}
				
				
			String totalDeath = rec.get("total_deaths");
			String totalDeathPerMillion = rec.get("total_deaths_per_million");
			@SuppressWarnings("deprecation")
			Date date1 = new Date(rec.get("date"));
			ConfirmedDeath currentConfirmedDeath = confirmedDeaths.get(currentLocation);
			if (!totalDeath.equals("")) {
				currentConfirmedDeath.addTotalDeath(date1, Long.valueOf(totalDeath));
			}
			else {
				currentConfirmedDeath.addTotalDeath(date1, (long) -1);
			}
			
			if(!totalDeathPerMillion.equals("")) {
				currentConfirmedDeath.addTotalDeathPerMillion(date1, Double.valueOf(totalDeathPerMillion));
			}
			else {
				currentConfirmedDeath.addTotalDeathPerMillion(date1, -1.0);
			}
				
				
			String population = rec.get("population");
			String fullyVaccinated = rec.get("people_fully_vaccinated");
			@SuppressWarnings("deprecation")
			Date date11 = new Date(rec.get("date"));
			RateOfVaccination currentRateOfVaccination = rateOfVaccinations.get(currentLocation);
			if (!population.equals("")) {
				currentRateOfVaccination.addPopulation(date11, Long.valueOf(population));
			}
			else {
				currentRateOfVaccination.addPopulation(date11, (long) -1);
			}
			
			if(!fullyVaccinated.equals("")) {
				currentRateOfVaccination.addFullyVaccinated(date11, Long.valueOf(fullyVaccinated));
			}
			else {
				currentRateOfVaccination.addFullyVaccinated(date11, (long) -1);
			}
			
			if(!fullyVaccinated.equals("") && !population.equals("")) {
				Double rate = computeRateOfVaccination(Long.parseLong(fullyVaccinated),Long.parseLong(population));
				currentRateOfVaccination.addRateOfVaccination(date11, rate);
			}
			else {
				currentRateOfVaccination.addRateOfVaccination(date11, -1.0);
			}
					
		}
		putLocationCode();
		System.out.println("Preprocessed");
		
	}
	
	private void putLocationCode() {
		for (CSVRecord rec : getFileParser("mapping.csv")) {
			String locationCode = rec.get("Country Code");
			String locationName = rec.get("Country Name");
			locationDecode.put(locationName, locationCode);
		}
	}
}
