package comp3111.covid;

import java.util.Date;
import java.util.HashMap;

/**
 * data class storing the data, including name inherit from location,total cases, 
 * total cases per million, and their getters, setters, and getters setters according to given date.
 * @author Justin Ding
 *
 */
public class ConfirmedCase extends Location {
	
	private HashMap<Date, Long> totalCase;
	private HashMap<Date, Double> totalCasePerMillion;
	
	public ConfirmedCase(String name) {
		super(name);
		this.totalCase = new HashMap<Date, Long>();
		this.totalCasePerMillion = new HashMap<Date, Double>();
	}
	
	public ConfirmedCase(String name, HashMap<Date, Long> totalCase, HashMap<Date, Double> totalCasePerMillion) {
		super(name);
		this.totalCase = totalCase;
		this.totalCasePerMillion = totalCasePerMillion;
	}
	
	public void addTotalCase(Date date, Long totalCase) {
		this.totalCase.put(date, totalCase);
	}
	
	public void addTotalCasePerMillion(Date date, Double totalCasePerMillion) {
		this.totalCasePerMillion.put(date, totalCasePerMillion);
	}
	
	public Long getTotalCaseDateSpecific(Date date) {
		return totalCase.get(date);
	}
	
	public Double getTotalCasePerMillionDateSpecific(Date date) {
		return totalCasePerMillion.get(date);
	}

	public HashMap<Date, Long> getTotalCase() {
		return totalCase;
	}

	public void setTotalCase(HashMap<Date, Long> totalCase) {
		this.totalCase = totalCase;
	}

	public HashMap<Date, Double> getTotalCasePerMillion() {
		return totalCasePerMillion;
	}

	public void setTotalCasePerMillion(HashMap<Date, Double> totalCasePerMillion) {
		this.totalCasePerMillion = totalCasePerMillion;
	}
	

}



