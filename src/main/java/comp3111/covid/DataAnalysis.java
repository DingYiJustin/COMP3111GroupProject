package comp3111.covid;

import java.util.Date;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;


import org.apache.commons.csv.*;
import edu.duke.*;

/**
 * 
 * Data Explorer on COVID-19
 * @author <a href=mailto:namkiu@ust.hk>Namkiu Chan</a>
 * @version	1.1
 * 
 */
public class DataAnalysis {
	public static Data data = new Data();
	public static HashMap<String, RateOfVaccination> graphRateOfVaccination = new HashMap<>();
	
	/**
	 * process the rate of vaccinations in the variable data such that they become cummulative
	 */
	public static void processRateOfVaccination() {
		LocalDate minDate = LocalDate.of(2020,3,1);
        LocalDate maxDate = LocalDate.of(2021,7,20);
        LocalDate currentDate = LocalDate.of(2020,3,1);
        
        ArrayList<String> locationNames = data.getLocationName();
        String currentCountry = null;
        
        for(int i = 0; i<locationNames.size(); i++ ) {
        	currentCountry = locationNames.get(i);
        	currentDate = LocalDate.of(2020,3,1);
        	RateOfVaccination currentRov = data.getRateOfVaccinationLocSpecific(currentCountry);
        	RateOfVaccination inputtingRov = new RateOfVaccination(currentCountry);
        	Double prevCurrentRateOfVaccinationValue = -1.0;
        	while(!currentDate.isAfter(maxDate)) {
        		String strDate = String.format("%d/%d/%d",currentDate.getMonthValue(),currentDate.getDayOfMonth(),currentDate.getYear());
        		Date currentDateInDate = new Date(strDate);
	        	Double currentRateOfVaccinationValue = currentRov.getRateOfVaccinationDateSpecific(currentDateInDate);
	        	if(currentRateOfVaccinationValue == null || currentRateOfVaccinationValue < 0 ) {
	        		currentRateOfVaccinationValue = prevCurrentRateOfVaccinationValue;
	        	}
	        	else {
	        		prevCurrentRateOfVaccinationValue = currentRateOfVaccinationValue;
	        	}
	        	inputtingRov.addRateOfVaccination(currentDateInDate, currentRateOfVaccinationValue);
	        	
	        	currentDate = currentDate.plus(1,ChronoUnit.DAYS);
	        }
        	graphRateOfVaccination.put(currentCountry, inputtingRov);
        }
	       
	}
	
	public static float computeRateOfVaccination(long fullyVaccinated, long population) {
		if(population == 0)
			return 0;
		if(fullyVaccinated > population)
			return 1.0f;
		return (float) fullyVaccinated / population * 100;
		
	}
 
}