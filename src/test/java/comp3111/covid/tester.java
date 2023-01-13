package comp3111.covid;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
public class tester {
	int[] arrAscending, arrDescending;
	
	@Before
	public void setUp() throws Exception {
		arrAscending = new int[] {1, 2, 3, 4, 5};
		arrDescending = new int[] {5, 4, 3, 2, 1};
	}

	@Test
	public void computeRateWithValidInput() {
		assertTrue(DataAnalysis.computeRateOfVaccination(10,1000)==1.0f);
	}
	@Test
	public void computeRateWithInvalidInput() {
		assertTrue(DataAnalysis.computeRateOfVaccination(0,1000)==0.0f);
	}
	
	@Test
	public void controllerTest() throws Exception {
		String[] args = {""};
		assertTrue(MyApplication.main(args));
	}
	
}
