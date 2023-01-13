package comp3111.covid;

import java.lang.Math;
import java.lang.String;
/**
 * data class storing the data for vaccination data for table view and table cell processing, including country, people fully vaccinated, rate of vaccination and their getters.
 * @author Justin Ding
 *
 */
public class RateOfVaccinationDateSpec {
	private String location;
	private String fullyVaccinated;
	private String rateOfVaccination;
	
	public RateOfVaccinationDateSpec(String loc, long tV, double rov) {
		rateOfVaccination = String.format("%.2f", rov)+" %";
		fullyVaccinated = String.format("%d", tV);
		location = loc;
		if(rov < 0) {
			rateOfVaccination = "";
		}
		if(tV == -1) {
		    fullyVaccinated = "";
		}
		
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getFullyVaccinated(){
		return fullyVaccinated;
	}
	
	public String getRateOfVaccination(){
		return rateOfVaccination;
	}
}
