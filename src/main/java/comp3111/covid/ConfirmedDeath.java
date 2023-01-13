package comp3111.covid;

import java.util.Date;
import java.util.HashMap;

/**
 * data class storing the data, including name inherit from location,total deaths, 
 * total deaths per million, and their getters, setters, and getters setters according to given date.
 * @author Justin Ding
 *
 */
public class ConfirmedDeath extends Location{
	
	private HashMap<Date, Long> totalDeath;
	private HashMap<Date, Double> totalDeathPerMillion;

	public ConfirmedDeath(String name) {
		super(name);
		this.totalDeath = new HashMap<Date, Long>();
		this.totalDeathPerMillion = new HashMap<Date, Double>();
	}

	public ConfirmedDeath(String name, HashMap<Date, Long> totalDeath, HashMap<Date, Double> totalDeathPerMillion) {
		super(name);
		this.totalDeath = totalDeath;
		this.totalDeathPerMillion = totalDeathPerMillion;
	}
	
	public void addTotalDeath(Date date, Long totalDeath) {
		this.totalDeath.put(date, totalDeath);
	}
	
	public void addTotalDeathPerMillion(Date date, Double totalDeathPerMillion) {
		this.totalDeathPerMillion.put(date, totalDeathPerMillion);
	}
	
	public Long getTotalCaseDeathDateSpecific(Date date) {
		return totalDeath.get(date);
	}
	
	public Double getTotalDeathPerMillionDateSpecific(Date date) {
		return totalDeathPerMillion.get(date);
	}
	
	public HashMap<Date, Long> getTotalDeath() {
		return totalDeath;
	}

	public void setTotalDeath(HashMap<Date, Long> totalDeath) {
		this.totalDeath = totalDeath;
	}

	public HashMap<Date, Double> getTotalDeathPerMillion() {
		return totalDeathPerMillion;
	}

	public void setTotalDeathPerMillion(HashMap<Date, Double> totalDeathPerMillion) {
		this.totalDeathPerMillion = totalDeathPerMillion;
	}
	
	
	
}
