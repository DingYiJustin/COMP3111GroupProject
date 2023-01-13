package comp3111.covid;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class DeathData {
	private final SimpleStringProperty country;
	private final SimpleStringProperty total;
	private final SimpleStringProperty perMillion;
	
	public DeathData(String country, Long total, Double perMillion) {
		super();
		this.country = new SimpleStringProperty(country);
		this.total = new SimpleStringProperty((total == null || total < 0)? "" : String.format("%d", total));
		this.perMillion = new SimpleStringProperty((perMillion == null || perMillion < 0)? "" : String.format("%.2f", perMillion));
	}
	
	public String getCountry() {
		return country.get();
	}
	
	public String getTotal() {
		return total.get();
	}
	
	public String getPerMillion() {
		return perMillion.get();
	}
}
