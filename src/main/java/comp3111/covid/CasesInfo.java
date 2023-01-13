package comp3111.covid;

public class CasesInfo
{
	private String place;
	private String totalCases;
	private String totalCasesPerM;
	
	public CasesInfo(String p, Long tc, Double tcpm){
		place = p;
		if(tc == null || tc <0 || tc==-1)
		{totalCases = "";}
		else
		{totalCases=String.format("%d", tc);}
		
		if(tcpm == null || tcpm <0 || tcpm ==-1)
		{totalCasesPerM = "";}
		else
		{totalCasesPerM=String.format("%.2f", tcpm);}
	}
	
	public String getPlace() {
		return place;
	}
	
	public String getTotalCases(){
		return totalCases;
	}
	
	public String getTotalCasesPerM(){
		return totalCasesPerM;
	}
}

