package comp3111.covid;

import java.util.Date;
import java.util.HashMap;

/**
 * data class storing the data, including name inherit from location,people fully vaccinated, 
 * population, rate of vaccination and their getters, setters, and getters setters according to given date.
 * @author Justin Ding
 *
 */
public class RateOfVaccination extends Location{
	private HashMap<Date, Long> fullyVaccinated;
	private HashMap<Date, Long> population;
	private HashMap<Date, Double> rateOfVaccination;
	
	public RateOfVaccination(String name, HashMap<Date, Long> fullyVaccinated, HashMap<Date, Long> population,
			HashMap<Date, Double> rateOfVaccination) {
		super(name);
		this.fullyVaccinated = fullyVaccinated;
		this.population = population;
		this.rateOfVaccination = rateOfVaccination;
	}
	
	public RateOfVaccination(String name) {
		super(name);
		this.fullyVaccinated = new HashMap<Date, Long>();
		this.population = new HashMap<Date, Long>();
		this.rateOfVaccination = new HashMap<Date, Double>();
	}
	
	public void addFullyVaccinated(Date date, Long fullyVaccinated) {
		this.fullyVaccinated.put(date, fullyVaccinated);
	}
	
	public void addPopulation(Date date, Long population) {
		this.population.put(date, population);
	}
	
	public void addRateOfVaccination(Date date, Double rateOfVaccination) {
		this.rateOfVaccination.put(date, rateOfVaccination);
	}
	
	public Long getFullyVaccinatedDateSpecific(Date date) {
		return fullyVaccinated.get(date);
	}
	
	public Long getPopulationDateSpecific(Date date) {
		return population.get(date);
	}
	
	public Double getRateOfVaccinationDateSpecific(Date date) {
		return rateOfVaccination.get(date);
	}

	public HashMap<Date, Long> getFullyVaccinated() {
		return fullyVaccinated;
	}

	public void setFullyVaccinated(HashMap<Date, Long> fullyVaccinated) {
		this.fullyVaccinated = fullyVaccinated;
	}

	public HashMap<Date, Long> getPopulation() {
		return population;
	}

	public void setPopulation(HashMap<Date, Long> population) {
		this.population = population;
	}

	public HashMap<Date, Double> getRateOfVaccination() {
		return rateOfVaccination;
	}

	public void setRateOfVaccination(HashMap<Date, Double> rateOfVaccination) {
		this.rateOfVaccination = rateOfVaccination;
	}
}
