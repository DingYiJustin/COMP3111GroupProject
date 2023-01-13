package comp3111.covid;

/**
 * data class for location, abstract, including name and getter of name
 * @author Justin Ding
 *
 */

public abstract class Location {
	private String name = null;
	
	public Location(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
